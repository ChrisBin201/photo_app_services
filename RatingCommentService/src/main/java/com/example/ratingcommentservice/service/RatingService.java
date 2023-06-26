package com.example.ratingcommentservice.service;

import com.example.ratingcommentservice.dto.CommentDTO;
import com.example.ratingcommentservice.dto.NotificationModel;
import com.example.ratingcommentservice.dto.RatingDTO;
import com.example.ratingcommentservice.dto.res.CommentRes;
import com.example.ratingcommentservice.dto.res.RatingRes;
import com.example.ratingcommentservice.model.Comment;
import com.example.ratingcommentservice.model.Rating;
import com.example.ratingcommentservice.model.outer.User;
import com.example.ratingcommentservice.repo.CommentRepo;
import com.example.ratingcommentservice.repo.RatingRepo;
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
public class RatingService {
    private static final Logger logger = LoggerFactory.getLogger(RatingService.class);
    @Autowired
    private RatingRepo ratingRepo;
    @Autowired
    private UserRepo  userRepo;
    private WebClient webClient = WebClient.create();

    public RatingRes create(RatingDTO dto,String token, String toUserId){
        Rating rating = new Rating();
        rating.setRating(dto.getRating());
        rating.setMessage(dto.getMessage());
        Optional<User> optionalUser = userRepo.findById(dto.getUserId());
        if(optionalUser.isEmpty()){
            User user = webClient.get()
                    .uri("http://localhost:8080/api/user/getUserById/"+dto.getUserId()+"?photoId="+dto.getPhotoId())
                    .header(HttpHeaders.AUTHORIZATION,"Bearer "+ token)
                    .retrieve()
                    .bodyToMono(User.class)
                    .block();
            rating.setUser(user);
            log.info("USER CALL: "+user.getId()+" "+user.getUsername());
        }
        else {
            rating.setUser(optionalUser.get());
            log.info("USER: "+optionalUser.get().getId()+" "+optionalUser.get().getUsername());
        }
        rating.setPhotoId(dto.getPhotoId());
        RatingRes ratingRes = new RatingRes(ratingRepo.save(rating));
        log.info(ratingRes.getPhotoId());
        webClient.post()
                .uri("http://localhost:8082/api/send-notification/new-rating/"+toUserId+"?photoId="+ratingRes.getPhotoId())
                .header(HttpHeaders.AUTHORIZATION, token)
                .header("X-auth-user-id", String.valueOf(dto.getUserId()))
                .retrieve()
                .bodyToMono(NotificationModel.class).block();
        return ratingRes;

    }

    public List<RatingRes> getAllByPhoto(String id) {
        List<Rating> list = ratingRepo.findByPhotoId(id);
        return list.stream().map(rating -> new RatingRes(rating)).collect(Collectors.toList());
    }

    public RatingRes update(RatingDTO dto){
        Rating rating = ratingRepo.findById(dto.getId()).get();
        rating.setRating(dto.getRating());
        rating.setMessage(dto.getMessage());
//        rating.setLastModifiedDate(dto.getLastModifiedDate());
        return new RatingRes(ratingRepo.save(rating));

    }

    public boolean deleteById(long id) {
        try {
            ratingRepo.deleteById(id);
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return false;
        }
    }
}
