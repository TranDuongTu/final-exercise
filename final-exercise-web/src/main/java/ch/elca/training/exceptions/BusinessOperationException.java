package ch.elca.training.exceptions;

/**
 * Root {@link Exception} for all occurred on Business layer.
 * 
 * @author DTR
 */
public class BusinessOperationException extends RuntimeException {
	private static final long serialVersionUID = 3158522382181203844L;
	
	private Exception causeException;

	public BusinessOperationException(String mess) {
		super(mess);
	}
	
	public BusinessOperationException(String mess, Exception causeException) {
		super(mess);
		this.causeException = causeException;
	}
	
	public Exception getCaException() {
		return this.causeException;
	}
}
