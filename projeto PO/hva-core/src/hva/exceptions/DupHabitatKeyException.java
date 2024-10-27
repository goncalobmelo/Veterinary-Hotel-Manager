package hva.exceptions;

import java.io.Serial;

public class DupHabitatKeyException extends Exception {

	@Serial
	private static final long serialVersionUID = 202410092240L;

	String _habitatKey;

	public DupHabitatKeyException(String key) {
	  _habitatKey = key;
	}

	public String getKey() {
		return _habitatKey;
	}

}