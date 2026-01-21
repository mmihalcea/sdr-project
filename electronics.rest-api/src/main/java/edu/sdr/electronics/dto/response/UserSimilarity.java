package edu.sdr.electronics.dto.response;

import edu.sdr.electronics.domain.StoreUser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserSimilarity {
    private StoreUser user;
    private double score;
}
