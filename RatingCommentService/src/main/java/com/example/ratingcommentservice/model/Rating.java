package com.example.ratingcommentservice.model;

import com.example.ratingcommentservice.model.outer.User;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
@EntityListeners(Rating.LastUpdateListener.class )
public class Rating extends Auditable implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private int rating;
    private String message;
//    @Column(name = "user_id")
//    private Long userId;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private User user;
    @Column(name = "photo_id")
    private String photoId;

    public static class LastUpdateListener {

        @PreUpdate
        @PrePersist
        public void setLastUpdate( Rating c ) {
            c.setLastModifiedDate( LocalDateTime.now() );
        }
    }
}
