package edu.sdr.electronics.repository;

import edu.sdr.electronics.domain.StoreUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface StoreUserRepository extends JpaRepository<StoreUser, Long> {

    Optional<StoreUser> findByUsername(String username);

    List<StoreUser> findByUsernameContaining(String username);

    Optional<StoreUser> findByEmail(String email);

    List<StoreUser> findAllByRolesIdIn(List<Integer> roles);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    @Query(value = "select s.user_id from store_user_follower s where s.follower_id=:userId", nativeQuery = true)
    List<Long> findAllFollowing(Long userId);

}
