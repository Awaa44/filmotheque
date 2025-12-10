package fr.eni.tp.filmotheque.dal;

import fr.eni.tp.filmotheque.bo.Film;

import java.util.List;

public interface FilmRepository {

    //lister tous les films
    List<Film> findAllFilms();

    //trouver un film par id
    Film findFilmById(int id);

    //ajouter un film
    Film saveFilm(Film film);


}
