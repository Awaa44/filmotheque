package fr.eni.tp.filmotheque.bll;

import fr.eni.tp.filmotheque.bo.Genre;
import fr.eni.tp.filmotheque.dal.GenreRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenreServiceImpl implements GenreService {

    //injection du DAL
    GenreRepository genreRepository;

    public GenreServiceImpl(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    @Override
    public List<Genre> findAllGenre() {
        List<Genre> genres = genreRepository.findAllGenres();
        return genres;
    }

    @Override
    public Genre findGenreById(long id) {
        Genre genre = genreRepository.findGenreById(id);
        return genre;
    }

    @Override
    public Genre saveGenre(Genre genre) {
        Genre newGenre = genreRepository.saveGenre(genre);
        return newGenre;
    }


/*    //réaliser le RowMapper avec une classe interne
    class GenreRowMapper implements RowMapper<Genre> {

        @Override
        public Genre mapRow(ResultSet rs, int rowNum) throws SQLException {
            Genre genre = new Genre();
            genre.setId(rs.getInt("id"));
            genre.setTitre(rs.getString("libelle"));
            return genre;
        }
    }*/

}
