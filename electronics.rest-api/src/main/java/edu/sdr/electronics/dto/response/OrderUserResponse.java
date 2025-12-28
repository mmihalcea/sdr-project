package edu.sdr.electronics.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderUserResponse extends StoreUserResponse {
    private String name;
    private UserAddressResponse address;
}
