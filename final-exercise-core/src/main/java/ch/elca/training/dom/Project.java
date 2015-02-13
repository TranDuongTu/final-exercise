package ch.elca.training.dom;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Represent a Project and its information.
 * 
 * @author DTR
 */
@Entity
@Table(name = Project.TABLE_NAME)
public class Project extends BaseDom {
	
	public static final String TABLE_NAME = "project_table";
	
	/**
	 * Project Number.
	 */
	public static final String PROPERTY_NUMBER = "number";
	public static final String COLUMN_NUMBER = "pro_number";
	
	@Column(name = COLUMN_NUMBER, nullable = false, unique = true)
	private Integer number;
	
	/**
	 * Project Name.
	 */
	public static final String PROPERTY_NAME = "name";
	public static final String COLUMN_NAME = "pro_name";
	public static final int CONSTRAINT_NAME_LENGTH = 100;
	
	@Column(name = COLUMN_NAME, nullable = false, length = CONSTRAINT_NAME_LENGTH)
	private String name;
	
	/**
	 * Project Customer.
	 */
	public static final String PROPERTY_CUSTOMER = "customer";
	public static final String COLUMN_CUSTOMER = "pro_customer";
	public static final int CONSTRAINT_CUSTOMER_LENGTH = 500;
	
	@Column(name = COLUMN_CUSTOMER, nullable = false, length = CONSTRAINT_CUSTOMER_LENGTH)
	private String customer;
	
	/**
	 * Project status.
	 */
	public static final String PROPERTY_STATUS = "status";
	public static final String COLUMN_STATUS = "pro_status";
	public static final int CONSTRAINT_STATUS_LENGTH = 3;
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = COLUMN_STATUS, nullable = false, length = CONSTRAINT_STATUS_LENGTH)
	private Status status;
	
	/**
	 * Start date.
	 */
	public static final String PROPERTY_START_DATE = "startDate";
	public static final String COLUMN_START_DATE = "pro_start_date";
	
	@Temporal(value = TemporalType.DATE)
	@Column(name = COLUMN_START_DATE, nullable = false)
	private Date startDate;
	
	/**
	 * End date.
	 */
	public static final String PROPERTY_END_DATE = "endDate";
	public static final String COLUMN_END_DATE = "pro_end_date";
	
	@Temporal(value = TemporalType.DATE)
	@Column(name = COLUMN_END_DATE)
	private Date endDate;
	
	// ==================================================================================
	// GETTERs AND SETTERs
	// ==================================================================================
	
	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public String toString() {
		return String.format("{id: %d, "
				+ "number: %d, "
				+ "name: %s, "
				+ "customer: %s, "
				+ "status: %s, "
				+ "endDate: %s, "
				+ "startDate: %s}", 
				id, number, name, customer, 
				status, endDate, startDate);
	};
}
