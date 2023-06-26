package com.example.ratingcommentservice.service;

import com.corundumstudio.socketio.SocketIOServer;
import com.example.ratingcommentservice.dto.CommentDTO;
import com.example.ratingcommentservice.dto.MessageResponse;
import com.example.ratingcommentservice.dto.NotificationModel;
import com.example.ratingcommentservice.dto.res.CommentRes;
import com.example.ratingcommentservice.model.Comment;
import com.example.ratingcommentservice.model.outer.User;
import com.example.ratingcommentservice.repo.CommentRepo;
import com.example.ratingcommentservice.repo.outer.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CommentService {
    private static final Logger logger = LoggerFactory.getLogger(CommentService.class);
    @Autowired
    private CommentRepo commentRepo;
    @Autowired
    private UserRepo userRepo;
//    @Autowired
//    private SocketIOServer socketServer;
    private WebClient webClient = WebClient.create();

    public CommentRes create(CommentDTO dto, String token, String toUserId){
        Comment comment = new Comment();
        comment.setMessage(dto.getMessage());
        Optional<User> optionalUser = userRepo.findById(dto.getUserId());
        if(optionalUser.isEmpty()){
            User user = webClient.get()
                    .uri("https://jsqedvglu56wklwlbxfhvc2ude.srv.us/api/user/getUserById/"+dto.getUserId()+"?photoId="+dto.getPhotoId())
                    .header(HttpHeaders.AUTHORIZATION, token)
                    .retrieve()
                    .bodyToMono(User.class)
                    .block();
            comment.setUser(user);
            log.info("USER CALL: "+user.getId()+" "+user.getUsername());
        }
        else {
            comment.setUser(optionalUser.get());
            log.info("USER: "+optionalUser.get().getId()+" "+optionalUser.get().getUsername());
        }
        if(dto.getPhotoId() != null) comment.setPhotoId(dto.getPhotoId());
        if(dto.getPhotoId() != null) comment.setPostId(dto.getPostId());
        CommentRes commentRes = new CommentRes(commentRepo.save(comment));
        log.info(commentRes.getPhotoId());
        webClient.post()
                .uri("http://localhost:8082/api/send-notification/new-comment/"+toUserId+"?photoId="+commentRes.getPhotoId())
                .header(HttpHeaders.AUTHORIZATION, token)
                .header("X-auth-user-id", String.valueOf(dto.getUserId()))
                .retrieve()
                .bodyToMono(NotificationModel.class).block();
        return commentRes ;

    }

    public List<CommentRes> getAllByPost(long id) {
        List<Comment> list = commentRepo.findByPostId(id);
        return list.stream().map(comment -> new CommentRes(comment)).collect(Collectors.toList());
    }
    public List<CommentRes> getAllByPhoto(String id) {
        List<Comment> list = commentRepo.findByPhotoId(id);
        return list.stream().map(comment -> new CommentRes(comment)).collect(Collectors.toList());
    }

    public CommentRes update(CommentDTO dto){
        Comment comment = commentRepo.findById(dto.getId()).get();
        comment.setMessage(dto.getMessage());
//        comment.setLastModifiedDate(dto.getLastModifiedDate());
        return new CommentRes(commentRepo.save(comment)) ;
    }

    public boolean deleteById(long id) {
        try {
            commentRepo.deleteById(id);
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return false;
        }
    }

}
