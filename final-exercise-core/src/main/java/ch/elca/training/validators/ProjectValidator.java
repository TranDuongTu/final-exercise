package ch.elca.training.validators;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import ch.elca.training.dom.Project;

/**
 * Validation for {@link Project}.
 * 
 * @author DTR
 */
@Component
public class ProjectValidator implements Validator {
	
	public static enum ErrorCode {
		NumberNull,
		NumberNegative,
		NameBlank,
		NameLengthExceed,
		CustomerBlank,
		CustomerLengthExceed,
		StatusNull,
		StartDateNull,
		EndDateLessThanStartDate,
	}
	
	public boolean supports(Class<?> clazz) {
		return Project.class.equals(clazz);
	}

	public void validate(Object object, Errors errors) {
		Project project = (Project) object;
		
		validateNumber(project, errors);
		validateName(project, errors);
		validateCustomer(project, errors);
		validateStatus(project, errors);
		validateDates(project, errors);
	}
	
	/**
	 * Project number.
	 */
	private void validateNumber(Project project, Errors errors) {
		final String REASON_NULL = "Project number must be not null";
		final String REASON_NEGATIVE = "Project number must be positive";
		
		if (project.getNumber() == null) {
			errors.rejectValue(Project.PROPERTY_NUMBER, 
					ErrorCode.NumberNull.toString(), REASON_NULL);
		}
		
		if (project.getNumber() < 1) {
			errors.rejectValue(Project.PROPERTY_NUMBER, 
					ErrorCode.NumberNegative.toString(), REASON_NEGATIVE);
		}
	}
	
	/**
	 * Project name.
	 */
	private static final int MAX_NAME_LENGTH = 100;
	private void validateName(Project project, Errors errors) {
		final String REASON_BLANK = "Project name must not be blank";
		final String REASON_EXCEED = "Project name must be in" 
				+ MAX_NAME_LENGTH + " characters";
		
		if (!StringUtils.isNotBlank(project.getName())) {
			errors.rejectValue(Project.PROPERTY_NAME, 
					ErrorCode.NameBlank.toString(), REASON_BLANK);
		}
		
		if (project.getName() != null && project.getName().length() > MAX_NAME_LENGTH) {
			errors.rejectValue(Project.PROPERTY_NAME, 
					ErrorCode.NameLengthExceed.toString(), REASON_EXCEED);
		}
	}
	
	/**
	 * Project customer.
	 */
	private static final int MAX_CUSTOMER_LENGTH = 500;
	private void validateCustomer(Project project, Errors errors) {
		final String REASON_BLANK = "Project customer must not be blank";
		final String REASON_EXCEED = "Project customer must be in" 
				+ MAX_CUSTOMER_LENGTH + " characters";
		
		if (!StringUtils.isNotBlank(project.getCustomer())) {
			errors.rejectValue(Project.PROPERTY_CUSTOMER, 
					ErrorCode.CustomerBlank.toString(), REASON_BLANK);
		}
		
		if (project.getCustomer() != null 
				&& project.getCustomer().length() > MAX_CUSTOMER_LENGTH) {
			errors.rejectValue(Project.PROPERTY_CUSTOMER, 
					ErrorCode.CustomerLengthExceed.toString(), REASON_EXCEED);
		}
	}
	
	/**
	 * Project status.
	 */
	private void validateStatus(Project project, Errors errors) {
		final String REASON_NULL = "Project Status must not be null";
		
		if (project.getStatus() == null) {
			errors.rejectValue(Project.PROPERTY_STATUS, 
					ErrorCode.StatusNull.toString(), REASON_NULL);
		}
	}
	
	/**
	 * Project Dates.
	 */
	private void validateDates(Project project, Errors errors) {
		final String REASON_START_DATE_NULL = "Project start date must be not null";
		final String REASON_END_LESS = "Project end date must be greater than start date";
		
		if (project.getStartDate() == null) {
			errors.rejectValue(Project.PROPERTY_START_DATE, 
					ErrorCode.StartDateNull.toString(), REASON_START_DATE_NULL);
		}
		
		if (project.getStartDate() != null && project.getEndDate() != null) {
			Date startDate = project.getStartDate();
			Date endDate = project.getEndDate();
			
			if (endDate.getTime() - startDate.getTime() < 0) {
				errors.reject(ErrorCode.EndDateLessThanStartDate.toString(), REASON_END_LESS);
			}
		}
	}
}
