package fr.eni.tp.filmotheque.dal;

import fr.eni.tp.filmotheque.bo.Film;
import fr.eni.tp.filmotheque.bo.Genre;
import fr.eni.tp.filmotheque.bo.Participant;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class FilmRepositoryImpl implements FilmRepository {

    //injection d'un jdbcTemplate avec constructeur
    private final JdbcTemplate jdbcTemplate;

    public FilmRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public List<Film> findAllFilms() {
        String sqlFilm = "SELECT " +
                "f.id, f.titre, f.annee, f.duree, f.synopsis, " +
                "g.id AS genre_id, g.libelle AS genre_libelle, " +
                "p.id AS real_id, p.prenom AS real_prenom, p.nom AS real_nom " +
                "FROM films f " +
                "INNER JOIN genres g ON f.genreId = g.id " +
                "INNER JOIN participants p ON f.realisateurId = p.id";


        List<Film> films = jdbcTemplate.query(sqlFilm, new FilmCompletRowMapper());

        //récupérer les acteurs pour chaque film avec un for via la méthode privée findActeursByFilmId
        for(Film film : films) {
            List<Participant> acteurs = findActeursByFilmId(film.getId());
            film.setActeurs(acteurs);
        }

        return films;
    }

    //méthode privée pour trouver la liste des acteurs
    private List<Participant> findActeursByFilmId(long filmId) {
        String sql = "SELECT p.id, p.prenom, p.nom " +
                "FROM participants p " +
                "INNER JOIN acteurs a ON p.id = a.participantId " +
                "WHERE a.filmId = ?";

        return jdbcTemplate.query(sql, new ParticipantRowMapper(), filmId);
    }

    //construction du RowMapper pour afficher les films
    class FilmCompletRowMapper implements RowMapper<Film> {

        @Override
        public Film mapRow(ResultSet rs, int rowNum) throws SQLException {
            //FILMS
            Film film = new Film();
            film.setId(rs.getInt("id"));
            film.setTitre(rs.getString("titre"));
            film.setAnnee(rs.getInt("annee"));
            film.setDuree(rs.getInt("duree"));
            film.setSynopsis(rs.getString("synopsis"));

            //GENRES
            Genre genre = new Genre();
            genre.setId(rs.getInt("genre_id"));
            genre.setTitre(rs.getString("genre_libelle"));
            film.setGenre(genre);

            //REALISATEURS
            Participant realisateur = new Participant();
            realisateur.setId(rs.getInt("real_id"));
            realisateur.setNom(rs.getString("real_nom"));
            realisateur.setPrenom(rs.getString("real_prenom"));
            film.setRealisateur(realisateur);

            return film;
        }
    }

    class ParticipantRowMapper implements RowMapper<Participant> {
        @Override
        public Participant mapRow(ResultSet rs, int rowNum) throws SQLException {
            Participant participant = new Participant();
            participant.setId(rs.getInt("id"));
            participant.setNom(rs.getString("nom"));
            participant.setPrenom(rs.getString("prenom"));
            return participant;

        }
    }

}
