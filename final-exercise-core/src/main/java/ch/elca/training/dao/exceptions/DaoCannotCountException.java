package ch.elca.training.dao.exceptions;

/**
 * Exception occurred when count operation failed.
 * 
 * @author DTR
 */
public class DaoCannotCountException extends DaoOperationException {
	
	private static final long serialVersionUID = -5085372369096981662L;

	public DaoCannotCountException(String mess) {
		super(mess);
	}
}
