package com.practice.webeditor.repository;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.practice.webeditor.data.model.TextFileModel;

public class TextFileRepository {
    private final DatabaseReference databaseReference;

    public TextFileRepository(DatabaseReference databaseReference) {
        this.databaseReference = databaseReference.child("textFiles");
    }

    public void saveTextFile(TextFileModel textFile) {
        DatabaseReference fileReference = databaseReference.push();
        String recordId = fileReference.getKey();
        fileReference.setValueAsync(textFile);
    }

    public void updateTextFile(String fileId, TextFileModel updatedTextFile) {
        DatabaseReference fileReference = databaseReference.child(fileId);
        fileReference.setValueAsync(updatedTextFile);
    }

    public void deleteTextFile(String fileId) {
        DatabaseReference fileReference = databaseReference.child(fileId);
        fileReference.removeValueAsync();
    }

    public void getTextFile(String fileId, ValueEventListener valueEventListener) {
        DatabaseReference fileReference = databaseReference.child(fileId);
        ValueEventListener fileListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                TextFileModel textFileModel = dataSnapshot.getValue(TextFileModel.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        fileReference.addListenerForSingleValueEvent(fileListener);
    }

}
