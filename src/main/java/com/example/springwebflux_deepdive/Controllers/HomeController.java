package com.example.springwebflux_deepdive.Controllers;




import com.example.springwebflux_deepdive.Models.User;
import com.example.springwebflux_deepdive.Repositories.UserRepository;
import com.example.springwebflux_deepdive.Services.AuthService;
import com.example.springwebflux_deepdive.Services.JwtService;
import com.example.springwebflux_deepdive.dto.AuthRequest;
import com.example.springwebflux_deepdive.dto.AuthResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class HomeController {

private final AuthService authService;
private final UserRepository userRepository;
private final JwtService jwtService;
private final PasswordEncoder passwordEncoder;
    @GetMapping("/welcome")
    public Mono<ResponseEntity<Map>> welcome(){
        Map<String,String> usersInfo = new HashMap<>();
        usersInfo.put("salah","123");

        return  Mono.just(ResponseEntity.ok().body(usersInfo));
    }


    @GetMapping("/home")
    public Mono<String> welcomeMessage(@RequestParam String username){

        return Mono.just("hey"+username+"!! welcome to our website");
    }

    @GetMapping("/register")
    public Mono<String> register(){
    User user =
        new User(null,"ahmed","065060","ahmed@gmail.com","111","ADMIN");

        return authService.register(user);
    }


    @PostMapping("/auth")
    public Mono<ResponseEntity<AuthResponse>> login(@RequestBody AuthRequest request){

            return authService.auth(request);
    }


    @GetMapping("/admin")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Mono<String> welcomeAdmin(){

        return Mono.just("hey admin !! welcome to our website");
    }
}
