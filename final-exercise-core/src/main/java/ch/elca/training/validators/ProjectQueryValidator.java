package ch.elca.training.validators;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import ch.elca.training.services.searching.ProjectQuery;

/**
 * Validation for {@link ProjectQuery}.
 * 
 * @author DTR
 */
@Component
public class ProjectQueryValidator implements Validator {
	
	public static enum ErrorCode {
		QueryNumberNegative,
		QueryNameNull,
		QueryNameLengthExceed,
		QueryCustomerNull,
		QueryCustomerLengthExceed,
		QueryNoCriteriaSet,
		QueryPagingIndicesInvalid,
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
		
		validatePagingIndices(queryToBeValidated, errors);
	}
	
	/**
	 * Project number search validation
	 */
	private void validateNumber(ProjectQuery query, Errors errors) {
		if (query.getProjectNumber() != null && query.getProjectNumber() < 1) {
			errors.rejectValue(ProjectQuery.PROPERTY_NUMBER, ErrorCode.QueryNumberNegative.toString());
		}
	}
	
	/**
	 * Project Name search validation
	 */
	private static final int MAX_NAME_LENGTH = 100;
	private void validateName(ProjectQuery query, Errors errors) {
		if (query.getProjectName() == null) {
			errors.rejectValue(ProjectQuery.PROPERTY_NAME, ErrorCode.QueryNameNull.toString());
		}
		
		if (query.getProjectName().length() > MAX_NAME_LENGTH) {
			errors.rejectValue(ProjectQuery.PROPERTY_NAME, ErrorCode.QueryNameLengthExceed.toString());
		}
	}
	
	/**
	 * Project customer search validation
	 */
	private static final int MAX_CUSTOMER_LENGTH = 500;
	private void validateCustomer(ProjectQuery query, Errors errors) {
		if (query.getCustomer() == null) {
			errors.rejectValue(ProjectQuery.PROPERTY_CUSTOMER, ErrorCode.QueryCustomerNull.toString());
		}
		
		if (query.getCustomer().length() > MAX_CUSTOMER_LENGTH) {
			errors.rejectValue(ProjectQuery.PROPERTY_CUSTOMER, ErrorCode.QueryCustomerLengthExceed.toString());
		}
	}
	
	/**
	 * At least one search criteria must be set.
	 */
	private void validateAtLeastOneCriteria(ProjectQuery query, Errors errors) {
		if (!StringUtils.isNotBlank(query.getCustomer()) &&
				!StringUtils.isNotBlank(query.getProjectName()) &&
				query.getProjectStatus() == null &&
				query.getProjectNumber() == null) {
			errors.reject(ErrorCode.QueryNoCriteriaSet.toString());
		}
	}
	
	/**
	 * Paging indices.
	 */
	private void validatePagingIndices(ProjectQuery query, Errors errors) {
		if (query.getMax() <= 0) {
			errors.reject(ErrorCode.QueryPagingIndicesInvalid.toString());
		}
	}
}
