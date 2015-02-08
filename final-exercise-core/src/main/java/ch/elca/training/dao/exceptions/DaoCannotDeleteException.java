package ch.elca.training.dao.exceptions;

/**
 * Exception occurred when delete operation failed. 
 * 
 * @author DTR
 */
public class DaoCannotDeleteException extends DaoOperationException {
	
	private static final long serialVersionUID = -544765217410869399L;

	public DaoCannotDeleteException(String mess) {
		super(mess);
	}
}
