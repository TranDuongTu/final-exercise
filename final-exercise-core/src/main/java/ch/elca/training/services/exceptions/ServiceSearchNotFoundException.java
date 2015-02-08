package ch.elca.training.services.exceptions;

public class ServiceSearchNotFoundException extends ServiceOperationException {
	
	private static final long serialVersionUID = -618647230626923512L;

	public ServiceSearchNotFoundException(String mess) {
		super(mess);
	}
}
