package hva.exceptions;

import java.io.Serial;

public class WrongHabitatKeyException extends Exception {

	@Serial
	private static final long serialVersionUID = 202410092245L;

	String _habitatKey;

	public WrongHabitatKeyException(String key) {
	  _habitatKey = key;
	}

	public String getKey() {
		return _habitatKey;
	}

}