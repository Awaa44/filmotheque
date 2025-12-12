package fr.eni.tp.filmotheque.exception;

public class MembreNotFound extends RuntimeException {
    public MembreNotFound(String message) {
        super(message);
    }

    public MembreNotFound(){
        super("Membre introuvable");
    }
}
