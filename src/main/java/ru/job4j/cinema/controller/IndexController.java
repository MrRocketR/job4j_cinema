package ru.job4j.cinema.controller;

import lombok.Data;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.job4j.cinema.model.File;
import ru.job4j.cinema.service.FileService;

@Controller
@Data
public class IndexController {

    private final FileService fileService;

    @GetMapping("/index")
    public String mainPage(Model model) {
        File logo = fileService.findByName("logo");
        model.addAttribute("logo", logo);
        return "index";
    }

    @GetMapping("/logoFile/")
    public ResponseEntity<Resource> download() {
        File logoFile = fileService.findById(1);
        return ResponseEntity.ok()
                .headers(new HttpHeaders())
                .contentLength(logoFile.getImg().length)
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(new ByteArrayResource(logoFile.getImg()));
    }
}
