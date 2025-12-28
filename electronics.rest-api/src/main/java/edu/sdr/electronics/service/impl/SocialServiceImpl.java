package edu.sdr.electronics.service.impl;

import edu.sdr.electronics.domain.Post;
import edu.sdr.electronics.domain.StoreUser;
import edu.sdr.electronics.dto.request.PostRequest;
import edu.sdr.electronics.dto.response.PostResponse;
import edu.sdr.electronics.dto.response.SocialUserDetailsResponse;
import edu.sdr.electronics.dto.response.SocialUserResponse;
import edu.sdr.electronics.repository.PostRepository;
import edu.sdr.electronics.repository.StoreUserRepository;
import edu.sdr.electronics.service.SocialService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Log4j2
public class SocialServiceImpl implements SocialService {


    private final PostRepository postRepository;
    private final StoreUserRepository storeUserRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<PostResponse> likePost(Long postId) {
        postRepository.findById(postId).ifPresent(p -> {
            Principal principal = SecurityContextHolder.getContext().getAuthentication();
            Optional<StoreUser> storeUser = storeUserRepository.findByUsername(principal.getName());
            storeUser.ifPresent(u -> {
                if (p.getLikedBy().stream().noneMatch(user -> user.getId().equals(storeUser.get().getId()))) {
                    p.setNoOfLikes(p.getNoOfLikes() + 1);
                    p.getLikedBy().add(storeUser.get());

                } else {
                    p.setNoOfLikes(p.getNoOfLikes() - 1);
                    p.setLikedBy(p.getLikedBy().stream().filter(user -> !user.getId().equals(storeUser.get().getId())).collect(Collectors.toSet()));
                }
                postRepository.save(p);
            });
        });
        return this.getFeed();
    }

    @Override
    public List<SocialUserResponse> followUser(Long userId, String nameToSearch) {
        Principal principal = SecurityContextHolder.getContext().getAuthentication();
        Optional<StoreUser> storeUser = storeUserRepository.findByUsername(principal.getName());
        storeUser.flatMap(user -> storeUserRepository.findById(userId)).ifPresent(userToFollow -> {
            if (userToFollow.getFollowers().contains(storeUser.get())) {
                userToFollow.getFollowers().remove(storeUser.get());
            } else {
                userToFollow.getFollowers().add(storeUser.get());
            }

            storeUserRepository.save(userToFollow);
        });
        return this.searchUser(nameToSearch);
    }

    @Override
    public List<SocialUserResponse> searchUser(String name) {
        Principal principal = SecurityContextHolder.getContext().getAuthentication();
        Optional<StoreUser> storeUser = storeUserRepository.findByUsername(principal.getName());
        Converter<Set<StoreUser>, Boolean> followersConverter =
                ctx -> ctx.getSource() == null ? null : ctx.getSource().contains(storeUser.get());
        modelMapper.typeMap(StoreUser.class, SocialUserResponse.class)
                .addMapping(src -> src.getAddress().getCity(), SocialUserResponse::setCity)
                .addMappings(mapper -> mapper.using(followersConverter).map(StoreUser::getFollowers, SocialUserResponse::setFollowing));
        return modelMapper.map(storeUserRepository.findByUsernameContaining(name), new TypeToken<List<SocialUserResponse>>() {
        }.getType());
    }

    @Override
    public SocialUserDetailsResponse getUserDetails(Long userId) {
        SocialUserDetailsResponse userDetailedResponse = null;
        if (userId != null) {
            Optional<StoreUser> storeUser = this.storeUserRepository.findById(userId);
            if (storeUser.isPresent()) {
                userDetailedResponse = modelMapper.map(storeUser.get(), SocialUserDetailsResponse.class);
            }
        }
        return userDetailedResponse;
    }

    @Override
    public List<PostResponse> getFeed() {
        Principal principal = SecurityContextHolder.getContext().getAuthentication();
        List<PostResponse> response = new ArrayList<>();
        Optional<StoreUser> storeUser = storeUserRepository.findByUsername(principal.getName());
        if (storeUser.isPresent()) {
            List<Long> following = storeUserRepository.findAllFollowing(storeUser.get().getId());
            following.add(storeUser.get().getId());
            postRepository.findAllByUserIdInOrderByDateDesc(following).forEach(p -> {
                PostResponse postResponse = modelMapper.map(p, PostResponse.class);
                if (p.getLikedBy().stream().anyMatch(u -> u.getId().equals(storeUser.get().getId()))) {
                    postResponse.setAppreciated(true);
                }
                response.add(postResponse);
            });
            return response;
        }
        return null;
    }

    @Override
    public List<PostResponse> addPost(PostRequest postRequest) {
        Principal principal = SecurityContextHolder.getContext().getAuthentication();
        Optional<StoreUser> storeUser = storeUserRepository.findByUsername(principal.getName());
        if (storeUser.isPresent()) {
            Post post = new Post();
            post.setUser(storeUser.get());
            post.setNoOfLikes(0);
            post.setText(postRequest.getText());
            post.setDate(LocalDateTime.now());
            postRepository.save(post);
            return this.getFeed();
        }
        return null;
    }
}
