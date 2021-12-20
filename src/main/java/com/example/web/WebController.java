package com.example.web;

import com.example.db.DatabaseService;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

@Controller
public class WebController {

    @GetMapping("/csv_upload")
    public String csvUpload() {

        return "csv_form";
    }

    @PostMapping("/csv_upload")
    public String csvUploadHandle(Model model, @RequestParam("file") MultipartFile file) throws IOException, SQLException {

        InputStream inputStream = file.getInputStream();
        Scanner s = new Scanner(inputStream).useDelimiter("\n");
        List<String[]> result = new ArrayList<>();
        String file_name = file.getOriginalFilename();


        // TODO: валидация файла

        if (Objects.equals(file_name, "tr_mcc_codes.csv")
                || Objects.equals(file_name, "tr_types.csv")) {
            while (s.hasNext()) {
                String[] curr_string = s.next().split(";", -1);
                result.add(curr_string);
            }
        } else {
            while (s.hasNext()) {
                String[] curr_string = s.next().split(",", -1);
                result.add(curr_string);
            }
        }

        DatabaseService databaseService = DatabaseService.getDatabaseService();
        databaseService.insertCSV(result, file_name.substring(0, file_name.length() - 4));

        model.addAttribute("message", "Загрузка успешна!");
        return "csv_form";
    }

    @GetMapping("/request")
    public String requestResults() {
        return "request_results";
    }

    @PostMapping("/request")
    public String returnResults(@ModelAttribute("string_to_find") String string_to_find, Model model) {
        System.out.println(string_to_find);

        DatabaseService databaseService = DatabaseService.getDatabaseService();
        var result = databaseService.findTransactions(string_to_find);
        model.addAttribute("result", result);
        if (!result.isEmpty()){
            model.addAttribute("flag", true);
        }

        return "request_results";
    }
}
