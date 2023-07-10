package com.practice.webeditor.controller;

import com.practice.webeditor.data.model.TextFileModel;
import com.practice.webeditor.service.TextFileService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("/api/files")
@CrossOrigin(origins = {"http://localhost:8888/"})
public class TextFileController {
    private final TextFileService textFileService;

    @PostMapping
    public ResponseEntity<TextFileModel> uploadFile(@RequestBody TextFileModel textFileModel) {
        textFileModel.setFileId(UUID.randomUUID());
        textFileService.addFile(textFileModel);
        return new ResponseEntity<>(textFileModel, HttpStatus.OK);
    }

    @PutMapping("/{fileId}")
    public ResponseEntity<TextFileModel> updateFile(@PathVariable("fileId") String fileId, @RequestBody TextFileModel textFileModel) {
        if (textFileService.getFile(UUID.fromString(fileId)) != null) {
            textFileModel.setFileId(UUID.fromString(fileId));
            textFileService.addFile(textFileModel);
            return new ResponseEntity<>(textFileModel, HttpStatus.OK);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/commit/{fileId}")
    public ResponseEntity<?> commitFile(@PathVariable("fileId") String fileId) {
        return textFileService.commitFile(UUID.fromString(fileId)) ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{fileId}")
    public ResponseEntity<TextFileModel> getFile(@PathVariable("fileId") String fileId) {
        TextFileModel textFileModel = textFileService.getFile(UUID.fromString(fileId));
        return textFileModel != null ? new ResponseEntity<>(textFileModel, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping
    public ResponseEntity<List<TextFileModel>> getAll() {
        List<TextFileModel> files = textFileService.getAll();
        return files != null && !files.isEmpty() ? new ResponseEntity<>(files, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{fileId}")
    public ResponseEntity<?> deleteFile(@PathVariable("fileId") String fileId) {
        return textFileService.deleteFile(UUID.fromString(fileId)) ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}

