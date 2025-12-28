package edu.sdr.electronics.controller;


import edu.sdr.electronics.dto.request.PostRequest;
import edu.sdr.electronics.dto.response.PostResponse;
import edu.sdr.electronics.dto.response.SocialUserResponse;
import edu.sdr.electronics.service.SocialService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/social")
@AllArgsConstructor
@PreAuthorize("hasRole('USER')")
public class SocialController {

    private final SocialService socialService;


    @GetMapping("/feed")
    public ResponseEntity<List<PostResponse>> getFeed() {
        return new ResponseEntity<>(socialService.getFeed(), HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<SocialUserResponse>> searchUser(@RequestParam String name) {
        return new ResponseEntity<>(socialService.searchUser(name), HttpStatus.OK);
    }

    @PutMapping("/like/{postId}")
    public ResponseEntity<List<PostResponse>> likePost(@PathVariable Long postId) {
        return new ResponseEntity<>(socialService.likePost(postId), HttpStatus.OK);
    }

    @PostMapping("/follow/{userId}")
    public ResponseEntity<List<SocialUserResponse>> followUser(@PathVariable Long userId, @RequestParam String name) {
        return new ResponseEntity<>(socialService.followUser(userId, name),HttpStatus.OK);
    }

    @PostMapping("/add-post")
    public ResponseEntity<List<PostResponse>> addPost(@RequestBody PostRequest postRequest) {
        return new ResponseEntity<>(socialService.addPost(postRequest), HttpStatus.OK);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<SocialUserResponse> searchUser(@PathVariable Long id) {
        return new ResponseEntity<>(socialService.getUserDetails(id), HttpStatus.OK);
    }

}
