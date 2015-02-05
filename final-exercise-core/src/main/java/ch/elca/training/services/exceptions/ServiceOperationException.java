package ch.elca.training.services.exceptions;

/**
 * Based Exception for service layer.
 * 
 * @author DTR
 */
public class ServiceOperationException extends Exception {
	
	private static final long serialVersionUID = 4855496160212543758L;
	
	public ServiceOperationException(String mess) {
		super(mess);
	}
}
