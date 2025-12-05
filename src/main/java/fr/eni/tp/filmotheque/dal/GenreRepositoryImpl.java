package fr.eni.tp.filmotheque.dal;

import fr.eni.tp.filmotheque.bo.Genre;
import fr.eni.tp.filmotheque.exception.GenreNotFound;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class GenreRepositoryImpl implements GenreRepository {

    //injection d'un jdbcTemplate avec constructeur
    private final JdbcTemplate jdbcTemplate;

    public GenreRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    //création des méthodes BDD

    //fonction avec une liste, donc on utilise query et RowMapper
    @Override
    public List<Genre> findAllGenres() {
        String sql = "SELECT id, libelle FROM genre";
        List<Genre> genres = jdbcTemplate.query(sql, new GenreRowMapper());
        return genres;
    }

    //fonction pour trouver 1 résulat, donc on utilise queryForObject
    @Override
    public Genre findGenreById(int id) {
        String sql = "SELECT id, libelle FROM genre WHERE id = ?";

        Genre genre = null;

        //ici le try catch permet de récupérer l'erreur si l'id n'existe pas
        try {
            genre = jdbcTemplate.queryForObject(sql, new GenreRowMapper(), id);
        } catch (EmptyResultDataAccessException ex) {
            throw new GenreNotFound();
        }

        return genre;
    }

    //fonction pour créer un genre
    @Override
    public Genre saveGenre(Genre genre) {
        String sql = "INSERT INTO genre (id, libelle) VALUES (?, ?)";

        PreparedStatementSetter pss = new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setLong(1, genre.getId());
                ps.setString(2, genre.getTitre());
            }
        };
        jdbcTemplate.update(sql, pss);

        //on retourne le paramètre genre
        return genre;
    }

    //fonction pour modifier un genre existant
    @Override
    public Genre updateGenre(Genre genre) {
        String sql = "UPDATE genre SET libelle = ? WHERE id = ?";

        PreparedStatementSetter pss = new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setLong(1, genre.getId());
                ps.setString(1, genre.getTitre());
            }
        };
        jdbcTemplate.update(sql, pss);

        return genre;
    }



    //on fait une classe interne RowMapper
    class GenreRowMapper implements RowMapper<Genre> {

        @Override
        public Genre mapRow(ResultSet rs, int rowNum) throws SQLException{
            Genre genre = new Genre();
            genre.setId(rs.getInt("id"));
            //on peut gérer directement le changement de int vers long
            //genre.setId(Long.parseLong(String.valueOf(rs.getInt("id"))));
            genre.setTitre(rs.getString("libelle"));
            return genre;
        }
    }




}
