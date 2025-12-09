package fr.eni.tp.filmotheque.dal;

import fr.eni.tp.filmotheque.bo.Genre;

import java.util.List;

public interface GenreRepository {

    //Afficher tous les genres dans une liste
    List<Genre> findAllGenres();

    //trouver un genre via id
    Genre findGenreById(long id);

    //ajouter un genre
    Genre saveGenre(Genre genre);

    //supprimer un genre
    Genre updateGenre(Genre genre);


}
