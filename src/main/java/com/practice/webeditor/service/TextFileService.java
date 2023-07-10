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

    public void addFile(TextFileModel textFileModel) {
        textFileStorage.saveTextFile(textFileModel);
    }

    public TextFileModel getFile(UUID fileId) {
        return textFileStorage.readTextFileByUUID(fileId);
    }

    public boolean commitFile(UUID fileId) {
        return textFileStorage.commitTextFile(fileId);
    }
    public List<TextFileModel> getAll() {
        return textFileStorage.getAllFiles();
    }

    public boolean deleteFile(UUID fileId) {
        return textFileStorage.deleteFileByUUID(fileId);
    }
}
