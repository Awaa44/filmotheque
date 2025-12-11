package fr.eni.tp.filmotheque.dal;

import fr.eni.tp.filmotheque.bo.Membre;
import fr.eni.tp.filmotheque.exception.MembreNotFound;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class MembreRepositoryImpl implements MembreRepository {

    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public MembreRepositoryImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = namedParameterJdbcTemplate.getJdbcTemplate();
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public Membre saveMembre(Membre membre) {

        String sql = "INSERT INTO membres (prenom, nom, pseudo, motDePasse, admin)" +
                    "VALUES (:prenom, :nom, :pseudo, :motDePasse, :admin) ";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("prenom", membre.getPrenom());
        parameters.addValue("nom", membre.getNom());
        parameters.addValue("pseudo", membre.getPseudo());
        parameters.addValue("motDePasse", membre.getMotDePasse());
        parameters.addValue("admin", membre.isAdmin());

        namedParameterJdbcTemplate.update(sql, parameters, keyHolder ,new String[] { "id"} );

        membre.setId(keyHolder.getKey().intValue());
        return membre;
    }

    @Override
    public Membre findMembreByPseudo(String pseudo) {
        String sql = "SELECT id, prenom, nom, pseudo, motDePasse, admin FROM membres WHERE pseudo = ?";

        Membre membre = null;

        try {
            membre = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Membre.class), pseudo);
        } catch (EmptyResultDataAccessException e) {
            throw new MembreNotFound("Membre ou mot de passe non trouvé");
        }
        return membre;
    }

    @Override
    public boolean existsMembreByPseudo(String pseudo) {
        //Compte le nombre de lignes qui correspondent au pseudo, retourne 1 si pseudo existe,
        // et 0 s'il n'existe pas
        String sql = "SELECT COUNT(*) FROM membres WHERE pseudo = ?";
        //Utilisation de Integer.class plutôt qu'un rowMapper car 1 seul attribut à récupérer.
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, pseudo);

        //retourne true ou false
        return count!=null && count>0;
    }

    //UTILISATION DE BeanPropertyRowMapper à la place du ROWMAPPER manuel
/*    class membreRowMapper implements RowMapper<Membre> {
        @Override
        public Membre mapRow(ResultSet rs, int rowNum) throws SQLException {
            Membre membre = new Membre();
            membre.setId(rs.getInt("id"));
            membre.setPseudo(rs.getString("pseudo"));
            membre.setNom(rs.getString("nom"));
            membre.setMotDePasse(rs.getString("motDePasse"));
            membre.setAdmin(rs.getBoolean("admin"));

            return membre;
        }
    }*/
}
