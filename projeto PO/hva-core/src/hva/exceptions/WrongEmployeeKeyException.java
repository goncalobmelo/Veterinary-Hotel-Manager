package hva.exceptions;

import java.io.Serial;

public class WrongEmployeeKeyException extends Exception {

	@Serial
	private static final long serialVersionUID = 202410092244L;

	String _employeeKey;

	public WrongEmployeeKeyException(String key) {
	  _employeeKey = key;
	}

	public String getKey() {
		return _employeeKey;
	}
	
}