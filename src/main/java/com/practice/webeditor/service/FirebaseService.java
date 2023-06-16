package com.practice.webeditor.service;

import com.practice.webeditor.repository.FirebaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FirebaseService {
    private final FirebaseRepository firebaseRepository;

    @Autowired
    public FirebaseService(FirebaseRepository firebaseRepository) {
        this.firebaseRepository = firebaseRepository;
    }

//    public void writeDataToFirebase() {
//        String path = "path/to/data";
//        Object data = // Ваш объект данных для записи
//
//                firebaseRepository.writeData(path, data);
//    }
//
//    public void readDataFromFirebase() {
//        String path = "path/to/data";
//        Class<MyDataClass> valueType = MyDataClass.class;
//
//        MyDataClass data = firebaseRepository.readData(path, valueType);
//        // Используйте полученные данные
//    }
}
