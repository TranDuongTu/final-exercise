package ch.elca.training.services.searching;

import ch.elca.training.dom.Status;

/**
 * Supported criteria for searching projects.
 * 
 * @author DTR
 */
public class ProjectQuery {
	
	/**
	 * Factory instance for getting default search criteria.
	 */
	private static ProjectQuery instance;
	
	public static ProjectQuery defaultCriteria() {
		if (instance == null) {
			instance = new ProjectQuery();
			instance.setCustomer("");
			instance.setProjectName("");
			instance.setProjectStatus(null);
			instance.setTotal(1);
			instance.setMax(10);
		}
		
		return instance;
	}
	
	/**
	 * Object instance variables.
	 */
	public static final String PROPERTY_NUMBER = "projectNumber";
	private Integer projectNumber;
	
	public static final String PROPERTY_NAME = "projectName";
	private String projectName;
	
	public static final String PROPERTY_CUSTOMER = "customer";
	private String customer;
	
	public static final String PROPERTY_STATUS = "status";
	private Status projectStatus;
	
	public static final String PROPERTY_TOTAL = "total";
	private int total;
	
	public static final String PROPERTY_START = "start";
	private int start;
	
	public static final String PROPERTY_MAX = "max";
	private int max;
	
	public Integer getProjectNumber() {
		return projectNumber;
	}
	
	public void setProjectNumber(Integer projectNumber) {
		this.projectNumber = projectNumber;
	}
	
	public String getProjectName() {
		return projectName;
	}
	
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	
	public String getCustomer() {
		return customer;
	}
	
	public void setCustomer(String customer) {
		this.customer = customer;
	}
	
	public Status getProjectStatus() {
		return projectStatus;
	}
	
	public void setProjectStatus(Status projectStatus) {
		this.projectStatus = projectStatus;
	}
	
	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getMax() {
		return max;
	}

	public void setMax(int max) {
		this.max = max;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return String.format("{name: %s, number: %s, status: %s, customer: %s; total: %d; start: %d; max: %d}",
				projectName, projectNumber, projectStatus, customer, total, start, max);
	}
}
