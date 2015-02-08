package ch.elca.training.dao.exceptions;

/**
 * Exception occurred when saving object failed.
 * 
 * @author DTR
 */
public class DaoCannotSaveException extends DaoOperationException {

	private static final long serialVersionUID = 7131066796439066661L;

	public DaoCannotSaveException(String mess) {
		super(mess);
	}
}
