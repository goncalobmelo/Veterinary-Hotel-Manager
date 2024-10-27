package hva.exceptions;

import java.io.Serial;

public class DupAnimalKeyException extends Exception {

	@Serial
	private static final long serialVersionUID = 202410092238L;

	String _animalKey;

	public DupAnimalKeyException(String key) {
	  _animalKey = key;
	}

	public String getKey() {
		return _animalKey;
	}

}