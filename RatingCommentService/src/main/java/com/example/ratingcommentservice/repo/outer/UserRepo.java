package com.example.ratingcommentservice.repo.outer;

import com.example.ratingcommentservice.model.Comment;
import com.example.ratingcommentservice.model.outer.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User,Long> {
}
