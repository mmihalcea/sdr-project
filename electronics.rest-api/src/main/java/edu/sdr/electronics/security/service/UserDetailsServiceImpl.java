package edu.sdr.electronics.security.service;


import edu.sdr.electronics.domain.StoreUser;
import edu.sdr.electronics.repository.StoreUserRepository;
import edu.sdr.electronics.security.UserPrinciple;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final StoreUserRepository storeUserRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        StoreUser user = storeUserRepository.findByUsername(email).orElseThrow(
                () -> new UsernameNotFoundException("User Not Found with -> email : " + email));

        return UserPrinciple.build(user);
    }
}
