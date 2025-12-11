package fr.eni.tp.filmotheque.bll;

import fr.eni.tp.filmotheque.bo.Membre;
import fr.eni.tp.filmotheque.dto.MembreInscriptionDto;

public interface MembreService {

    //ajouter un membre
    Membre inscrireMembre(MembreInscriptionDto membreDto);

    //trouver un membre avec pseudo
    Membre trouverMembreParPseudo(String pseudo);

    //vérifier si un memebre existe
    boolean existsMembreByPseudo(String pseudo);
}
