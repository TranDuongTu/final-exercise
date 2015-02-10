package ch.elca.training.controllers;

import java.util.List;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ch.elca.training.constants.ModelKeys;
import ch.elca.training.constants.Urls;
import ch.elca.training.constants.ViewNames;
import ch.elca.training.dom.Project;
import ch.elca.training.dom.Status;
import ch.elca.training.exceptions.BusinessOperationException;
import ch.elca.training.propertyeditors.StatusEditor;
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
public class SearchProjectsController {

	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private ProjectQueryValidator projectQueryValidator;
	
	private Logger logger = Logger.getLogger(getClass());
	
	/**
	 * General {@link InitBinder} for {@link CustomEditor}.
	 */
	 @InitBinder
	 public void generalBinding(WebDataBinder binder) {
		 /* For converting between Status objects */
		 binder.registerCustomEditor(Status.class, new StatusEditor());
	 }
	
	/**
	 * {@link InitBinder} for {@link ProjectQuery}.
	 */
	@InitBinder(ModelKeys.PROJECT_QUERY)
	public void projectQueryBinding(WebDataBinder binder) {
		binder.setValidator(projectQueryValidator);
	}
	
	/**
	 * Handle all business level errors.
	 */
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler({BusinessOperationException.class})
	public String businessOperationsFailed(Model model, Exception e) {
		logger.debug("Handle error: " + e.getMessage());
		model.addAttribute(ModelKeys.ERROR_MESSAGE, e.getMessage());
		logger.debug("Error page: " + ViewNames.ERROR);
		return ViewNames.ERROR;
	}

	/**
	 * GET request for showing search form.
	 */
	@RequestMapping(method = RequestMethod.GET)
	protected String showSearchForm(Model model) {
		try {
			logger.debug("Going to serve GET request for search form");
			
			/* Adding default search */
			if (!model.containsAttribute(ModelKeys.PROJECT_QUERY)) {
				model.addAttribute(ModelKeys.PROJECT_QUERY, ProjectQuery.defaultCriteria());
			}
			
			logger.debug("View name: " + ViewNames.SEARCH);
			return ViewNames.SEARCH;
		} catch (Exception e) {
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
			logger.debug("Going to serve POST request when submitting search");
			
			/* Criteria need to be validated and retained in session */
	    	if (queryBindingResult.hasErrors()) {
	    		logger.debug("Binding errors for query binding: " + projectQuery);
	    		logger.debug("View name: " + ViewNames.SEARCH);
	    		return ViewNames.SEARCH;
	    	}
	    	
	    	/* Actual querying projects */
	    	logger.debug(String.format("Query Projects from %d, max %d", 
	    			projectQuery.getStart(), projectQuery.getMax()));
	    	List<Project> projects = projectService.searchProject(projectQuery, 
	    			projectQuery.getStart(), projectQuery.getMax());
	    	logger.debug("Totally retrieved projects: " + projects.size());
	    	
	    	/* Update Project query for the paging information */
	    	projectQuery.setTotal(projectService.countProjectMatch(projectQuery));
	    	model.addAttribute(ModelKeys.PROJECT_QUERY, projectQuery);
	
	        /* Flash attributes for redirecting context */
	    	flashAttributes.addFlashAttribute(ModelKeys.NOT_FOUND, projects.size() == 0);
	        flashAttributes.addFlashAttribute(ModelKeys.PROJECTS, projects);
	
	        logger.debug("Goto: " + Urls.REDIRECT_PREFIX + ViewNames.SEARCH);
	        return Urls.REDIRECT_PREFIX + ViewNames.SEARCH;
		} catch (ServiceOperationException e) {
			logger.debug("Error when attempting to consult the Service: " + e.getMessage());
			throw new BusinessOperationException(e.getMessage());
		} catch (Exception e) {
			logger.debug("Unexpected error when processing search form submit: " + e.getMessage());
			throw new BusinessOperationException(e.getMessage());
		}
    }
}