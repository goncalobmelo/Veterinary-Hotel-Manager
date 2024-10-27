package hva.exceptions;

import java.io.Serial;

public class WrongVaccineKeyException extends Exception {

	@Serial
	private static final long serialVersionUID = 202410092248L;

	String _vaccineKey;

	public WrongVaccineKeyException(String key) {
	  _vaccineKey = key;
	}

	public String getKey() {
		return _vaccineKey;
	}
}