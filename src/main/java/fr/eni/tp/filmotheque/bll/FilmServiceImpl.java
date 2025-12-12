package fr.eni.tp.filmotheque.bll;

import fr.eni.tp.filmotheque.bo.Film;
import fr.eni.tp.filmotheque.dal.FilmRepository;
import fr.eni.tp.filmotheque.exception.FilmNotFound;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Primary
public class FilmServiceImpl  implements FilmService {

    //attribut pour le logger
    private static final Logger logger = LoggerFactory.getLogger(FilmServiceImpl.class);

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
    public Film consulterFilmParId(Integer id) {
        try {
            return filmRepository.findFilmById(id);
        } catch (FilmNotFound e) {
            logger.warn("Film non trouvé avec l'id : {}", id);
            throw e;
        }
    }

    @Override
    @Transactional
    public void creerFilm(Film film) {
        logger.info("Création du film : {}", film.getTitre());
        try {
            filmRepository.saveFilm(film);
            //remplace un println
            logger.info("Film créé avec succès - Id : {}", film.getId());
        } catch (Exception e) {
            logger.error("Erreur lors de la création du film : {}", film.getTitre());
            throw e;
        }
    }
}
