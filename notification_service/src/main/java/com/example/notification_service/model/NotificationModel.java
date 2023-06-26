package com.example.notification_service.model;

import com.example.notification_service.config.DocumentId;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
public class NotificationModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @DocumentId
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "object_receive_id")
    private Long ObjectReceiveId;
    @Enumerated(EnumType.STRING)
    private Type type;
    @Column(name = "object_send_id")
    private Long ObjectSendId;
    private String content;
    @Column(name = "photo_id")
    private String photoId;
    @Enumerated(EnumType.STRING)
    private Status status;
    public enum  Type {
        COMMENT, RATING, FOLLOW
    }

    public enum  Status {
        SEEN, UNSEEN
    }
}
