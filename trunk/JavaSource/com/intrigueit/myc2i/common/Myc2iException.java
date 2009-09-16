package com.intrigueit.myc2i.common;

public class Myc2iException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = -357617792858146580L;
	protected Throwable throwable;
	/**
	 * Method 'Myc2iException'
	 * 
	 * @param message
	 */
	public Myc2iException(String message)
	{
		super(message);
	}

	/**
	 * Method 'Myc2iException'
	 * 
	 * @param message
	 * @param throwable
	 */
	public Myc2iException(String message, Throwable throwable)
	{
		super(message);
		this.throwable = throwable;
	}

	/**
	 * Method 'getCause'
	 * 
	 * @return Throwable
	 */
	public Throwable getCause()
	{
		return throwable;
	}
}
