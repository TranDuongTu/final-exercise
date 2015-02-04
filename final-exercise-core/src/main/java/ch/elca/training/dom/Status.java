package ch.elca.training.dom;

/**
 * Status for {@link Project}
 * 
 * @author DTR
 */
public enum Status {
	INV("Invalidate"), 
	TOV("To Validate"), 
	VAL("Validated"), 
	FIN("Finished");
	
	private String desc;
	
	private Status(String desc) {
		this.desc = desc;
	}
	
	public String getDesc() {
		return this.desc;
	}
}
