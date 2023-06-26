package com.example.ratingcommentservice.dto.res;

import com.example.ratingcommentservice.model.Auditable;
import com.example.ratingcommentservice.model.Comment;
import com.example.ratingcommentservice.model.outer.User;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class CommentRes extends Auditable implements Serializable {
    private long id;
    private String message;
    //    @Column(name = "user_id")
//    private Long userId;
    private Long userId;
    private String username;
    private String photoId;
    private Long postId;
    private String lastUpdate;

    public CommentRes(Comment comment){
        id = comment.getId();
        message= comment.getMessage();
        userId = comment.getUser().getId();
        username = comment.getUser().getUsername();
        if(comment.getPhotoId()!= null) photoId = comment.getPhotoId();
        if(comment.getPostId()!= null) postId = comment.getPostId();
        lastUpdate = comment.getLastModifiedDate()!=null? comment.getLastModifiedDate().toString(): null;

    }
}
