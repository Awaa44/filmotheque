package fr.eni.tp.filmotheque.bll;

import fr.eni.tp.filmotheque.bo.Genre;
import fr.eni.tp.filmotheque.exception.GenreNotFound;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class GenreServiceImplTest {

    @Autowired
    GenreService genreService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeAll
    public static void setUp() {
        System.out.println("Before All");
    }

    @AfterAll
    public static void tearDown() {
        System.out.println("After All");
    }

    @BeforeEach
    public void BeforeEachTest() {
        System.out.println("BeforeEach");
/*        jdbcTemplate.update("delete from genres");
        jdbcTemplate.update("INSERT INTO genres (id, libelle) VALUES (1, 'Animation')");
        jdbcTemplate.update("INSERT INTO genres (id, libelle) VALUES (2, 'Science-fiction')");
        jdbcTemplate.update("INSERT INTO genres (id, libelle) VALUES (3, 'Documentaire')");
        jdbcTemplate.update("INSERT INTO genres (id, libelle) VALUES (4, 'Action')");
        jdbcTemplate.update("INSERT INTO genres (id, libelle) VALUES (5, 'Comédie')");
        jdbcTemplate.update("INSERT INTO genres (id, libelle) VALUES (6, 'Drame')");*/
    }

    @Test
    @DisplayName("test findAllGenres cas où il y a plusieurs genres")
    public void testFindAllGenres() {
        //AAA
        //Arrange

        //Act
        List<Genre> genres = genreService.findAllGenre();

        //Assert
        assertNotNull(genres);
        assertEquals(6, genres.size());

    }

    @Test
    @DisplayName("test pour trouver le genre par id")
    public void testFindGenreById() {
        //Arrange
        int id = 1;

        //Act
        Genre genre = genreService.findGenreById(id);

        //act
        assertNotNull(genre);
        assertEquals(id, genre.getId());
        assertEquals("Animation", genre.getTitre());

    }

    @Test
    @DisplayName("test pour le cas où l'id du genre n'existe pas")
    public void testFindGenreByIdNotFound() {
        //Arrange
        int id = 8;

        //Act
       assertThrows(GenreNotFound.class, () -> genreService.findGenreById(id));
    }

    @Test
    @DisplayName("test pour ajouter un nouveau genre")
    public void testAddGenre() {
        //Arrante
        Genre genre = new Genre(7, "horreur");

        //Act
        Genre newGenre = genreService.saveGenre(genre);

        //Arrange
        assertNotNull(newGenre);
        assertEquals(genre.getId(), newGenre.getId());
        assertEquals(genre, newGenre);

    }

}
