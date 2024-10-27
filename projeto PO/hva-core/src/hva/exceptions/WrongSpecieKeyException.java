package hva.exceptions;

import java.io.Serial;

public class WrongSpecieKeyException extends Exception {

	@Serial
	private static final long serialVersionUID = 202410092246L;

	String _specieKey;

	public WrongSpecieKeyException(String key) {
	  _specieKey = key;
	}

	public String getKey() {
		return _specieKey;
	}
}