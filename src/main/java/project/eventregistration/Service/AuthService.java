package project.eventregistration.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import project.eventregistration.Dto.AuthRequest;
import project.eventregistration.Dto.JwtResponse;
import project.eventregistration.Dto.RegistrationDto;
import project.eventregistration.Utils.JwtTokenUtils;


@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtTokenUtils jwtTokenUtils;
    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    public ResponseEntity<?> authenticationService(AuthRequest authRequest) {
        try {
            UserDetails user = userService.loadUserByUsername(authRequest.getEmail());
            this.authenticate(authRequest.getEmail(), authRequest.getPassword());

            String token = jwtTokenUtils.generateToken(user);
            return ResponseEntity.ok(new JwtResponse(token));
        } catch (UsernameNotFoundException e) {
            System.out.println(e);
            throw new UsernameNotFoundException(e.getMessage());
        }
    }

    public ResponseEntity<?> registrationService(RegistrationDto registrationDto) {
        userService.createNewUser(registrationDto);
        return ResponseEntity.ok("User was successfully created!");
    }

    private void authenticate(String email, String password) {
        this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
    }

}
