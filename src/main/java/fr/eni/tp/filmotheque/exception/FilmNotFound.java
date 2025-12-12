package fr.eni.tp.filmotheque.exception;

public class FilmNotFound extends RuntimeException {
    public FilmNotFound() {
        super("Film introuvable");
    }

    // Constructeur avec message personnalisé
    public FilmNotFound(String message) {
        super(message);
    }

    // Constructeur avec message et cause
    public FilmNotFound(String message, Throwable cause) {
        super(message, cause);
    }
}
