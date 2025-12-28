package edu.sdr.electronics.service;

import edu.sdr.electronics.dto.request.ProfileUser;

import java.util.List;

public interface UserService {

    ProfileUser getUserDetails(String username);


    void updateUser(ProfileUser user);

    void updateCategories(List<Long> categories);
}
