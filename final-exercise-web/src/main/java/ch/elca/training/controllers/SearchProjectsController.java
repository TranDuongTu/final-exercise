package ch.elca.training.controllers;

import java.util.List;
import java.util.Map.Entry;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
import ch.elca.training.services.ProjectService;
import ch.elca.training.services.exceptions.ServiceOperationException;
import ch.elca.training.services.searching.ProjectQuery;
import ch.elca.training.validators.ProjectQueryValidator;

/**
 * Handle requests for searching projects.
 * 
 * @author DTR
 */
@Controller
@RequestMapping(Urls.SEARCH)
@SessionAttributes(value = {ModelKeys.PROJECT_QUERY})
public class SearchProjectsController extends BaseController {

	@Autowired
	private ProjectService projectService;
	
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

	/**
	 * GET request for showing search form.
	 */
	@RequestMapping(method = RequestMethod.GET)
	protected String showSearchForm(Model model) {
		try {
			logger.info("Going to serve GET request for search form");
			
			/* Adding default search */
			if (!model.containsAttribute(ModelKeys.PROJECT_QUERY)) {
				model.addAttribute(ModelKeys.PROJECT_QUERY, defaultProjectQuery);
			}
			
			logger.info("View name: " + ViewNames.SEARCH);
			model.addAttribute(ModelKeys.PAGE, "search");
			return ViewNames.SEARCH;
		} catch (Exception e) {
			logger.debug("Unexpected error when processing show search form request");
			throw new BusinessOperationException(e.getMessage());
		}
	}
	
	/**
	 * GET request that was redirected from EDIT page.
	 */
	@RequestMapping(value = Urls.SEARCH_BACK, method = RequestMethod.GET)
	protected String showSearchFormAfterEdit(Model model,
			final RedirectAttributes redirectAttributes,
			@ModelAttribute(ModelKeys.IS_SUCCESS) boolean isSuccess,
			@ModelAttribute(ModelKeys.PROJECT_QUERY) ProjectQuery projectQuery) {
		try {
			logger.info("Serve GET request redirected from EDIT page");
			
			if (isSuccess) {
				logger.info("Edit has successfully updated Project");
			} else {
				logger.info("Edit has unsuccessfully updated Project");
			}
			
			List<Project> projects = queryProjects(projectQuery);
	    	
	    	/* Update Project query for the paging information */
	    	projectQuery.setTotal(projectService.countProjectMatch(projectQuery));
	    	model.addAttribute(ModelKeys.PROJECT_QUERY, projectQuery);
			
	        /* Redirect to normal search handler method */
	        redirectAttributes.addFlashAttribute(ModelKeys.PROJECTS, projects);
	        redirectAttributes.addFlashAttribute(ModelKeys.IS_SUCCESS, isSuccess);
	        
			logger.info("Redirect to: " + Urls.REDIRECT_PREFIX + Urls.SEARCH);
			return Urls.REDIRECT_PREFIX + Urls.SEARCH;
		} catch (ServiceOperationException e) {
			logger.debug("Unexpected error when processing show search form request");
			throw new BusinessOperationException(e.getMessage());
		}
	}

