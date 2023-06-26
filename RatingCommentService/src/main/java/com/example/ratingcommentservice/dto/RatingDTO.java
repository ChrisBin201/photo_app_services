package com.example.ratingcommentservice.dto;

import com.example.ratingcommentservice.model.Auditable;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;

@Data
public class RatingDTO extends Auditable implements Serializable {
    @JsonIgnoreProperties(ignoreUnknown = true)
    private Long id;
    private int rating;
    private String message;
    private Long userId;
    private String photoId;
}
