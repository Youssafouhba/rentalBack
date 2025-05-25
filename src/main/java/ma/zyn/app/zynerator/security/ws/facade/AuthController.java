package ma.zyn.app.zynerator.security.ws.facade;

import jakarta.validation.Valid;

import ma.zyn.app.zynerator.security.bean.User;
import ma.zyn.app.zynerator.security.common.SecurityParams;
import ma.zyn.app.zynerator.security.dao.facade.core.RoleDao;
import ma.zyn.app.zynerator.security.payload.request.ActivationRequest;
import ma.zyn.app.zynerator.security.service.facade.UserService;
import ma.zyn.app.zynerator.security.ws.dto.UserDto;
import ma.zyn.app.zynerator.security.ws.converter.UserConverter;

import ma.zyn.app.zynerator.security.jwt.JwtUtils;
import ma.zyn.app.zynerator.security.payload.request.LoginRequest;
import ma.zyn.app.zynerator.security.payload.response.JwtResponse;

import ma.zyn.app.zynerator.transverse.emailling.EmailRequest;
import ma.zyn.app.zynerator.transverse.emailling.EmailService;

import ma.zyn.app.zynerator.security.common.AuthoritiesConstants;

import ma.zyn.app.zynerator.security.service.facade.LoginAttemptService;


import ma.zyn.app.zynerator.security.bean.Role;
import ma.zyn.app.zynerator.security.bean.RoleUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.util.regex.Pattern;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.stream.Collectors;

//@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/")
public class AuthController {


    @PostMapping("register/admin")
    public ResponseEntity<Map<String, String>> registerAdmin(@RequestBody UserDto userDto) {
        return register(userDto, AuthoritiesConstants.ADMIN);
    }

