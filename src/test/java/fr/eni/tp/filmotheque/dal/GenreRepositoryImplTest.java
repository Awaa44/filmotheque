package fr.eni.tp.filmotheque.dal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootTest
public class GenreRepositoryImplTest {

    @Autowired
    GenreRepository genreRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    public void testFindAll() {}
}
