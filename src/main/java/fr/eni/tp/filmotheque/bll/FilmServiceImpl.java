package fr.eni.tp.filmotheque.bll;

import fr.eni.tp.filmotheque.bo.Film;
import fr.eni.tp.filmotheque.bo.Participant;
import fr.eni.tp.filmotheque.dal.FilmRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FilmServiceImpl  implements FilmService {

    //injecter la couche dal
    FilmRepository filmRepository;

    public FilmServiceImpl(FilmRepository filmRepository) {
        this.filmRepository = filmRepository;
    }

    @Override
    public List<Film> consulterFilms() {
        List<Film> films = filmRepository.findAllFilms();
        return films;
    }

    @Override
    public Film consulterFilmParId(int id) {
       Film film = filmRepository.findFilmById(id);
       return film;
    }

    @Override
    public List<Participant> consulterParticipants() {
        return List.of();
    }

    @Override
    public Participant consulterParticipantParId(int id) {
        return null;
    }

    @Override
    public void creerFilm(Film film) {
        filmRepository.saveFilm(film);
    }
}
