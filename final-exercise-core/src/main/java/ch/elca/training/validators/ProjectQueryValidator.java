package ch.elca.training.validators;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import ch.elca.training.services.searching.ProjectQuery;

/**
 * Validation for {@link ProjectQuery}
 * 
 * @author DTR
 */
@Component
public class ProjectQueryValidator implements Validator {
	
	public static enum ErrorCode {
		NumberNull,
		NumberNegative,
		NameNull,
		NameLengthExceed,
		CustomerNull,
		CustomerLengthExceed,
		NoCriteriaSet
	}
	
	public boolean supports(Class<?> clazz) {
		return ProjectQuery.class.equals(clazz);
	}
	
	public void validate(Object object, Errors errors) {
		ProjectQuery queryToBeValidated = (ProjectQuery) object;
		
		validateNumber(queryToBeValidated, errors);
		validateName(queryToBeValidated, errors);
		validateCustomer(queryToBeValidated, errors);
			
		validateAtLeastOneCriteria(queryToBeValidated, errors);
	}
	
	/**
	 * Project number search validation
	 */
	private void validateNumber(ProjectQuery query, Errors errors) {
		final String REASON_NULL = "Number must be not null";
		final String REASON_NEGATIVE = "Number to query must be positive";
		
		if (query.getProjectNumber() == null) {
			errors.rejectValue(ProjectQuery.PROPERTY_NUMBER, 
					ErrorCode.NumberNull.toString(), REASON_NULL);
		}
		
		if (query.getProjectNumber() < 1) {
			errors.rejectValue(ProjectQuery.PROPERTY_NUMBER, 
					ErrorCode.NumberNegative.toString(), REASON_NEGATIVE);
		}
	}
	
	/**
	 * Project Name search validation
	 */
	private static final int MAX_NAME_LENGTH = 100;
	private void validateName(ProjectQuery query, Errors errors) {
		final String REASON_NULL = "Name must be not null";
		final String REASON_LENGTH_EXCEED = "Name must be less than " 
				+ MAX_NAME_LENGTH + " characters";
		
		if (query.getProjectName() == null) {
			errors.rejectValue(ProjectQuery.PROPERTY_NAME, 
					ErrorCode.NameNull.toString(), REASON_NULL);
		}
		
		if (query.getProjectName().length() > MAX_NAME_LENGTH) {
			errors.rejectValue(ProjectQuery.PROPERTY_NAME, 
					ErrorCode.NameLengthExceed.toString(), REASON_LENGTH_EXCEED);
		}
	}
	
	/**
	 * Project customer search validation
	 */
	private static final int MAX_CUSTOMER_LENGTH = 500;
	private void validateCustomer(ProjectQuery query, Errors errors) {
		final String REASON_NULL = "Customer must be not null";
		final String REASON_LENGTH_EXCEED = "Customer must less than "
				+ MAX_CUSTOMER_LENGTH + " characters";
		
		if (query.getCustomer() == null) {
			errors.rejectValue(ProjectQuery.PROPERTY_CUSTOMER, 
					ErrorCode.CustomerNull.toString(), REASON_NULL);
		}
		
		if (query.getCustomer().length() > MAX_CUSTOMER_LENGTH) {
			errors.rejectValue(ProjectQuery.PROPERTY_CUSTOMER, 
					ErrorCode.CustomerLengthExceed.toString(), REASON_LENGTH_EXCEED);
		}
	}
	
	/**
	 * At least one search criteria must be set.
	 */
	private void validateAtLeastOneCriteria(ProjectQuery query, Errors errors) {
		final String REASON = "At least one criteria must be set";
		
		if (!StringUtils.isNotBlank(query.getCustomer()) &&
				!StringUtils.isNotBlank(query.getProjectName()) &&
				query.getProjectStatus() == null &&
				query.getProjectNumber() == null) {
			errors.reject(ErrorCode.NoCriteriaSet.toString(), REASON);
		}
	}
}
