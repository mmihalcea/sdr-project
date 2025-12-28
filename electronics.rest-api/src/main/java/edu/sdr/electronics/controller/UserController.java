package edu.sdr.electronics.controller;

import edu.sdr.electronics.dto.request.ProfileUser;
import edu.sdr.electronics.dto.response.ResponseMessage;
import edu.sdr.electronics.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@AllArgsConstructor
@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
public class UserController {


    private final UserService userService;

    @PostMapping("/update")
    public ResponseEntity<?> updateUser(@RequestBody ProfileUser profileUser) {
        this.userService.updateUser(profileUser);
        return new ResponseEntity<>(new ResponseMessage("Informatii actualizate!"), HttpStatus.OK);
    }

    @GetMapping("/details")
    public ResponseEntity<?> getUserDetails(@RequestParam String username) {
        ProfileUser user = this.userService.getUserDetails(username);
        if (user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ResponseMessage("Detaliile utilizatoruli nu sunt disponibile"), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/update-categories")
    public ResponseEntity<ResponseMessage> updateCategories(@RequestBody List<Long> categories){
        userService.updateCategories(categories);
        return new ResponseEntity<>(new ResponseMessage("Informații actualizate"), HttpStatus.OK);
    }


}
