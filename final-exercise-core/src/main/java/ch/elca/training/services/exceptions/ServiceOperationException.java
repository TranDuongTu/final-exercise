package ch.elca.training.services.exceptions;

/**
 * Based Exception must be checked in Service layer.
 * 
 * @author DTR
 */
public class ServiceOperationException extends Exception {
	
	private static final long serialVersionUID = 4855496160212543758L;
	
	private Exception causeException;
	
	public ServiceOperationException(String mess) {
		super(mess);
	}
	
	public ServiceOperationException(String mess, Exception causeException) {
		super(mess);
		this.causeException = causeException;
	}
	
	public Exception getCauseException() {
		return causeException;
	}
}
