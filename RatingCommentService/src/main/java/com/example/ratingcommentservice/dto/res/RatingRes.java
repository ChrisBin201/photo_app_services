package com.example.ratingcommentservice.dto.res;

import com.example.ratingcommentservice.model.Comment;
import com.example.ratingcommentservice.model.Rating;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RatingRes {
    private long id;
    private String message;
    private int rating;
    //    @Column(name = "user_id")
//    private Long userId;
    private Long userId;
    private String username;
    private String photoId;
    private String lastUpdate;
    public RatingRes(Rating rating){
        id = rating.getId();
        message= rating.getMessage();
        this.rating = rating.getRating();
        userId = rating.getUser().getId();
        username = rating.getUser().getUsername();
        if(rating.getPhotoId()!= null) photoId = rating.getPhotoId();
        lastUpdate = rating.getLastModifiedDate()!=null? rating.getLastModifiedDate().toString(): null;
    }
}
