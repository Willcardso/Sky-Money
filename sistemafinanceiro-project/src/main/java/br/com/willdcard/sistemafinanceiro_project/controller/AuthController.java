package br.com.willdcard.sistemafinanceiro_project.controller;

import br.com.willdcard.sistemafinanceiro_project.dto.RegisterRequest;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import br.com.willdcard.sistemafinanceiro_project.dto.LoginRequest;
import br.com.willdcard.sistemafinanceiro_project.dto.TokenResponse;
import br.com.willdcard.sistemafinanceiro_project.model.User;
import br.com.willdcard.sistemafinanceiro_project.repository.UserRepository;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UserRepository userRepository,
                          PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // Token temporário (JWT vem depois)
        String token = "token-temporario";

        return ResponseEntity.ok(new TokenResponse(token));
    }
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {


    if (userRepository.findByEmail(request.getEmail()).isPresent()) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
    .body("E-mail já cadastrado");
    }


    String senhaCriptografada = passwordEncoder.encode(request.getPassword());


    User user = new User(request.getEmail(), senhaCriptografada);
    userRepository.save(user);


    return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
