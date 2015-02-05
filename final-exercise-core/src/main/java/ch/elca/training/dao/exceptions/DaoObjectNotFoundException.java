package ch.elca.training.dao.exceptions;

/**
 * Exception when can not query object in DAOs.
 * 
 * @author DTR
 */
public class DaoObjectNotFoundException extends DaoOperationException {
	
	private static final long serialVersionUID = -5913515502512974730L;

	public DaoObjectNotFoundException(String mess) {
		super(mess);
	}
}
