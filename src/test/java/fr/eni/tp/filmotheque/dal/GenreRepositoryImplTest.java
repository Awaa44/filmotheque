package fr.eni.tp.filmotheque.dal;

import fr.eni.tp.filmotheque.bo.Genre;
import fr.eni.tp.filmotheque.exception.GenreNotFound;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class GenreRepositoryImplTest {

    @Autowired
    GenreRepository genreRepository;

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
    public void beforeEach() {
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
    @DisplayName("test findAllGenres cas où il y plusieurs genres")
    public void testFindAllGenresCasPlusieursGenres() {
        //AAA
        //Arrange

        //Act : appel de la méthode findAll
        List<Genre> genres = genreRepository.findAllGenres();

        //Assert
        assertNotNull(genres);
        assertEquals(6, genres.size());
    }

    @Test
    @DisplayName("test pour trouver le genre par id")
    public void testPourTrouverLeGenreParId() {
        //AAA
        //Arrange
        int id = 1;

        //Assert : fonction pour trouver le genre
        Genre genre = genreRepository.findGenreById(id);

        //Act
        assertNotNull(genre);
        assertEquals(id, genre.getId());
        assertEquals("Animation", genre.getTitre());

    }

    @Test
    @DisplayName("test pour le cas où le genre n'existe pas")
    public void testPourLeGenreNExistePas() {
        //AAA
        //Arrange
        int id = 999;

        //Act
        assertThrows(GenreNotFound.class, () -> genreRepository.findGenreById(id));
    }

    @Test
    @DisplayName("test pour ajouter un genre")
    public void testPourAjouterUnGenre() {
        //Arrange
        Genre genre = new Genre(7,"Horreur");

        //Act
        Genre newGenre = genreRepository.saveGenre(genre);

        //Assert
        assertNotNull(newGenre);
        Genre genre2 = genreRepository.findGenreById(newGenre.getId());
        assertEquals(genre2, newGenre);

    }

    @Test
    @DisplayName("test pour modifier un genre")
    public void testPourModifierUnGenre() {

        Genre genre = new Genre(6,"Horreur");
        genre = genreRepository.updateGenre(genre);

        assertEquals("Horreur", genre.getTitre());
    }



}
