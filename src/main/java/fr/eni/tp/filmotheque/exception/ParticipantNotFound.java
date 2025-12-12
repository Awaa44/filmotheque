package fr.eni.tp.filmotheque.exception;

public class ParticipantNotFound extends RuntimeException {

    public ParticipantNotFound() {
        super("Participant non trouvé");
    }

    public ParticipantNotFound(String message) {
        super(message);
    }

}

