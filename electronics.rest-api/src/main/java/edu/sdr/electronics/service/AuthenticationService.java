package edu.sdr.electronics.service;

import edu.sdr.electronics.domain.County;
import edu.sdr.electronics.domain.StoreUser;

import java.util.List;

public interface AuthenticationService {

    StoreUser registerUser(StoreUser user);

    void forgotPassword(String email);

    List<County> getCounties();
}
