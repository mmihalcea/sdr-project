package edu.sdr.electronics.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PostResponse {

    private Long id;
    private String text;
    private LocalDateTime date;
    private Integer noOfLikes;
    private StoreUserResponse user;
    private boolean appreciated;
}
