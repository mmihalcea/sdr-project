package edu.sdr.electronics.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuizAnswerResponse {
    private Long id;
    private String text;
    private Integer category;
}
