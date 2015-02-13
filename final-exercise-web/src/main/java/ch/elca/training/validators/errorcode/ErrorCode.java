package ch.elca.training.validators.errorcode;

/**
 * Error codes that conform ones defined in {@link MessageSource}.
 * 
 * @author DTR
 */
public enum ErrorCode {
	ProjectNumberNull("error.editpage.projectnumbernull"),
	ProjectNumberNegative("error.editpage.projectnumbernegative"),
	ProjectNameBlank("error.editpage.projectnamenull"),
	ProjectNameLengthExceed("error.editpage.projectnameexceed"),
	ProjectCustomerBlank("error.editpage.projectcustomernull"),
	ProjectCustomerLengthExceed("error.editpage.projectcustomerexceed"),
	ProjectStatusNull("error.editpage.projectstatusnull"),
	ProjectStartDateNull("error.editpage.projectstartdatenull"),
	ProjectEndBeforeStart("error.editpage.projectendlessstart"),
	QueryNumberNegative("error.searchpage.querynumbernegative"),
	QueryNameNull("error.searchpage.querynamenull"),
	QueryNameLengthExceed("error.searchpage.querynamelengthexceed"),
	QueryCustomerNull("error.searchpage.querycustomernull"),
	QueryCustomerLengthExceed("error.searchpage.querycustomerlengthexceed"),
	QueryNoCriteriaSet("error.searchpage.querynocriteriaset"),
	QueryPagingIndicesInvalid("error.searchpage.querypagingindicesinvalid");
	
	private String code;
	
	private ErrorCode(String code) {
		this.code = code;
	}
	
	public String getCode() {
		return code;
	}
}
