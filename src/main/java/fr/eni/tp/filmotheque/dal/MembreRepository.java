package fr.eni.tp.filmotheque.dal;

import fr.eni.tp.filmotheque.bo.Membre;

public interface MembreRepository {

    //ajouter un membre
    Membre saveMembre(Membre membre);

    Membre findMembreByPseudo(String pseudo);

    boolean existsMembreByPseudo(String pseudo);
}
