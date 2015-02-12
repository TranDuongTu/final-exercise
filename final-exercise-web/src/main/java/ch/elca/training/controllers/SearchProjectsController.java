package ch.elca.training.controllers;

import java.util.List;
import java.util.Map.Entry;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ch.elca.training.constants.ModelKeys;
import ch.elca.training.constants.Urls;
import ch.elca.training.constants.ViewNames;
import ch.elca.training.dom.Project;
import ch.elca.training.exceptions.BusinessOperationException;
import ch.elca.training.services.exceptions.ServiceOperationException;
import ch.elca.training.services.searching.ProjectQuery;
import ch.elca.training.validators.ProjectQueryValidator;

/**
 * Handle requests about searching projects.
 * 
 * @author DTR
 */
@Controller
@RequestMapping(Urls.SEARCH)
@SessionAttributes(value = {ModelKeys.PROJECT_QUERY})
public class SearchProjectsController extends BaseController {
	
	@Autowired
	@Qualifier("defaultProjectQuery")
	private ProjectQuery defaultProjectQuery;
	
	@Autowired
	private ProjectQueryValidator projectQueryValidator;
	
	/**
	 * {@link InitBinder} for {@link ProjectQuery}.
	 */
	@InitBinder(ModelKeys.PROJECT_QUERY)
	public void projectQueryBinding(WebDataBinder binder) {
		binder.setValidator(projectQueryValidator);
	}

	/************************************************************************************
	 * GET request for showing search form and result. All requests want to show
	 * search result will probably redirect to this handler method.
	 * 
	 * Search page need:
	 * 	- List of projects to display.
	 *  - Signal from Edit page for whether or not they succeeded in updating Project
	 *  - Page parameter for template to know which page is being rendered.
	 ************************************************************************************/
	@RequestMapping(method = RequestMethod.GET)
	protected String display(Model model) {
		try {
			logger.info("Going to serve GET request for showing search form");
			
			/* Adding default search query for the first time */
			if (!model.containsAttribute(ModelKeys.PROJECT_QUERY)) {
				model.addAttribute(ModelKeys.PROJECT_QUERY, defaultProjectQuery);
			} 
			
			/* This time is for locale change */
			else if (!model.containsAttribute(ModelKeys.PROJECTS)) {
				requeryOnLocaleChange(model);
			}
			
			return showPage(ViewNames.SEARCH, model);
		} catch (Exception e) {
			String message = "Error when showing search page";
			logger.debug(message + ": " + e.getMessage());
			throw new BusinessOperationException(message, e.getMessage());
		}
	}
	
	/**********************************************************************************************
	 * GET request that was redirected from EDIT page.
	 **********************************************************************************************/
	@RequestMapping(value = Urls.SEARCH_BACK, method = RequestMethod.GET)
	protected String handleRedirectFromEditPage(Model model,
			final RedirectAttributes redirectAttributes,
			@ModelAttribute(ModelKeys.IS_SUCCESS) boolean isSuccess,
			@ModelAttribute(ModelKeys.PROJECT_QUERY) ProjectQuery projectQuery) {
		try {
			logger.info("Serve GET request redirected from EDIT page");
			
			addSuccessSignalFromEdit(redirectAttributes, isSuccess);
			List<Project> projects = requeryAndUpdateProjectQuery(projectQuery, model);
			
	        return redirectProjectsFound(redirectAttributes, projects);
		} catch (ServiceOperationException e) {
			String message = "Error from Service layer when searching for projects";
			logger.debug(message + ": " + e.getMessage());
			throw new BusinessOperationException(message, e.getMessage());
		} catch (Exception e) {
			String message = "Unexpected error when processing redirect from EDIT page: ";
			logger.debug(message + ": " + e.getMessage());
			throw new BusinessOperationException(message, e.getMessage());
		}
	}

	/**********************************************************************************************
	 * POST request when user submit search criteria.
	 * Using POST-REDIRECT-GET pattern.
	 **********************************************************************************************/
	@RequestMapping(method = RequestMethod.POST)
    protected String submitSearch(Model model,
    		@ModelAttribute(ModelKeys.PROJECT_QUERY) @Valid ProjectQuery projectQuery, 
    		BindingResult queryBindingResult,
    		RedirectAttributes redirectAttributes) {
		try {
			logger.info("Going to serve POST request when submitting search");
			
			if (queryBindingResult.hasErrors()) {
				return returnForBindingErrors(ViewNames.SEARCH, projectQuery, queryBindingResult, model);
			}
	    	
	    	List<Project> projects = requeryAndUpdateProjectQuery(projectQuery, model);

	        return redirectProjectsFound(redirectAttributes, projects);
		} catch (ServiceOperationException e) {
			String message = "Error from Service layer when searching projects";
			logger.debug(message + ": " + e.getMessage());
			throw new BusinessOperationException(message, e.getMessage());
		} catch (Exception e) {
			String message = "Error when processing search form submit: ";
			logger.debug(message + ": " + e.getMessage());
			throw new BusinessOperationException(message, e.getMessage());
		}
    }
	
