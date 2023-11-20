package project.eventregistration.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import project.eventregistration.Dto.RegistrationDto;
import project.eventregistration.Exceptions.Errors.UsernameIsAlreadyExist;
import project.eventregistration.Models.Role;
import project.eventregistration.Models.User;
import project.eventregistration.Repository.RoleRepository;
import project.eventregistration.Repository.UserRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByEmail(username).orElseThrow(() -> new UsernameNotFoundException(
                "User with given name '%s' is not found " + username));
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole().getName()));

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                authorities
        );
    }

    public void createNewUser(RegistrationDto registrationDto) {
        User user = new User();
        try {
            Role defaultRole = roleRepository.findByName("ROLE_USER"); // er
            user.setName(registrationDto.getName());
            user.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
            user.setEmail(registrationDto.getEmail());
            user.setPhoto(registrationDto.getPhoto());
            user.setRole(defaultRole);
            userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            throw new UsernameIsAlreadyExist("Username is already exist");
        } catch (Exception e) {
            System.out.println(e);
            throw new UsernameIsAlreadyExist("Something went wrong!");
        }

    }
}
