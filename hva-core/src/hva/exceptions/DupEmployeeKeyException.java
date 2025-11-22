package hva.exceptions;

import java.io.Serial;

public class DupEmployeeKeyException extends Exception {

	@Serial
	private static final long serialVersionUID = 202410092239L;
	
	String _employeeKey;

	public DupEmployeeKeyException(String key) {
	  _employeeKey = key;
	}

	public String getKey() {
		return _employeeKey;
	}
	
}