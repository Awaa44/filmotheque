package fr.eni.tp.filmotheque.security;

import fr.eni.tp.filmotheque.bo.Membre;
import fr.eni.tp.filmotheque.dal.MembreRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class FilmothequeUserDetailService implements UserDetailsService {

    private final MembreRepository membreRepository;

    public FilmothequeUserDetailService(MembreRepository membreRepository) {
        this.membreRepository = membreRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Membre membre = membreRepository.findMembreByPseudo(username);

        if (membre == null) {
            // pour créer un utilisateur en dur
            if ("bob".equals(username)) {
                return User.builder()
                        .username("bob")
                        .password("azerty")
                        .roles("ADMIN")
                        .build();
            }

            throw new UsernameNotFoundException("Utilisateur ou mot de passe n'existe pas");
        }

        // Utilisateur trouvé en base
        return User.builder()
                .username(membre.getPseudo())
                .password(membre.getMotDePasse()) // mot de passe hashé de la base
                .roles(membre.isAdmin() ? "ADMIN" : "USER")
                .build();
    }
}


