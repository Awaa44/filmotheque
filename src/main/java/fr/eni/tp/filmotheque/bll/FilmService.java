package fr.eni.tp.filmotheque.bll;

import fr.eni.tp.filmotheque.bo.Film;

import java.util.List;

public interface FilmService {
    List<Film> consulterFilms();

    Film consulterFilmParId(Integer id);

    void creerFilm(Film film);
}
