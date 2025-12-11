package fr.eni.tp.filmotheque.bll;

import fr.eni.tp.filmotheque.bo.Membre;
import fr.eni.tp.filmotheque.dal.MembreRepository;
import fr.eni.tp.filmotheque.dto.MembreInscriptionDto;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class MembreServiceImpl implements MembreService{

    private final PasswordEncoder passwordEncoder;
    MembreRepository membreRepository;

    public MembreServiceImpl(MembreRepository membreRepository, PasswordEncoder passwordEncoder) {
        this.membreRepository = membreRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Membre inscrireMembre(MembreInscriptionDto membreDto) {

        Membre membre = new Membre();
        //copie du Dto vers BO Membre
        membre.setNom(membreDto.getNom());
        membre.setPrenom(membreDto.getPrenom());
        membre.setPseudo(membreDto.getPseudo());

        //logique métier, ici on hash le mot de passe
        membre.setMotDePasse(passwordEncoder.encode(membreDto.getMotDePasse()));

        //on met l'utilisateur par défaut en User
        membre.setAdmin(false);

        return membreRepository.saveMembre(membre);
    }

    @Override
    public Membre trouverMembreParPseudo(String pseudo) {
        return membreRepository.findMembreByPseudo(pseudo);
    }

    @Override
    public boolean existsMembreByPseudo(String pseudo) {
        return membreRepository.existsMembreByPseudo(pseudo);
    }
}
