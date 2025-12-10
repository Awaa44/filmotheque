package fr.eni.tp.filmotheque.bo;

public class Participant extends Personne {
	/**
	 * Numéro de sérialisation
	 */
	private static final long serialVersionUID = 1L;

	public Participant() {
	}

	public Participant(String nom, String prenom) {
		super(nom, prenom);
	}

	public Participant(int id, String nom, String prenom) {
		super(id, nom, prenom);
	}
}
