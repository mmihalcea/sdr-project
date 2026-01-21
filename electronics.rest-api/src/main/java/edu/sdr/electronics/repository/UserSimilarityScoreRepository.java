package edu.sdr.electronics.repository;

import edu.sdr.electronics.domain.StoreUser;
import edu.sdr.electronics.domain.UserSimilarityScore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserSimilarityScoreRepository extends JpaRepository<UserSimilarityScore, Long> {
    List<UserSimilarityScore> findByUser1(StoreUser user);
}
