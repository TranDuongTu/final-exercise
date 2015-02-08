package ch.elca.training.exceptions;

/**
 * Root {@link Exception} for all occurred on Business layer.
 * 
 * @author DTR
 */
public class BusinessOperationException extends RuntimeException {
	private static final long serialVersionUID = 3158522382181203844L;

	public BusinessOperationException(String mess) {
		super(mess);
	}
}
