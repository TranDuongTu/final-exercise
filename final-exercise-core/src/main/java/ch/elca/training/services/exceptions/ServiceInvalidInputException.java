package ch.elca.training.services.exceptions;

/**
 * When input criteria for searching projects is invalid.
 *  
 * @author DTR
 */
public class ServiceInvalidInputException extends ServiceOperationException {
	
	private static final long serialVersionUID = -2332212208841371574L;

	public ServiceInvalidInputException(String mess) {
		super(mess);
	}
}
