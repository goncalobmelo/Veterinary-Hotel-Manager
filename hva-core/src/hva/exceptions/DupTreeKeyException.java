package hva.exceptions;

import java.io.Serial;

public class DupTreeKeyException extends Exception {

	@Serial
	private static final long serialVersionUID = 202410092241L;


	String _treeKey;

	public DupTreeKeyException(String key) {
	  _treeKey = key;
	}

	public String getKey() {
		return _treeKey;
	}
}