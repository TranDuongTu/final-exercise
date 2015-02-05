package ch.elca.training.dao.exceptions;

/**
 * Generic Exception for DAO operations.
 * 
 * @author DTR
 */
public class DaoOperationException extends Exception {

	private static final long serialVersionUID = -8057096598874800971L;

	public DaoOperationException(String mess) {
		super(mess);
	}
}