	/**
	 * POST request for projects matching search criteria.
	 * Using POST-REDIRECT-GET pattern.
	 */
	@RequestMapping(method = RequestMethod.POST)
    protected String submitSearch(
    		@ModelAttribute(ModelKeys.PROJECT_QUERY) @Valid ProjectQuery projectQuery, 
    		BindingResult queryBindingResult,
    		Model model,
    		RedirectAttributes flashAttributes) {
    	
		try {
			logger.info("Going to serve POST request when submitting search");
			
			/* Criteria need to be validated and retained in session */
	    	if (queryBindingResult.hasErrors()) {
	    		logger.debug("Binding errors for query binding: " + projectQuery);
	    		logger.info("View name: " + ViewNames.SEARCH);
	    		
	    		model.addAttribute(ModelKeys.PAGE, "search");
	    		return ViewNames.SEARCH;
	    	}
	    	
	    	List<Project> projects = queryProjects(projectQuery);
	    	
	    	/* Update Project query for the paging information */
	    	projectQuery.setTotal(projectService.countProjectMatch(projectQuery));
	    	projectQuery.getDeletes().clear();
	    	for (Project project : projects) {
	    		projectQuery.getDeletes().put(project.getNumber(), false);
	    	}
	    	model.addAttribute(ModelKeys.PROJECT_QUERY, projectQuery);
	
	        /* Flash attributes for redirecting context */
	    	flashAttributes.addFlashAttribute(ModelKeys.NOT_FOUND, projects.size() == 0);
	        flashAttributes.addFlashAttribute(ModelKeys.PROJECTS, projects);
	
	        logger.info("Goto: " + Urls.REDIRECT_PREFIX + ViewNames.SEARCH);
	        return Urls.REDIRECT_PREFIX + Urls.SEARCH;
		} catch (ServiceOperationException e) {
			logger.debug("Error when attempting to consult the Service: " + e.getMessage());
			throw new BusinessOperationException(e.getMessage());
		} catch (Exception e) {
			logger.debug("Unexpected error when processing search form submit: " + e.getMessage());
			throw new BusinessOperationException(e.getMessage());
		}
    }
	
	/**
	 * Handle delete operation.
	 */
	@RequestMapping(value = Urls.SEARCH_DELETE, method = RequestMethod.POST)
    protected String submitSearchDelete(
    		@ModelAttribute(ModelKeys.PROJECT_QUERY) @Valid ProjectQuery projectQuery, 
    		BindingResult queryBindingResult,
    		Model model,
    		RedirectAttributes flashAttributes) {
    	try {
    		logger.debug("Going to serve POST request that want to delete projects");
    		
    		/* Criteria need to be validated and retained in session */
	    	if (queryBindingResult.hasErrors()) {
	    		logger.debug("Binding errors for query binding: " + projectQuery);
	    		logger.info("View name: " + ViewNames.SEARCH);
	    		
	    		model.addAttribute(ModelKeys.PAGE, "search");
	    		return ViewNames.SEARCH;
	    	}
	    
	    	for (Entry<Integer, Boolean> entry : projectQuery.getDeletes().entrySet()) {
	    		if (projectQuery.getDeletes().get(entry.getKey())) {
	    			projectService.deleteProjectNumber(entry.getKey());
	    		}
	    	}
	    	
	    	/* Re-query */
	    	int total = projectService.countProjectMatch(projectQuery);
	    	if (projectQuery.getStart() <= total) {
	    		projectQuery.setStart(projectQuery.getStart() - projectQuery.getMax());
	    	}
	    	List<Project> projects = projectService.searchProject(
	    			projectQuery, projectQuery.getStart(), projectQuery.getMax());
	    	flashAttributes.addFlashAttribute(ModelKeys.PROJECTS, projects);
    		
    		return Urls.REDIRECT_PREFIX + Urls.SEARCH;
    	} catch (BusinessOperationException e) {
    		logger.debug("Error when attempting to consult the Service: " + e.getMessage());
			throw new BusinessOperationException(e.getMessage());
    	} catch (Exception e) {
    		logger.debug("Unexpected error when processing search form submit: " + e.getMessage());
			throw new BusinessOperationException(e.getMessage());
    	}
    }
	
	// ============================================================================================
	// PRIVATE HELPERs
	// ============================================================================================
	
	private List<Project> queryProjects(ProjectQuery projectQuery) throws ServiceOperationException {
		/* Actual querying projects */
    	logger.debug("Query Projects with: " + projectQuery);
    	List<Project> projects = projectService.searchProject(projectQuery, 
    			projectQuery.getStart(), projectQuery.getMax());
    	logger.debug("Totally retrieved projects: " + projects.size());
    	return projects;
	}
}