package fr.eni.tp.filmotheque.bll;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootTest
public class MembreServiceTest {

    @Autowired
    MembreService membreService;

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
    public void beforeEachTest() {
        System.out.println("BeforeEach");
        jdbcTemplate.update("DELETE FROM membres");
        jdbcTemplate.update("DBCC CHECKIDENT ('membres', RESEED, 0)");
        jdbcTemplate.update(
                "INSERT INTO membres (prenom, nom, pseudo, motDePasse, admin) VALUES (?, ?, ?, ?, ?)",
                "Jean", "Dupont", "jdupont", "azerty", false
        );
    }

    @AfterEach
    public void afterEachTest() {}




}
