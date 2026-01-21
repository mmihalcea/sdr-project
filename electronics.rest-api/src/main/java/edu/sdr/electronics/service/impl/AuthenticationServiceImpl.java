package edu.sdr.electronics.service.impl;

import edu.sdr.electronics.domain.County;
import edu.sdr.electronics.domain.Role;
import edu.sdr.electronics.domain.StoreUser;
import edu.sdr.electronics.repository.CountyRepository;
import edu.sdr.electronics.repository.RoleRepository;
import edu.sdr.electronics.repository.StoreUserRepository;
import edu.sdr.electronics.service.AuthenticationService;
import edu.sdr.electronics.utils.RoleName;
import edu.sdr.electronics.utils.UserAgentParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private StoreUserRepository userRepository;
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private CountyRepository countyRepository;

    @Override
    public StoreUser registerUser(StoreUser registerRequest, String userAgent) {
        StoreUser user = new StoreUser(registerRequest.getName(), registerRequest.getUsername(), registerRequest.getEmail(),
                registerRequest.getPassword(), registerRequest.getAddress());

        user.setBrowser(UserAgentParser.getBrowser(userAgent));
        user.setOperatingSystem(UserAgentParser.getOperatingSystem(userAgent));

        Set<Role> roles = new HashSet<>();
        roles.add(roleRepository.findByName(RoleName.ROLE_USER).orElse(null));

        user.setRoles(roles);
        return userRepository.save(user);
    }

    @Override
    public void updateUserAgent(String username, String userAgent) {
        userRepository.findByUsername(username).ifPresent(user -> {
            user.setBrowser(UserAgentParser.getBrowser(userAgent));
            user.setOperatingSystem(UserAgentParser.getOperatingSystem(userAgent));
            userRepository.save(user);
        });
    }

    @Override
    public void forgotPassword(String email) {
        String generatedPassword = this.getAlphaNumericString(10);
        StoreUser user = this.userRepository.findByEmail(email).orElse(null);
        if (user != null) {
            user.setPassword(generatedPassword);
            this.userRepository.save(user);
            this.sendForgotPasswordEmail(generatedPassword, email);
        }

    }

    @Override
    public List<County> getCounties() {
        return countyRepository.findAll();
    }

    private String getAlphaNumericString(int n) {
        String alphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";

        StringBuilder sb = new StringBuilder(n);

        for (int i = 0; i < n; i++) {
            sb.append(alphaNumericString.charAt(new Random().nextInt(alphaNumericString.length())));
        }

        return sb.toString();
    }

    private void sendForgotPasswordEmail(String password, String email) {
        String subject = "Resetare parola aplicatia Inchirieri intrumente muzicale";

        String message = "Ati resetat parola. Noua parola:";

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(email);
        mailMessage.setSubject(subject);
        mailMessage.setText(message + "\r\n" + password + "\r\n");
        mailSender.send(mailMessage);
    }

}
