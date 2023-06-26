package com.example.notification_service.model;

import lombok.Data;

import javax.persistence.*;
@Data
public class NotificationDTO {
    private Long id;
    private Long ObjectReceiveId;
    @Enumerated(EnumType.STRING)
    private NotificationModel.Type type;
    private Long ObjectSendId;
    private String content;
    @Enumerated(EnumType.STRING)
    private NotificationModel.Status status;
}
