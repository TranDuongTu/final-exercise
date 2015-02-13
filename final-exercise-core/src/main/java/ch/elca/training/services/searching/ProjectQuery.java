package ch.elca.training.services.searching;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ch.elca.training.dom.Status;

/**
 * Query object used to search for projects.
 * 
 * @author DTR
 */
@Component("defaultProjectQuery")
@Scope("singleton")
public class ProjectQuery {
	
	/**
	 * Default values.
	 */
	public static final Integer DEFAULT_NUMBER = null;
	public static final String DEFAULT_NAME = "";
	public static final Status DEFAULT_STATUS = null;
	public static final String DEFAULT_CUSTOMER = "";
	public static final int DEFAULT_START_INDEX = 0;
	public static final int DEFAULT_TOTAL = 0;
	public static final int DEFAULT_MAX_ITEMS = 10;
	
	// ========================================================================
	// INFORMATIONS CARRIED
	// ========================================================================
	
	/**
	 * Search by Project number.
	 */
	public static final String PROPERTY_NUMBER = "projectNumber";
	private Integer projectNumber = DEFAULT_NUMBER;
	
	/**
	 * Search by project name.
	 */
	public static final String PROPERTY_NAME = "projectName";
	private String projectName = DEFAULT_NAME;
	
	/**
	 * Search by customer.
	 */
	public static final String PROPERTY_CUSTOMER = "customer";
	private String customer = DEFAULT_CUSTOMER;
	
	/**
	 * Search by status.
	 */
	public static final String PROPERTY_STATUS = "status";
	private Status projectStatus = DEFAULT_STATUS;
	
	/**
	 * Total number of matched projects.
	 */
	public static final String PROPERTY_TOTAL = "total";
	private int total = DEFAULT_TOTAL;
	
	/**
	 * Start index of matched projects that should be showed.
	 */
	public static final String PROPERTY_START = "start";
	private int start = DEFAULT_START_INDEX;
	
	/**
	 * Maximum number of projects on a page.
	 */
	public static final String PROPERTY_MAX = "max";
	private int max = DEFAULT_MAX_ITEMS;
	
	/**
	 * Project IDs that has been marked as delete.
	 */
	public static final String PROPERTY_DELETES = "deletes";
	private Map<Integer, Boolean> deletes = new HashMap<Integer, Boolean>();
	
	// ========================================================================
	// GETTERs AND SETTERs
	// ========================================================================
	
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

	public Map<Integer, Boolean> getDeletes() {
		return deletes;
	}

	public void setDeletes(Map<Integer, Boolean> deletes) {
		this.deletes = deletes;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		List<Integer> requestedDeleteIds = new ArrayList<Integer>();
		for (Entry<Integer, Boolean> entry : deletes.entrySet()) {
			if (entry.getValue()) {
				requestedDeleteIds.add(entry.getKey());
			}
		}
		
		return String.format("{name: %s, "
				+ "number: %s, "
				+ "status: %s, "
				+ "customer: %s, "
				+ "total: %d, "
				+ "start index: %d, "
				+ "max items: %d, "
				+ "delete ids: %s}",
				projectName, projectNumber, projectStatus, 
				customer, total, start, max, requestedDeleteIds);
	}
}
