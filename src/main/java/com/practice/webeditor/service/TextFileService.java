package com.practice.webeditor.service;

import com.practice.webeditor.data.model.TextFileModel;
import com.practice.webeditor.data.storage.TextFileStorage;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class TextFileService {
    public final TextFileStorage textFileStorage;

    public void addOrUpdateFile(TextFileModel textFileModel) {
        textFileStorage.addOrUpdateFile(textFileModel);
    }

    public TextFileModel getFile(UUID fileId) {
        return textFileStorage.getFileByUUID(fileId);
    }

    public List<TextFileModel> getAll() {
        return textFileStorage.getAllFiles();
    }

    public void deleteFile(UUID fileId) {
        textFileStorage.deleteFileByUUID(fileId);
    }
}
