package fr.eni.tp.filmotheque.dal;

import fr.eni.tp.filmotheque.bo.Film;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class FilmRepositoryImplTest {

    @Autowired
    private FilmRepository filmRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeAll
    public static void beforeAll() {
        System.out.println("Before All");
    }

    @AfterAll
    public static void afterAll() {
        System.out.println("After All");
    }

    @BeforeEach
    public void beforeEachTest()
    {
        System.out.println("Before Each");
    }

    @AfterEach
    public  void afterEachTest()
    {
        System.out.println("After Each");
    }

    @Test
    @DisplayName("test récupération de tous les films en bdd")
    public void testFindAll() {
        //Arrange

        //Act
        List<Film> films = filmRepository.findAllFilms();

        //Assert
        assertNotNull(films);
        assertEquals(4, films.size());
    }

    @Test
    @DisplayName("test findFilmById")
    public void findFilmById() {
        //Arrange
        int id = 1;

        //act
        Film film = filmRepository.findFilmById(id);

        //Act
        assertNotNull(film);
        assertEquals(id, film.getId());
        assertEquals("Jurassic Park", film.getTitre());

    }


}
