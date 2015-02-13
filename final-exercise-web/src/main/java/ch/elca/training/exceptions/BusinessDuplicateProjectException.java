package ch.elca.training.exceptions;

/**
 * Occurred when trying to insert Project with duplicated number.
 * 
 * @author DTR
 */
public class BusinessDuplicateProjectException extends BusinessOperationException {

	private static final long serialVersionUID = -2876712165806772527L;

	public BusinessDuplicateProjectException(String mess) {
		super(mess);
	}
	
	public BusinessDuplicateProjectException(String mess, Exception causedException) {
		super(mess, causedException);
	}
}
