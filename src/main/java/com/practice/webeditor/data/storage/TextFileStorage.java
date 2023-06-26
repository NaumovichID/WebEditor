package com.practice.webeditor.data.storage;

import com.practice.webeditor.data.model.TextFileModel;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Component
public class TextFileStorage {
    private final HashMap<UUID, TextFileModel> fileModelHashMap = new HashMap<>();

    public void addOrUpdateFile(TextFileModel fileModel) {
        fileModelHashMap.put(fileModel.getFileId(), fileModel);
    }

    public TextFileModel getFileByUUID(UUID fileId) {
        return fileModelHashMap.get(fileId);
    }

    public List<TextFileModel> getAllFiles() {
        return fileModelHashMap.values().stream().toList();
    }

    public void deleteFileByUUID(UUID fileId) {
        fileModelHashMap.remove(fileId);
    }
}
