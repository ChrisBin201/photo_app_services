package com.example.notification_service.controller;

import com.example.notification_service.model.MessageResponse;
import com.example.notification_service.model.NotificationModel;
import com.example.notification_service.repo.NotificationRepo;
import com.example.notification_service.service.FirebaseMessagingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/send-notification")
@Slf4j
public class Controller {

    @Autowired
    NotificationRepo repo;
    @Autowired
    FirebaseMessagingService firebaseMessagingService;

    @PostMapping("/new-comment/{receiveId}")
    public ResponseEntity<NotificationModel> comment(@PathVariable String receiveId,
                                                                      @RequestParam("photoId") String photoId,
                                                                      @RequestHeader("X-auth-user-id") String userId,
                                                                   @RequestHeader(HttpHeaders.AUTHORIZATION) String token) throws ExecutionException, InterruptedException {
        log.info("USER_REQUEST: "+userId);
        MessageResponse<NotificationModel> response = new  MessageResponse();
        NotificationModel notification = new NotificationModel();

        notification.setObjectReceiveId(Long.parseLong(receiveId));
        notification.setObjectSendId(Long.parseLong(userId));
        notification.setType(NotificationModel.Type.COMMENT);
        notification.setStatus(NotificationModel.Status.UNSEEN);
        notification.setPhotoId(photoId);
        notification.setContent("You have new comment");

        notification = repo.save(notification);
        firebaseMessagingService.sendNotification(notification);
        response.success(notification);
        response.setClient(Long.parseLong(userId));
        return  ResponseEntity.ok(notification);

    }
    @PostMapping("/new-rating/{receiveId}")
    public ResponseEntity<NotificationModel> rating(@PathVariable String receiveId,
                                                                   @RequestParam("photoId") String photoId,
                                                                   @RequestHeader("X-auth-user-id") String userId,
                                                                   @RequestHeader(HttpHeaders.AUTHORIZATION) String token) throws ExecutionException, InterruptedException {
        log.info("USER_REQUEST: "+userId);
        MessageResponse<NotificationModel> response = new  MessageResponse();
        NotificationModel notification = new NotificationModel();

        notification.setObjectReceiveId(Long.parseLong(receiveId));
        notification.setObjectSendId(Long.parseLong(userId));
        notification.setType(NotificationModel.Type.RATING);
        notification.setStatus(NotificationModel.Status.UNSEEN);
        notification.setPhotoId(photoId);
        notification.setContent("You have new rating");

        notification = repo.save(notification);
        firebaseMessagingService.sendNotification(notification);
        response.success(notification);
        response.setClient(Long.parseLong(userId));
        return  ResponseEntity.ok(notification);

    }
    @PostMapping("/new-following/{receiveId}")
    public ResponseEntity<MessageResponse<NotificationModel>> following(@PathVariable String receiveId,
                                                                        @RequestHeader("X-auth-user-id") String userId,
                                                                   @RequestHeader(HttpHeaders.AUTHORIZATION) String token) throws ExecutionException, InterruptedException {
        log.info("USER_REQUEST: "+userId);
        MessageResponse<NotificationModel> response = new  MessageResponse();
        NotificationModel notification = new NotificationModel();

        notification.setObjectReceiveId(Long.parseLong(receiveId));
        notification.setObjectSendId(Long.parseLong(userId));
        notification.setType(NotificationModel.Type.FOLLOW);
        notification.setStatus(NotificationModel.Status.UNSEEN);
        notification.setContent("You have new follow");

        notification = repo.save(notification);
        firebaseMessagingService.sendNotification(notification);
        response.success(notification);
        response.setClient(Long.parseLong(userId));
        return  ResponseEntity.ok(response);

    }
}
