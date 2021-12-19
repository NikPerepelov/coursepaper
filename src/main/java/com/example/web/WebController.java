package com.example.web;

import com.example.db.DatabaseService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
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
        databaseService.exec(result, file_name.substring(0, file_name.length() - 4));

        model.addAttribute("message", "Загрузка успешна!");
        return "csv_form";
    }

    @GetMapping("/request")
    public String requestResults(Model model) {
        List<String> results = new ArrayList<>();

        results.add("Один");
        results.add("Два");

        model.addAttribute("results", results);
        return "request_results";
    }
}
