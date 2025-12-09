package fr.eni.tp.filmotheque.bll;

import fr.eni.tp.filmotheque.bo.Film;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class FilmServiceImplTest {

    @Autowired
    private FilmService filmService;

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
        System.out.println("Before Each Test");
    }

    @AfterEach
    public void afterEachTest()
    {
        System.out.println("After Each Test");
    }

    @Test
    @DisplayName("Afficher la liste de tous les films")
    public void testConsulterFilms()
    {
        List<Film> films = filmService.consulterFilms();

        //Assert
        assertNotNull(films);
        assertEquals(4, films.size());
    }
}
