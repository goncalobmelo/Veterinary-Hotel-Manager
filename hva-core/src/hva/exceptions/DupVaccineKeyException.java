package hva.exceptions;

import java.io.Serial;

public class DupVaccineKeyException extends Exception {

	@Serial
	private static final long serialVersionUID = 202410092242L;

	String _vaccineKey;

	public DupVaccineKeyException(String key) {
	  _vaccineKey = key;
	}

	public String getKey() {
		return _vaccineKey;
	}

}