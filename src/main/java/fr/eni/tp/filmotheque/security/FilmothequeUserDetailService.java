package fr.eni.tp.filmotheque.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class FilmothequeUserDetailService implements UserDetailsService {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    /* Cette méthode est appelée par Spring à chaque fois qu'un utilise essaye de se connecter */
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        if(!"bob".equals(username)) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        User.UserBuilder builder = User.withUsername(username)
                .password(passwordEncoder.encode("azerty"))
                //.password("{bcrypt}$2a$10$AkOvi4sSEucJFfQJyWLkb.mJ3DgTy2qmvLVeE486SoJ7ET3B7laNy")
                .roles("ADMIN");

        return builder.build();
    }
}