	/**********************************************************************************************
	 * Handle delete operation.
	 **********************************************************************************************/
	@RequestMapping(value = Urls.SEARCH_DELETE, method = RequestMethod.POST)
    protected String submitSearchDelete(Model model,
    		@ModelAttribute(ModelKeys.PROJECT_QUERY) @Valid ProjectQuery projectQuery, 
    		BindingResult queryBindingResult,
    		RedirectAttributes redirectAttributes) {
    	try {
    		logger.debug("Going to serve POST request that want to delete projects");
    		
    		if (queryBindingResult.hasErrors()) {
    			return returnForBindingErrors(ViewNames.SEARCH, projectQuery, queryBindingResult, model);
    		}
	    
	    	deleteRequestedProjects(projectQuery);
	    	
	    	List<Project> projects = requeryAndUpdateProjectQuery(projectQuery, model);
    		
    		return redirectProjectsFound(redirectAttributes, projects);
    	} catch (BusinessOperationException e) {
    		String message = "Error from Service layer trying to delete and requery";
    		logger.debug(message + ": " + e.getMessage());
			throw new BusinessOperationException(message, e.getMessage());
    	} catch (Exception e) {
    		String message = "Error when processing delete operation submit";
    		logger.debug(message + ": " + e.getMessage());
			throw new BusinessOperationException(message, e.getMessage());
    	}
    }
	
	// ============================================================================================
	// PRIVATE HELPERs
	// ============================================================================================
	
	/**
	 * Query Projects with {@link ProjectQuery}.
	 */
	private List<Project> queryProjects(ProjectQuery projectQuery) throws ServiceOperationException {
    	logger.debug("Query Projects with: " + projectQuery);
    	List<Project> projects = projectService.searchProject(projectQuery);
    	logger.debug("Totally retrieved projects: " + projects.size());
    	return projects;
	}
	
	/**
	 * Redirect list of projects to search page.
	 */
	private String redirectProjectsFound(RedirectAttributes redirectAttributes, List<Project> projects) {
		final String url = Urls.REDIRECT_PREFIX + Urls.SEARCH;
		redirectAttributes.addFlashAttribute(ModelKeys.PROJECTS, projects);
		logger.info("Redirect " + projects.size() + " projects to: " + url);
		return url;
	}
	
	/**
	 * Add update success information from EDIT page.
	 */
	private void addSuccessSignalFromEdit(RedirectAttributes redirectAttributes, boolean isSuccess) {
		if (isSuccess) {
			logger.info("Edit has successfully updated Project");
		} else {
			logger.info("Edit has unsuccessfully updated Project");
		}
		redirectAttributes.addFlashAttribute(ModelKeys.IS_SUCCESS, isSuccess);
	}
	
	/**
	 * re-query projects and Update {@link ProjectQuery}.
	 */
	private List<Project> requeryAndUpdateProjectQuery(ProjectQuery projectQuery, Model model) 
			throws ServiceOperationException {
		logger.debug("Attempt to query projects match: " + projectQuery);
		List<Project> projects = queryProjects(projectQuery);
		
		/* Retain current start index */
		logger.debug("Retrieved " + projects.size() + " projects");
    	projectQuery.setTotal(projectService.countProjectMatchExcludePaging(projectQuery));
    	while (projectQuery.getStart() >= projectQuery.getTotal()) {
    		if (projectQuery.getStart() == 0) {
    			break;
    		}
    		
    		if (projectQuery.getStart() - projectQuery.getMax() >= 0) {
    			projectQuery.setStart(projectQuery.getStart() - projectQuery.getMax());
    		} else {
    			projectQuery.setStart(0);
    		}
    	}
    	
    	/* Update delete indices */
    	projectQuery.getDeletes().clear();
    	for (Project project : projects) {
    		projectQuery.getDeletes().put(project.getNumber(), false);
    	}
    	
    	/* Update session */
    	model.addAttribute(ModelKeys.PROJECT_QUERY, projectQuery);
    	
    	return projects;
	}
	
	/**
	 * Delete Projects
	 */
	private void deleteRequestedProjects(ProjectQuery projectQuery) throws ServiceOperationException {
		logger.debug("Attempt to delete projects with query: " + projectQuery);
		for (Entry<Integer, Boolean> entry : projectQuery.getDeletes().entrySet()) {
    		if (projectQuery.getDeletes().get(entry.getKey())) {
    			projectService.deleteProjectNumber(entry.getKey());
    		}
    	}
	}
	
	/**
	 * Re-query on locale changed.
	 */
	private void requeryOnLocaleChange(Model model) throws ServiceOperationException {
		ProjectQuery projectQuery = (ProjectQuery) model.asMap().get(ModelKeys.PROJECT_QUERY);
		Errors errors = new BeanPropertyBindingResult(projectQuery, ModelKeys.PROJECT_QUERY);
		projectQueryValidator.validate(projectQuery, errors);
		
		if (!errors.hasErrors()) {
			List<Project> projects = requeryAndUpdateProjectQuery(projectQuery, model);
			model.addAttribute(ModelKeys.PROJECTS, projects);
		}
	}
}