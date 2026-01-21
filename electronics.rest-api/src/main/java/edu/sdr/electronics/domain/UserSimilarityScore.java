package edu.sdr.electronics.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class UserSimilarityScore {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user1_id", nullable = false)
    private StoreUser user1;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user2_id", nullable = false)
    private StoreUser user2;

    @Column(nullable = false)
    private double score;

    public UserSimilarityScore(StoreUser user1, StoreUser user2, double score) {
        this.user1 = user1;
        this.user2 = user2;
        this.score = score;
    }
}
