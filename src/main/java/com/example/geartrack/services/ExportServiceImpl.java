package com.example.geartrack.services;

import com.example.geartrack.dao.TripDao;
import com.example.geartrack.dao.TripItemDao;
import com.example.geartrack.dao.UserDao;
import com.example.geartrack.entities.ItemEntity;
import com.example.geartrack.entities.TripEntity;
import com.example.geartrack.entities.UserEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class ExportServiceImpl implements ExportService{

    private final TripItemDao tripItemDao;

    private final UserDao userDao;

    private final TripDao tripDao;

    private UserEntity getUserFromContext() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return userDao.getUserByEmail((String) authentication.getPrincipal());
    }

    @Override
    public File getXLSXFile(UUID tripId) throws IOException {

        List<ItemEntity> items =  tripItemDao.getItemsForTrip(tripId, getUserFromContext());

        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Gear");

            Row row = sheet.createRow(0);

            row.createCell(0).setCellValue("Id");
            row.createCell(1).setCellValue("Название");
            row.createCell(2).setCellValue("Описание");
            row.createCell(3).setCellValue("Собрано");

            for (int i = 0; i < items.size(); i++) {
                Row itemRow = sheet.createRow(i + 1);

                itemRow.createCell(0).setCellValue(i);
                itemRow.createCell(1).setCellValue(items.get(i).getName());
                itemRow.createCell(2).setCellValue(items.get(i).getDescription());
                itemRow.createCell(3).setCellValue(items.get(i).isCollected());
            }

            File tempFile = File.createTempFile("temp", ".xlsx");

            try (FileOutputStream fos = new FileOutputStream(tempFile)) {
                workbook.write(fos);
            }

            return tempFile;
        }
    }

    private String getPathToFont() {
        Yaml yaml = new Yaml();
        String fontPath = null;

        try (InputStream inputStream = getClass().getResourceAsStream("/config.yaml")) {
            Map<String, Object> config = yaml.load(inputStream);
            fontPath = (String) config.get("fontPath");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return fontPath;
    }


    @Override
    public File getPDFFile(UUID tripId) throws IOException {

        List<ItemEntity> items =  tripItemDao.getItemsForTrip(tripId, getUserFromContext());

        TripEntity trip = tripDao.get(tripId, getUserFromContext());

        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);

        try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
            String pathToFont = getPathToFont();

            if (pathToFont == null) {
                throw new IllegalStateException("Path to font is null");
            }

            PDType0Font font = PDType0Font.load(document, new File(pathToFont));
            contentStream.setFont(font, 12);
            contentStream.beginText();
            contentStream.newLineAtOffset(25, 700);
            float lineSpacing = 12;

            contentStream.showText("Название: " + trip.getName());
            contentStream.newLineAtOffset(0, -lineSpacing);
            contentStream.showText("Описание: " + trip.getDescription());
            contentStream.newLineAtOffset(0, -lineSpacing);
            contentStream.showText("Дата начала: " + trip.getStartDate());
            contentStream.newLineAtOffset(0, -lineSpacing);
            contentStream.showText("Дата окончания: " + trip.getEndDate());
            contentStream.newLineAtOffset(0, -2*lineSpacing);

            for (ItemEntity item : items) {
                contentStream.showText("\u25A1 " + item.getName());
                contentStream.newLineAtOffset(0, -lineSpacing);
            }

            contentStream.endText();
        }


        File tempFile = File.createTempFile("temp", ".pdf");

        document.save(tempFile);
        document.close();

        return tempFile;
    }
}
