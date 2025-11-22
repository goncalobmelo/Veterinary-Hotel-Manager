package hva.exceptions;

import java.io.Serial;

/**
 * 
 */
public class ResponsabilityUnassignedException extends Exception {

	@Serial
	private static final long serialVersionUID = 202410231702L;

	/** The requested filename. */
	String _responsabilityKey;

	public ResponsabilityUnassignedException(String key) {
	  _responsabilityKey = key;
	}

	public String getKey() {
		return _responsabilityKey;
	}

}