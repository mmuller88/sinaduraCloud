package net.zylk.sinadura.cloud.exceptions;

public class DaoCoreException extends Exception {

	public DaoCoreException(String message) {
		super(message);
	}

	public DaoCoreException(Exception e) {
		super(e);
	}

	public DaoCoreException(String message, Exception e) {
		super(message, e);
	}
}
