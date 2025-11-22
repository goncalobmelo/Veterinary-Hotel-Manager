package hva.exceptions;

import java.io.Serial;

public class WrongVeterinarianKeyException extends Exception {

	@Serial
	private static final long serialVersionUID = 202410092249L;

	String _veterinarianKey;

	public WrongVeterinarianKeyException(String key) {
	  _veterinarianKey = key;
	}

	public String getKey() {
		return _veterinarianKey;
	}
}