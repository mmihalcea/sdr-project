package edu.sdr.electronics.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AddQuestionRequest {

    private String text;
    private List<AddAnswerRequest> answers;

}
