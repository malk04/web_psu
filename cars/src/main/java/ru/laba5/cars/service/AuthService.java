package ru.laba5.cars.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.laba5.cars.configs.jwt.JwtUtils;
import ru.laba5.cars.exceptions.RegistrationException;
import ru.laba5.cars.models.ERole;
import ru.laba5.cars.models.Role;
import ru.laba5.cars.models.User;
import ru.laba5.cars.pojo.*;
import ru.laba5.cars.repository.RoleRepository;
import ru.laba5.cars.repository.UserRepository;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthService {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRespository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtUtils jwtUtils;

    public MessageResponse registerUser(SignupRequest signupRequest) throws RegistrationException {
        if (userRespository.existsByUsername(signupRequest.getUsername())) {
            throw new RegistrationException("Пользователь с таким именем уже зарегистрирован");
        }

        if (userRespository.existsByEmail(signupRequest.getEmail())) {
            throw new RegistrationException("Пользователь с такой почтой уже зарегистрирован");
        }

        User user = new User(signupRequest.getUsername(),
                signupRequest.getEmail(),
                passwordEncoder.encode(signupRequest.getPassword()));

        Set<Role> roles = new HashSet<>();

        Role userRole = roleRepository
                .findByName(ERole.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("Error, Role USER is not found"));
        roles.add(userRole);

        user.setRoles(roles);
        userRespository.save(user);
        return new MessageResponse("User CREATED");
    }

    public String loginUser(LoginRequest loginRequest) {

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        User user = userRespository.findByUsername(loginRequest.getUsername()).get();
        user.setVisits(user.getVisits() + 1);
        userRespository.save(user);

        return jwt;
    }

    public LkResponse getDataForLk() {
        Object principal = SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        UserDetailsImpl userDetails = (UserDetailsImpl) principal;
        return new LkResponse(userDetails.getUsername(), userDetails.getVisits(), userDetails.getAuthorities());
    }

    public DataTime getDataTime(){
        return new DataTime();
    }
}
