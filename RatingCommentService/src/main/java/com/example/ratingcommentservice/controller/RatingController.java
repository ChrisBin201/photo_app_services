package com.example.ratingcommentservice.controller;

import com.example.ratingcommentservice.dto.RatingDTO;
import com.example.ratingcommentservice.dto.MessageResponse;
import com.example.ratingcommentservice.dto.res.RatingRes;
import com.example.ratingcommentservice.model.Rating;
import com.example.ratingcommentservice.service.RatingService;
import com.example.ratingcommentservice.service.RatingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/rating",produces = "application/json")
@CrossOrigin(origins = "*")
@Slf4j
public class RatingController {
    @Autowired
    private RatingService ratingService;

    @PostMapping("/to-user/{id}")
    public ResponseEntity<MessageResponse<RatingRes>> create(@RequestBody RatingDTO dto,
                                                             @PathVariable("id") String toUserId,
                                                             @RequestHeader("X-auth-user-id") String userId,
                                                             @RequestHeader(HttpHeaders.AUTHORIZATION) String token){
        log.info("USER_REQUEST: "+userId);
        dto.setUserId(Long.parseLong(userId));
        MessageResponse<RatingRes> response = new  MessageResponse();
        response.success(ratingService.create(dto,token,toUserId));
        response.setClient(Long.parseLong(userId));
        return  ResponseEntity.ok(response);

    }
    @GetMapping("/photo/{id}")
    public ResponseEntity<MessageResponse<List<RatingRes>>> getAllByPhoto(@PathVariable String id,
                                                                       @RequestHeader("X-auth-user-id") String userId) {
        log.info("USER_REQUEST: "+userId);
        MessageResponse<List<RatingRes>> response = new  MessageResponse();
        response.success(ratingService.getAllByPhoto(id));
        response.setClient(Long.parseLong(userId));
        return ResponseEntity.ok(response);
    }
    @PutMapping
    public ResponseEntity<MessageResponse<RatingRes>> update(@RequestBody RatingDTO dto){
        MessageResponse<RatingRes> response = new  MessageResponse();
        response.success(ratingService.update(dto));
        return  ResponseEntity.ok(response);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse<String>> deleteById(@PathVariable String id) {
        MessageResponse<String> response = new  MessageResponse();
        try {
            ratingService.deleteById(Long.parseLong(id));
            response.success("Success");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.error(e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }
}
