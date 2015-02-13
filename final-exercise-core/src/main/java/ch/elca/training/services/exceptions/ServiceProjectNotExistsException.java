package ch.elca.training.services.exceptions;

/**
 * Occurred when attempting to get (or delete) non-existed project.
 * 
 * @author DTR
 */
public class ServiceProjectNotExistsException extends ServiceOperationException {
	private static final long serialVersionUID = 4260653014919145394L;

	public ServiceProjectNotExistsException(String mess) {
		super(mess);
	}
	
	public ServiceProjectNotExistsException(String mess, Exception causeException) {
		super(mess, causeException);
	}
}
