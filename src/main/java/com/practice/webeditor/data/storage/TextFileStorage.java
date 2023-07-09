package com.practice.webeditor.data.storage;

import com.practice.webeditor.data.model.TextFileModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@Component
@EnableScheduling
public class TextFileStorage {

    @Value("${file.storage.path}")
    private String storagePath;

    public List<TextFileModel> getAllFiles() {
        List<TextFileModel> textFileModels = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        File directory = new File(storagePath);
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                try {
                    textFileModels.add(objectMapper.readValue(file, TextFileModel.class));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return textFileModels;
    }

    public boolean deleteFileByUUID(UUID fileId) {
        String filePath = storagePath + fileId + ".json";
        File file = new File(filePath);
        return file.delete();
    }

    public void saveTextFile(TextFileModel textFileModel) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String filePath = storagePath + textFileModel.getFileId().toString() + ".json";
            objectMapper.writeValue(new File(filePath), textFileModel);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public TextFileModel readTextFileByUUID(UUID fileId) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String filePath = storagePath + fileId + ".json";
            return objectMapper.readValue(new File(filePath), TextFileModel.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private final int minutesOfDelay = 6 * 60;

    @Scheduled(fixedDelay = minutesOfDelay * 60 * 1000)
    public void deleteFilesWithoutAccess() {

        File directory = new File(storagePath);
        File[] files = directory.listFiles();

        if (files != null) {
            for (File file : files) {
                try {
                    BasicFileAttributes attributes = Files.readAttributes(file.toPath(), BasicFileAttributes.class);
                    Date lastAccessedTime = new Date(attributes.lastAccessTime().toMillis());
                    LocalDateTime lastAccessedDateTime = LocalDateTime.ofInstant(lastAccessedTime.toInstant(), attributes.lastAccessTime().toInstant().atZone(ZoneId.systemDefault()).getZone());
                    LocalDateTime currentDateTime = LocalDateTime.now();

                    Duration duration = Duration.between(lastAccessedDateTime, currentDateTime);
                    long minutesSinceLastAccess = duration.toMinutes();

                    int maxMinutesSinceLastAccess = 30;
                    if (minutesSinceLastAccess > maxMinutesSinceLastAccess) {
                        if (file.delete()) {
                            System.out.println("File deleted: " + file.getName());
                        } else {
                            System.out.println("Failed to delete file: " + file.getName());
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
