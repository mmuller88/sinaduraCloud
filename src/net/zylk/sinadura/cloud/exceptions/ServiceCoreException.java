package net.zylk.sinadura.cloud.exceptions;

public class ServiceCoreException extends Exception {

	public ServiceCoreException(String message)
	{
		super(message);
	}
	public ServiceCoreException(String message, Exception e)
	{
		super(message, e);
	}
}
