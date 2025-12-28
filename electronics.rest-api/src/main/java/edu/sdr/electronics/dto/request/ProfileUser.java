package edu.sdr.electronics.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProfileUser extends BaseUser {
    private byte[] profilePic;
    private List<Long> categories;
}
