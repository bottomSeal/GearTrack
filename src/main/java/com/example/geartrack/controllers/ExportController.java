package com.example.geartrack.controllers;

import com.example.geartrack.services.ExportService;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

@RestController
@Slf4j
@RequestMapping("/export")
@RequiredArgsConstructor
public class ExportController {

    private final ExportService exportService;

    @GetMapping("/xlsx-report/{tripId}")
    @ResponseBody
    public ResponseEntity<InputStreamResource> downloadXLSX(@PathVariable UUID tripId) throws IOException {

        File file = exportService.getXLSXFile(tripId);

        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=file.xlsx");

        MediaType mediaType = MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(file.length())
                .contentType(mediaType)
                .body(resource);
    }

    @GetMapping("/pdf-report/{tripId}")
    @ResponseBody
    public ResponseEntity<InputStreamResource> downloadPDF(@PathVariable UUID tripId) throws IOException {

        File file = exportService.getPDFFile(tripId);

        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=file.pdf");

        MediaType mediaType = MediaType.APPLICATION_PDF;

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(file.length())
                .contentType(mediaType)
                .body(resource);
    }
}
