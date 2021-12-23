package com.example.web;

import com.example.Entities.transactions;
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
import java.util.logging.Logger;

@Controller
public class WebController {

    private static final Logger LOGGER = Logger.getLogger(WebController.class.getName());
    @GetMapping("/csv_upload")
    public String csvUpload() {

        return "csv_form";
    }

    @PostMapping("/csv_upload")
    public String csvUploadHandle(Model model, @RequestParam("file") MultipartFile file){

        Scanner s;
        List<String[]> result = new ArrayList<>();
        String file_name = "";

        try (InputStream inputStream = file.getInputStream();)
        {
            s = new Scanner(inputStream).useDelimiter("\n");
            file_name = file.getOriginalFilename();
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
        }
        catch (IOException ex)
        {
            LOGGER.info(ex.getMessage());
        }

        // TODO: валидация файла

        try{
            DatabaseService databaseService = DatabaseService.getDatabaseService();
            if (file_name.contains(".csv"))
            {
                databaseService.insertCSV(result, file_name.substring(0, file_name.length() - 4));
                model.addAttribute("message", "Загрузка успешна!");
                model.addAttribute("color", "color:DarkGreen");
            }
            else if (file_name.length() < 1)
            {
                model.addAttribute("message", "Вы не выбрали файл");
                model.addAttribute("color", "color:Crimson");
                LOGGER.info("Файл не был выбран");
            }
            else
            {
                model.addAttribute("message", "Вы не выбрали файл не подходящего разрешения. Пожалуйста, выберете .csv файл");
                model.addAttribute("color", "color:Crimson");
                LOGGER.info("Был выбран файл не подходящего разрешения");
            }
        }
        catch (SQLException ex)
        {
            LOGGER.info(ex.getMessage());
        }

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
