package com.example.ratingcommentservice.model;

import com.example.ratingcommentservice.model.outer.User;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
@EntityListeners(Comment.LastUpdateListener.class )
public class Comment extends Auditable implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String message;
//    @Column(name = "user_id")
//    private Long userId;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private User user;
    @Column(name = "photo_id")
    private String photoId;
    @Column(name = "post_id")
    private Long postId;

    public static class LastUpdateListener {

        @PreUpdate
        @PrePersist
        public void setLastUpdate( Comment c ) {
            c.setLastModifiedDate( LocalDateTime.now() );
        }
    }
}
