package edu.sdr.electronics.service;

import edu.sdr.electronics.dto.request.PostRequest;
import edu.sdr.electronics.dto.response.PostResponse;
import edu.sdr.electronics.dto.response.SocialUserDetailsResponse;
import edu.sdr.electronics.dto.response.SocialUserResponse;

import java.util.List;

public interface SocialService {

    List<PostResponse> likePost(Long postId);

    List<SocialUserResponse> followUser(Long userId, String nameToSearch);

    List<SocialUserResponse> searchUser(String name);

    SocialUserDetailsResponse getUserDetails(Long userId);

    List<PostResponse> getFeed();

    List<PostResponse> addPost(PostRequest postRequest);


}