    private ResponseEntity<Map<String, String>> register(UserDto userDto, String roleUsers) {
        if (userService.findByUsername(userDto.getUsername()) != null) {
            return ResponseEntity
                    .badRequest()
                    .body(Collections.singletonMap("error", "This username has already been taken"));
        }
        if (userService.findByEmail(userDto.getEmail()) != null) {
            return ResponseEntity
                    .badRequest()
                    .body(Collections.singletonMap("error", "This email is already on use "));
        }
        
        if (userDto.getEmail() == null || !Pattern.matches("^[A-Za-z0-9+_.-]+@(.+)$", userDto.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(Collections.singletonMap("error", "Invalid email format ,valide format is:  xxxx@xxx.xxx"));
        }
        
        if (userDto.getPassword() == null || userDto.getPassword().length() < 8) {
            return ResponseEntity
                    .badRequest()
                    .body(Collections.singletonMap("error", "Password must be at least 8 characters long."));
        }

        LocalDateTime expirationDate = LocalDateTime.now().plus(24, ChronoUnit.HOURS);
        userDto.setEnabled(false);
        userDto.setExpirationLinkDate(expirationDate);
        userDto.setLinkValidationCode(userService.generateCode(8));
        EmailRequest emailRequest = new EmailRequest();
        emailRequest.setFrom("votre email");
        emailRequest.setBcc(userDto.getEmail());
        emailRequest.setCc(userDto.getEmail());
        emailRequest.setTo(userDto.getEmail());
        emailRequest.setSubject("Verify your email");
        emailRequest.setBody("Welcome to Zynerator !! Your activation code is : " + userDto.getLinkValidationCode());
        userConverter.setRoleUsers(true);

        User user = userConverter.toItem(userDto);
        
        RoleUser roleUser = new RoleUser();
        Role role = new Role();
        role.setAuthority(roleUsers);
        roleUser.setRole(role);
        roleUser.setUserApp(user);
        user.setRoleUsers(Collections.singletonList(roleUser));

        
        userService.createAndDisable(user);
        emailService.sendSimpleMessage(emailRequest);

        Map<String, String> response = new HashMap<>();
        response.put("message", "You have registered successfully");
        return ResponseEntity.ok(response);
    }


    @PostMapping("activateAccount")
    public ResponseEntity<?> verifyUser(@RequestBody ActivationRequest activationRequest) {
        User user = userService.findByLinkValidationCode(activationRequest.getActivationCode());
        String username = activationRequest.getUsername();
        User userVerifier = userService.findByUsername(username);
        System.out.println(username);
        if (user == null) {
            return new ResponseEntity<>("Bad Crentials", HttpStatus.BAD_REQUEST);
        } else if (userVerifier == null) {
            System.out.println(username);
            System.out.println("userVerifier is null");
            return new ResponseEntity<>("Bad Crentials", HttpStatus.BAD_REQUEST);
        } else if (!user.getUsername().equals(userVerifier.getUsername())) {
            return new ResponseEntity<>("Bad Crentials", HttpStatus.BAD_REQUEST);
        } else if (!isActivationLinkValid(user)) {
            return new ResponseEntity<>("Activation code is not valid", HttpStatus.BAD_REQUEST);
        } else {
            user.setEnabled(true);
            userService.update(user);
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    
    @PostMapping("login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        User user = userService.findByUsername(loginRequest.getUsername());
        if (user != null && loginAttemptService.isLocked(user.getUsername())) {
            Long remainingTime = loginAttemptService.getRemainingLockoutTime(user.getUsername());
            String lockoutMessage = String.format("Account is locked due to multiple failed login attempts. Please try again in %d seconds.", remainingTime);
            return ResponseEntity.status(HttpStatus.LOCKED).body(Collections.singletonMap("message", lockoutMessage));
        }
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            loginAttemptService.loginSucceeded(user.getUsername());
            String jwt = jwtUtils.generateJwtToken(authentication);
            List<String> roles = user.getRoleUsers().stream().map(roleUser -> roleUser.getRole().getAuthority()).collect(Collectors.toList());

            HttpHeaders headers = new HttpHeaders();
            headers.add(SecurityParams.JWT_HEADER_NAME, SecurityParams.HEADER_PREFIX + jwt);
            return ResponseEntity.ok().headers(headers)
                    .body(new JwtResponse(jwt, user.getFirstName(), user.getLastName(), user.getUsername(), user.getEmail(), roles));
        } catch (AuthenticationException e) {
            loginAttemptService.loginFailed(loginRequest.getUsername());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login failed: " + e.getMessage());
        }
    }




    private boolean isActivationLinkValid(User user) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expiry = user.getExpirationLinkDate();
        return expiry != null && now.isBefore(expiry);
    }


    /*
    @PutMapping("forgetPassword")
    public ResponseEntity<Map<String, String>> forgetPassword(@RequestBody ForgetPasswordRequest forgetPasswordRequest) {
        User user = userService.findByEmail(forgetPasswordRequest.getEmail());

        if (user != null) {
            if (user.getLinkValidationCode().equals(forgetPasswordRequest.getLinkValidationCode())) {

                boolean passwordChanged = userService.changePassword(user.getUsername(), forgetPasswordRequest.getNewPassword());

                if (passwordChanged) {
                    EmailRequest emailRequest = new EmailRequest();
                    emailRequest.setFrom(user.getEmail());
                    emailRequest.setBcc(user.getEmail());
                    emailRequest.setCc(user.getEmail());
                    emailRequest.setTo(user.getEmail());
                    emailRequest.setSubject("Verify your email");
                    emailRequest.setBody("your password has been changed");
                    emailService.sendSimpleMessage(emailRequest);
                    Map<String, String> response = new HashMap<>();
                    response.put("message", "check your email");
                    return ResponseEntity.ok(response);
                }
            } else {
                ResponseEntity.badRequest().body(Collections.singletonMap("error", "invalid verification code"));
            }
        }
        return ResponseEntity.badRequest().body(Collections.singletonMap("error", "Your email  is uncorrect"));
    }

     */

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserService userService;

    @Autowired
    UserConverter userConverter;

    @Autowired
    EmailService emailService;

    @Autowired
    RoleDao roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    LoginAttemptService loginAttemptService;


    }
