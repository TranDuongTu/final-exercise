package ch.elca.training.services.exceptions;

public class ServiceInvalidProjectNumberException extends ServiceInvalidInputException {
	private static final long serialVersionUID = 6472890512552013418L;

	public ServiceInvalidProjectNumberException(String mess) {
		super(mess);
	}
}
