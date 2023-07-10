package com.practice.webeditor.data.storage;

import com.practice.webeditor.data.model.TextFileModel;
import jakarta.annotation.PostConstruct;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.InitCommand;
import org.eclipse.jgit.api.errors.*;
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

    @PostConstruct
    private void repositoryInit() {
        InitCommand initCommand = Git.init();
        initCommand.setDirectory(new File(storagePath));
        try {
            Git git = initCommand.call();
        } catch (GitAPIException ex) {
            throw new RuntimeException(ex);
        }

    }

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
            String filePath = storagePath + textFileModel.getFileId().toString() + ".json";
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(new File(filePath), textFileModel);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean commitTextFile(UUID fileID) {
        try {
            String filePath = storagePath + fileID + ".json";

            File gitFile = new File(filePath);

            try (Git git = Git.open(new File(storagePath))) {
                git.add().addFilepattern(gitFile.getName()).call();
                git.commit().setMessage("Add file: " + gitFile.getName()).call();
                return true;
            } catch (GitAPIException e) {
                throw new RuntimeException(e);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
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
    public void deleteLongUnusedFiles() {

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
