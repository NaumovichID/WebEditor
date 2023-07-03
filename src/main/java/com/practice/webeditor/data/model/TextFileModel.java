package com.practice.webeditor.data.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
public class TextFileModel {
    private String fileName;
    private String content;
    private UUID fileId;
}
