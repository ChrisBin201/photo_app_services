package com.example.ratingcommentservice.dto;

import lombok.Data;

import javax.persistence.*;

@Data
public class NotificationModel {
    private Long id;
    private Long ObjectReceiveId;
    @Enumerated(EnumType.STRING)
    private Type type;
    private Long ObjectSendId;
    private String content;
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
