package hva.exceptions;

import java.io.Serial;

public class WrongAnimalKeyException extends Exception {

	@Serial
	private static final long serialVersionUID = 202410092243L;

	String _animalKey;

	public WrongAnimalKeyException(String key) {
	  _animalKey = key;
	}

	public String getKey() {
		return _animalKey;
	}

}