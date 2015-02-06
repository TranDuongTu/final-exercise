package ch.elca.training.services.exceptions;

/**
 * Occurred when trying to delete not existed {@link Project}
 * 
 * @author DTR
 */
public class ServiceProjectNotExistsException extends ServiceOperationException {
	private static final long serialVersionUID = 4260653014919145394L;

	public ServiceProjectNotExistsException(String mess) {
		super(mess);
	}
}
