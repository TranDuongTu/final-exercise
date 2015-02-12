package ch.elca.training.exceptions;

/**
 * Root {@link Exception} for all occurred on Business layer.
 * 
 * @author DTR
 */
public class BusinessOperationException extends RuntimeException {
	private static final long serialVersionUID = 3158522382181203844L;
	
	private String underLayerMessage;

	public BusinessOperationException(String mess) {
		super(mess);
	}
	
	public BusinessOperationException(String mess, String technicalMessage) {
		super(mess);
		this.underLayerMessage = technicalMessage;
	}
	
	public String getUnderLayerlMessage() {
		return this.underLayerMessage;
	}
}
