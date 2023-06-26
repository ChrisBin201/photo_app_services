package com.example.ratingcommentservice.repo;

import com.example.ratingcommentservice.model.Comment;
import com.example.ratingcommentservice.model.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepo extends JpaRepository<Comment,Long> {
    List<Comment> findByPostId(long id);
    List<Comment> findByPhotoId(String id);

}
