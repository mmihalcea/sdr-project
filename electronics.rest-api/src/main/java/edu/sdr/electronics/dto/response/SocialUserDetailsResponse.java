package edu.sdr.electronics.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class SocialUserDetailsResponse extends SocialUserResponse {

    private Set<SocialUserResponse> followers;
    private String email;

}
