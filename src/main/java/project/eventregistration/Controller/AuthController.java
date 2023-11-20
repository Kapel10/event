package project.eventregistration.Controller;

import jakarta.annotation.security.PermitAll;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import project.eventregistration.Dto.AuthRequest;
import project.eventregistration.Dto.RegistrationDto;
import project.eventregistration.Service.AuthService;
import project.eventregistration.Utils.JwtTokenUtils;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    private final JwtTokenUtils jwtTokenUtils;

    @PostMapping("/authentication")
    public ResponseEntity<?> authentication(@RequestBody AuthRequest authRequest) {
        return authService.authenticationService(authRequest);
    }

    @GetMapping("/Hello")
    @PermitAll
    public String helloMessage() {
        return "Hello user";
    }

    @PostMapping("/registration")
    @PermitAll
    public ResponseEntity<?> registration(@RequestBody RegistrationDto registrationDto) {
        return authService.registrationService(registrationDto);
    }


    @GetMapping("/token-email")
    public String getUsernameFromToken(String token){
        return jwtTokenUtils.getUsername(token);

    }

    @GetMapping("/token-role")
    public String getRoleFromToken(String token){
        return jwtTokenUtils.getRole(token);
    }
}
