package fr.eni.tp.filmotheque.bll;

import fr.eni.tp.filmotheque.bo.Genre;

import java.util.List;

public interface GenreService {

    List<Genre> findAllGenre();

    Genre findGenreById(int id);

    Genre saveGenre(Genre genre);


}
