package edu.sdr.electronics.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SocialUserResponse extends StoreUserResponse {
    private String name;
    private String city;
    private Long id;
    private boolean following;
}
