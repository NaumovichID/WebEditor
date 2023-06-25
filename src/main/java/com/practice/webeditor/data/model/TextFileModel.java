package com.practice.webeditor.data.model;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class TextFileModel {
    private String fileName;
    private String content;
    private UUID fileId;

    public TextFileModel(String fileName, String content, UUID fileId) {
        this.fileName = fileName;
        this.content = content;
        this.fileId = fileId;
    }
}
