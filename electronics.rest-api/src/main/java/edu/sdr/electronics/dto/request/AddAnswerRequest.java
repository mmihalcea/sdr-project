package edu.sdr.electronics.dto.request;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddAnswerRequest {
    private String text;
    private Long category;

}
