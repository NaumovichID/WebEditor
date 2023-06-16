package com.practice.webeditor.repository;

import com.google.firebase.database.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Repository
public class FirebaseRepository {
    private final FirebaseDatabase firebaseDatabase;

    @Autowired
    public FirebaseRepository(FirebaseDatabase firebaseDatabase) {
        this.firebaseDatabase = firebaseDatabase;
    }

    public void writeData(String path, Object data) {
        DatabaseReference ref = firebaseDatabase.getReference(path);
        ref.setValueAsync(data);
    }

    public <T> T readData(String path, Class<T> valueType) {
        DatabaseReference ref = firebaseDatabase.getReference(path);
        DatabaseReference.CompletionListener completionListener =
                (databaseError, databaseReference) -> {
                    // Обработка ошибок при чтении данных
                };

        CompletableFuture<T> future = new CompletableFuture<>();
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                T value = dataSnapshot.getValue(valueType);
                future.complete(value);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                future.completeExceptionally(databaseError.toException());
            }
        });

        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            // Обработка исключений
        }

        return null;
    }
}

