package com.example.notification_service.service;

import com.example.notification_service.model.Note;
import com.example.notification_service.model.NotificationModel;
import com.example.notification_service.repo.AbstractFirestoreRepository;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Service
public class FirebaseMessagingService  {
//    private final FirebaseMessaging firebaseMessaging;
    private static final String COLLECTION_NAME = "notifications";

//    protected FirebaseMessagingService(Firestore firestore, String collection) {
//        super(firestore, collection);
//    }

//    public FirebaseMessagingService(FirebaseMessaging firebaseMessaging) {
//        this.firebaseMessaging = firebaseMessaging;
//    }


//    public String sendNotification(Note note, String token) throws FirebaseMessagingException {
//
//        Notification notification = Notification
//                .builder()
//                .setTitle(note.getSubject())
//                .setBody(note.getContent())
//                .build();
//
//        Message message = Message
//                .builder()
//                .setToken(token)
//                .setNotification(notification)
//                .putAllData(note.getData())
//                .build();
//
//        return firebaseMessaging.send(message);
//    }

    public String sendNotification(NotificationModel notification) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> collectionApiFuture = dbFirestore.collection(COLLECTION_NAME).document(notification.getId().toString()).create(notification);
        return collectionApiFuture.get().getUpdateTime().toString();
    }

}
