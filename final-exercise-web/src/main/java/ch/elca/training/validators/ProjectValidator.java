package ch.elca.training.validators;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import ch.elca.training.dom.Project;
import ch.elca.training.services.ProjectService;
import ch.elca.training.validators.errorcode.ErrorCode;

/**
 * Validation for {@link Project}.
 * 
 * @author DTR
 */
@Component
public class ProjectValidator implements Validator {
	
	@Autowired
	private ProjectService projectService;
	
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
			errors.rejectValue(Project.PROPERTY_NUMBER, ErrorCode.ProjectNumberNull.getCode());
		} else if (project.getNumber() < 1) {
			errors.rejectValue(Project.PROPERTY_NUMBER, ErrorCode.ProjectNumberNegative.getCode());
		}
	}
	
	/**
	 * Project name.
	 */
	private void validateName(Project project, Errors errors) {
		if (StringUtils.isBlank(project.getName())) {
			errors.rejectValue(Project.PROPERTY_NAME, ErrorCode.ProjectNameBlank.getCode());
		} else if (project.getName().length() > Project.CONSTRAINT_NAME_LENGTH) {
			errors.rejectValue(Project.PROPERTY_NAME, ErrorCode.ProjectNameLengthExceed.getCode());
		}
	}
	
	/**
	 * Project customer.
	 */
	private void validateCustomer(Project project, Errors errors) {
		if (!StringUtils.isNotBlank(project.getCustomer())) {
			errors.rejectValue(Project.PROPERTY_CUSTOMER, ErrorCode.ProjectCustomerBlank.getCode());
		} else if (project.getCustomer().length() > Project.CONSTRAINT_CUSTOMER_LENGTH) {
			errors.rejectValue(Project.PROPERTY_CUSTOMER, ErrorCode.ProjectCustomerLengthExceed.getCode());
		}
	}
	
	/**
	 * Project status.
	 */
	private void validateStatus(Project project, Errors errors) {
		if (project.getStatus() == null) {
			errors.rejectValue(Project.PROPERTY_STATUS, ErrorCode.ProjectStatusNull.getCode());
		}
	}
	
	/**
	 * Project Dates.
	 */
	private void validateDates(Project project, Errors errors) {
		if (project.getStartDate() == null) {
			errors.rejectValue(Project.PROPERTY_START_DATE, ErrorCode.ProjectStartDateNull.getCode());
		}
		
		else if (project.getEndDate() != null) {
			Date startDate = project.getStartDate();
			Date endDate = project.getEndDate();
			
			if (endDate.getTime() - startDate.getTime() < 0) {
				errors.reject(ErrorCode.ProjectEndBeforeStart.getCode());
			}
		}
	}
}
