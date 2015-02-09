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
		ProjectNumberNull,
		ProjectNumberNegative,
		ProjectNameBlank,
		ProjectNameLengthExceed,
		ProjectCustomerBlank,
		ProjectCustomerLengthExceed,
		ProjectStatusNull,
		ProjectStartDateNull,
		ProjectEndDateLessThanStartDate,
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
		if (project.getNumber() == null) {
			errors.rejectValue(Project.PROPERTY_NUMBER, ErrorCode.ProjectNumberNull.toString());
		} else if (project.getNumber() < 1) {
			errors.rejectValue(Project.PROPERTY_NUMBER, ErrorCode.ProjectNumberNegative.toString());
		}
	}
	
	/**
	 * Project name.
	 */
	public static final int MAX_NAME_LENGTH = 100;
	private void validateName(Project project, Errors errors) {
		if (StringUtils.isBlank(project.getName())) {
			errors.rejectValue(Project.PROPERTY_NAME, ErrorCode.ProjectNameBlank.toString());
		} else if (project.getName().length() > MAX_NAME_LENGTH) {
			errors.rejectValue(Project.PROPERTY_NAME, ErrorCode.ProjectNameLengthExceed.toString());
		}
	}
	
	/**
	 * Project customer.
	 */
	private static final int MAX_CUSTOMER_LENGTH = 500;
	private void validateCustomer(Project project, Errors errors) {
		if (!StringUtils.isNotBlank(project.getCustomer())) {
			errors.rejectValue(Project.PROPERTY_CUSTOMER, ErrorCode.ProjectCustomerBlank.toString());
		} else if (project.getCustomer().length() > MAX_CUSTOMER_LENGTH) {
			errors.rejectValue(Project.PROPERTY_CUSTOMER, ErrorCode.ProjectCustomerLengthExceed.toString());
		}
	}
	
	/**
	 * Project status.
	 */
	private void validateStatus(Project project, Errors errors) {
		if (project.getStatus() == null) {
			errors.rejectValue(Project.PROPERTY_STATUS, ErrorCode.ProjectStatusNull.toString());
		}
	}
	
	/**
	 * Project Dates.
	 */
	private void validateDates(Project project, Errors errors) {
		if (project.getStartDate() == null) {
			errors.rejectValue(Project.PROPERTY_START_DATE, ErrorCode.ProjectStartDateNull.toString());
		}
		
		else if (project.getEndDate() != null) {
			Date startDate = project.getStartDate();
			Date endDate = project.getEndDate();
			
			if (endDate.getTime() - startDate.getTime() < 0) {
				errors.reject(ErrorCode.ProjectEndDateLessThanStartDate.toString());
			}
		}
	}
}
