package com.project.filestorage.service;

import com.project.filestorage.dto.AuthRequestDto;
import com.project.filestorage.model.Role;
import com.project.filestorage.model.User;
import com.project.filestorage.repository.RoleRepository;
import com.project.filestorage.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class UserService {

    private final JwtService jwtService;

    private final UserRepository userRepository;

    private final AuthenticationManager authenticationManager;

    private final BCryptPasswordEncoder encoder;

    private final RoleRepository roleRepository;


    public UserService(JwtService jwtService, UserRepository userRepository, AuthenticationManager authenticationManager, BCryptPasswordEncoder encoder, RoleRepository roleRepository) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.encoder = encoder;
        this.roleRepository = roleRepository;
    }

    public User register(AuthRequestDto authRequestDto){
        Role userRole = roleRepository.findByRole("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("Default role not found"));
        User user = new User();
        user.setUsername(authRequestDto.getUsername());
        user.setPassword(encoder.encode(authRequestDto.getPassword()));
        user.setRoles(Set.of(userRole));
        userRepository.save(user);
        return user;
    }

    public String verify(AuthRequestDto authRequestDto){
        Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequestDto.getUsername(), authRequestDto.getPassword()));
        if (auth.isAuthenticated()){
            System.out.println("Logged in successfully");
            return jwtService.generateToken(authRequestDto.getUsername());
        }
        return "Failure";
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }
}
