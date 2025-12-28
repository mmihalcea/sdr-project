package edu.sdr.electronics.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseUser {
    private String name;
    private String username;
    private String email;
    private String password;

}
