package ch.elca.training.validators;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import ch.elca.training.dom.Project;
import ch.elca.training.services.searching.ProjectQuery;
import ch.elca.training.validators.errorcode.ErrorCode;

/**
 * Validation for {@link ProjectQuery}.
 * 
 * @author DTR
 */
@Component
public class ProjectQueryValidator implements Validator {
	
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
			errors.rejectValue(ProjectQuery.PROPERTY_NUMBER, ErrorCode.QueryNumberNegative.getCode());
		}
	}
	
	/**
	 * Project Name search validation
	 */
	private void validateName(ProjectQuery query, Errors errors) {
		if (query.getProjectName() == null) {
			errors.rejectValue(ProjectQuery.PROPERTY_NAME, ErrorCode.QueryNameNull.getCode());
		}
		
		if (query.getProjectName().length() > Project.CONSTRAINT_NAME_LENGTH) {
			errors.rejectValue(ProjectQuery.PROPERTY_NAME, ErrorCode.QueryNameLengthExceed.getCode());
		}
	}
	
	/**
	 * Project customer search validation
	 */
	private void validateCustomer(ProjectQuery query, Errors errors) {
		if (query.getCustomer() == null) {
			errors.rejectValue(ProjectQuery.PROPERTY_CUSTOMER, ErrorCode.QueryCustomerNull.getCode());
		}
		
		if (query.getCustomer().length() > Project.CONSTRAINT_CUSTOMER_LENGTH) {
			errors.rejectValue(ProjectQuery.PROPERTY_CUSTOMER, ErrorCode.QueryCustomerLengthExceed.getCode());
		}
	}
	
	/**
	 * At least one search criteria must be set.
	 */
	private void validateAtLeastOneCriteria(ProjectQuery query, Errors errors) {
		if (StringUtils.isBlank(query.getCustomer()) &&
				StringUtils.isBlank(query.getProjectName()) &&
				query.getProjectStatus() == null &&
				query.getProjectNumber() == null) {
			errors.reject(ErrorCode.QueryNoCriteriaSet.getCode());
		}
	}
	
	/**
	 * Paging indices.
	 */
	private void validatePagingIndices(ProjectQuery query, Errors errors) {
		if (query.getMax() <= 0) {
			errors.reject(ErrorCode.QueryPagingIndicesInvalid.getCode());
		}
	}
}
