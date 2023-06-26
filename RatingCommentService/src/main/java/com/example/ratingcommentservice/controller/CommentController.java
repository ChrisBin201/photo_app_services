package com.example.ratingcommentservice.controller;

import com.example.ratingcommentservice.dto.CommentDTO;
import com.example.ratingcommentservice.dto.MessageResponse;
import com.example.ratingcommentservice.dto.res.CommentRes;
import com.example.ratingcommentservice.model.Comment;
import com.example.ratingcommentservice.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/comment",produces = "application/json")
@CrossOrigin(origins = "*")
@Slf4j
public class CommentController {
    @Autowired
    private CommentService commentService;
    @PostMapping("/to-user/{id}")
    public ResponseEntity<MessageResponse<CommentRes>> create(@RequestBody CommentDTO dto,
                                                              @PathVariable("id") String toUserId,
                                                              @RequestHeader("X-auth-user-id") String userId,
                                                              @RequestHeader(HttpHeaders.AUTHORIZATION) String token){
        log.info("USER_REQUEST: "+userId);
        dto.setUserId(Long.parseLong(userId));
        MessageResponse<CommentRes> response = new  MessageResponse();
        response.success(commentService.create(dto,token,toUserId));
        response.setClient(Long.parseLong(userId));
        return  ResponseEntity.ok(response);

    }
    @GetMapping("/post/{id}")
    public ResponseEntity<MessageResponse<List<CommentRes>>> getAllByPost(@PathVariable String id) {
        MessageResponse<List<CommentRes>> response = new  MessageResponse();
        response.success(commentService.getAllByPost(Long.parseLong(id)));
        return ResponseEntity.ok(response);
    }
    @GetMapping("/photo/{id}")
    public ResponseEntity<MessageResponse<List<CommentRes>>> getAllByPhoto(@PathVariable String id,
                                                                        @RequestHeader("X-auth-user-id") String userId) {
        log.info("USER_REQUEST: "+userId);
        MessageResponse<List<CommentRes>> response = new  MessageResponse();
        response.success(commentService.getAllByPhoto(id));
        response.setClient(Long.parseLong(userId));
        return ResponseEntity.ok(response);
    }
    @PutMapping
    public ResponseEntity<MessageResponse<CommentRes>> update(@RequestBody CommentDTO dto,
                                                           @RequestHeader("X-auth-user-id") String userId){
        MessageResponse<CommentRes> response = new  MessageResponse();
        response.success(commentService.update(dto));
        return  ResponseEntity.ok(response);
    }
    @DeleteMapping("/{id}")
        public ResponseEntity<MessageResponse<String>> deleteById(@PathVariable String id,
                                                                  @RequestHeader("X-auth-user-id") String userId) {
        MessageResponse<String> response = new  MessageResponse();
        try {
            commentService.deleteById(Long.parseLong(id));
            response.success("Success");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.error(e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }
}
