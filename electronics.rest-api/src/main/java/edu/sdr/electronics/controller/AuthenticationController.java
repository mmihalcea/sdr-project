package edu.sdr.electronics.controller;

import edu.sdr.electronics.domain.County;
import edu.sdr.electronics.domain.StoreUser;
import edu.sdr.electronics.dto.request.LoginRequest;
import edu.sdr.electronics.dto.response.JwtResponse;
import edu.sdr.electronics.dto.response.ResponseMessage;
import edu.sdr.electronics.repository.StoreUserRepository;

import edu.sdr.electronics.service.AuthenticationService;
import edu.sdr.electronics.service.JWTService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {


    private final AuthenticationService authenticationService;
    private final AuthenticationManager authenticationManager;
    private final StoreUserRepository storeUserRepository;
    private final JWTService jwtService;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest, @RequestHeader("User-Agent") String userAgent) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        authenticationService.updateUserAgent(userDetails.getUsername(), userAgent);
        String jwtToken = jwtService.generateToken(userDetails);

        return ResponseEntity.ok(new JwtResponse(jwtToken, userDetails.getUsername(), userDetails.getAuthorities()));
    }


    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody StoreUser registerRequest, @RequestHeader("User-Agent") String userAgent) {
        if (storeUserRepository.existsByUsername(registerRequest.getUsername())) {
            return new ResponseEntity<>(new ResponseMessage("Numele utilizator exista!"),
                    HttpStatus.BAD_REQUEST);
        }

        if (storeUserRepository.existsByEmail(registerRequest.getEmail())) {
            return new ResponseEntity<>(new ResponseMessage("E-mailul este deja inregistrat!"),
                    HttpStatus.BAD_REQUEST);
        }

        this.authenticationService.registerUser(registerRequest, userAgent);

        return new ResponseEntity<>(new ResponseMessage("Utilizator inregistrat cu success!"), HttpStatus.OK);
    }


    @GetMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestParam String email) {

        if (!storeUserRepository.existsByEmail(email)) {
            return new ResponseEntity<>(new ResponseMessage("Nu exista un cont asociat acestui e-mail!"),
                    HttpStatus.BAD_REQUEST);
        }
        this.authenticationService.forgotPassword(email);

        return new ResponseEntity<>(new ResponseMessage("Parola temporara a fost trimisa pe e-mail!"), HttpStatus.OK);
    }

    @GetMapping("/counties")
    public ResponseEntity<List<County>> getCounties() {
        return new ResponseEntity<>(authenticationService.getCounties(), HttpStatus.OK);
    }

}
