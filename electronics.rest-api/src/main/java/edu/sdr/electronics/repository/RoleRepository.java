package edu.sdr.electronics.repository;


import edu.sdr.electronics.domain.Role;
import edu.sdr.electronics.utils.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

    Optional<Role> findByName(RoleName roleName);

}