package edu.sdr.electronics.service.impl;

import edu.sdr.electronics.domain.Category;
import edu.sdr.electronics.domain.StoreUser;
import edu.sdr.electronics.dto.request.ProfileUser;
import edu.sdr.electronics.repository.StoreUserRepository;
import edu.sdr.electronics.service.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final PasswordEncoder encoder;
    private final ModelMapper modelMapper;
    private final StoreUserRepository userRepository;
    private final StoreUserRepository storeUserRepository;


    @Override
    public ProfileUser getUserDetails(String username) {
        Optional<StoreUser> user = this.userRepository.findByUsername(username);
        Converter<Set<Category>, List<Long>> categoryConverter =
                ctx -> ctx.getSource().stream().map(Category::getId).collect(Collectors.toList());
        modelMapper.typeMap(StoreUser.class, ProfileUser.class)
                .addMappings(mapper -> mapper.using(categoryConverter).map(StoreUser::getCategories, ProfileUser::setCategories));
        return user.map(value -> this.modelMapper.map(value, ProfileUser.class)).orElse(null);
    }

    @Override
    public void updateUser(ProfileUser profileUer) {
        StoreUser user = this.userRepository.findByUsername(profileUer.getUsername()).orElse(null);
        if (user != null) {
            if (profileUer.getPassword() != null && !profileUer.getPassword().isEmpty()) {
                user.setPassword(this.encoder.encode(profileUer.getPassword()));
            }
            user.setEmail(profileUer.getEmail());
            user.setProfilePic(profileUer.getProfilePic());
            this.userRepository.save(this.modelMapper.map(user, StoreUser.class));
        }

    }

    @Override
    public void updateCategories(List<Long> categories) {
        Principal principal = SecurityContextHolder.getContext().getAuthentication();
        StoreUser storeUser = this.storeUserRepository.findByUsername(principal.getName()).orElse(null);
        if (storeUser != null) {
            storeUser.setCategories(categories.stream().map(Category::new).collect(Collectors.toSet()));
            storeUserRepository.save(storeUser);
        }

    }

}
