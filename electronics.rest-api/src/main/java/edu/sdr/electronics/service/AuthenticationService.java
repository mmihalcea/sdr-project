package edu.sdr.electronics.service;

import edu.sdr.electronics.domain.County;
import edu.sdr.electronics.domain.StoreUser;

import java.util.List;

public interface AuthenticationService {

    StoreUser registerUser(StoreUser user, String userAgent);

    void updateUserAgent(String username, String userAgent);

    void forgotPassword(String email);

    List<County> getCounties();
}
