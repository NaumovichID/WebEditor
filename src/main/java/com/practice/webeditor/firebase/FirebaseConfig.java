package com.practice.webeditor.firebase;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.FirebaseDatabase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;
import java.io.IOException;

@Configuration
public class FirebaseConfig {

    @PostConstruct
    public FirebaseApp init() throws IOException {
        FileInputStream serviceAccount =
                new FileInputStream("webeditor-c7306-firebase-adminsdk-82qfs-b7167b585b.json");

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setDatabaseUrl("https://webeditor-c7306-default-rtdb.firebaseio.com")
                .build();

        return FirebaseApp.initializeApp(options);
    }
    @Bean
    public FirebaseDatabase firebaseDatabase() throws IOException {
        return FirebaseDatabase.getInstance(init());
    }
}
