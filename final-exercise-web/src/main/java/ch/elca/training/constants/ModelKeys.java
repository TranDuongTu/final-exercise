package ch.elca.training.constants;

/**
 * Constants used to reference objects in Model.
 * 
 * @author DTR
 */
public final class ModelKeys {
	
	/**
	 * Indicate which page we are currently in.
	 */
	public static final String PAGE = "page";
	
	// ========================================================================
	// SEARCH PAGE
	// ========================================================================
	
	/**
	 * List of {@link Project} to be displayed on search page.
	 */
	public static final String PROJECTS = "projects";
	
	/**
	 * Attribute that present when search return 0 Projects match.
	 */
	public static final String NOT_FOUND = "notFound";
	
	/**
	 * Is update success?
	 */
	public static final String IS_SUCCESS = "isSuccess";
	
	// ========================================================================
	// EDIT PAGE
	// ========================================================================
	
	/**
	 * Project to show on Edit page.
	 */
	public static final String PROJECT = "project";
	
	// ========================================================================
	// ERROR PAGE
	// ========================================================================
	
	/**
	 * Error Message to be showed on error page.
	 */
	public static final String ERROR_MESSAGE = "errorMessage";
	
	// ========================================================================
	// SESSION
	// ========================================================================
	
	/**
	 * {@link ProjectQuery} object on search project page.
	 */
	public static final String PROJECT_QUERY = "projectQuery";
}
