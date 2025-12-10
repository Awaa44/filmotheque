package fr.eni.tp.filmotheque.dal;

import fr.eni.tp.filmotheque.bo.Participant;
import fr.eni.tp.filmotheque.exception.ParticipantNotFound;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class ParticipantRepositoryImpl implements ParticipantRepository {

	private JdbcTemplate jdbcTemplate;
	private NamedParameterJdbcTemplate namedJdbcTemplate;

	public ParticipantRepositoryImpl(NamedParameterJdbcTemplate namedJdbcTemplate) {
		this.namedJdbcTemplate = namedJdbcTemplate;
		this.jdbcTemplate = namedJdbcTemplate.getJdbcTemplate();
	}

	@Override
	public List<Participant> findAllParticipants() {
		String sql = "select id, nom, prenom from participants";

		List<Participant> participants = jdbcTemplate.query(sql,
				new BeanPropertyRowMapper<Participant>(Participant.class));
		return participants;

	}

	@Override
	public Participant findParticipantById(Integer id) {
		String sql = "select id, nom, prenom from participants where id=?";

		Participant participant=null;

		try {
			 participant = jdbcTemplate.queryForObject(sql,
					new BeanPropertyRowMapper<Participant>(Participant.class), id);

		} catch (EmptyResultDataAccessException exc) {
			throw new ParticipantNotFound();
		}

		return participant;
	}
}
