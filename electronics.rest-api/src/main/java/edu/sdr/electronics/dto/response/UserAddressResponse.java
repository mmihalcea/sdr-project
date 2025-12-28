package edu.sdr.electronics.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserAddressResponse {
    private String city;
    private Double lat;
    private Double lon;
    private String street;
    private String number;
    private String postCode;

}
