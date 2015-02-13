package ch.elca.training.services.exceptions;

/**
 * Occurred when attempting to insert duplicated project.
 * 
 * @author DTR
 */
public class ServiceDuplicateProjectException extends ServiceOperationException {
	
	private static final long serialVersionUID = -5240961079592486991L;

	public ServiceDuplicateProjectException(String mess) {
		super(mess);
	}
	
	public ServiceDuplicateProjectException(String mess, Exception causedException) {
		super(mess, causedException);
	}
}
