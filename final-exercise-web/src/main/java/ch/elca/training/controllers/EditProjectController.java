package ch.elca.training.controllers;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ch.elca.training.constants.ModelKeys;
import ch.elca.training.constants.Urls;
import ch.elca.training.constants.ViewNames;
import ch.elca.training.dom.Project;
import ch.elca.training.exceptions.BusinessOperationException;
import ch.elca.training.services.ProjectService;
import ch.elca.training.services.exceptions.ServiceOperationException;
import ch.elca.training.services.exceptions.ServiceProjectNotExistsException;
import ch.elca.training.services.searching.ProjectQuery;
import ch.elca.training.validators.ProjectValidator;

/**
 * Handle Edit project request.
 * 
 * @author DTR
 */
@Controller
@RequestMapping(Urls.EDIT)
@SessionAttributes(value = {ModelKeys.PROJECT_QUERY})
public class EditProjectController extends BaseController {
	
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private ProjectValidator projectValidator;
	
	/**
	 * Request parameters.
	 */
	
	/* Project number to be edited */
	private static final String PNUM_PARAM = "pnumber";
	
	/* For canceling the edit page. */
	private static final String CANCEL_PARAM = "_cancel";
	
	/**
	 * Binding for {@link Project}.
	 */
	@InitBinder(ModelKeys.PROJECT)
	public void projectBinding(WebDataBinder binder, Locale locale) {
		binder.setValidator(projectValidator);
		
		// CustomDateEditor for converting date string
		String formatString = messageSource.getMessage(
				FORMAT_DATE_KEY, null, DEFAULT_DATE_FORMAT, locale);
    	SimpleDateFormat dateFormat = new SimpleDateFormat(formatString);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(
                dateFormat, false));
	}
	
	/**
	 * Edit form for user to input modified informations.
	 */
	@RequestMapping(method = RequestMethod.GET)
    protected String showEditProjectForm(
    		@RequestParam(value = CANCEL_PARAM, required = false) String isCancel,
    		@RequestParam(PNUM_PARAM) int pNumber,
    		final RedirectAttributes flashAttributes,
    		@ModelAttribute(ModelKeys.PROJECT_QUERY) ProjectQuery query,
    		Model model) {
    	
    	try {
    		logger.info("Going to serve GET request on EDIT page");
    		
    		/* User click cancel */
    		if (isCancel != null) {
    			logger.debug("User cancel editing project");
        		logger.info("Redirect to: " + Urls.SEARCH);
        		return Urls.REDIRECT_PREFIX + Urls.SEARCH;
        	}
    		
    		/* Query Project based on its number */
    		Project project;
    		try {
    			logger.debug("Query details of the Project with number: " + pNumber);
    			project = projectService.getProjectByNumber(pNumber);
    			logger.debug("Project found");
    		} catch (ServiceProjectNotExistsException e) {
    			logger.debug("Cannot retrieve Project");
    			project = null;
    		}
    		model.addAttribute(ModelKeys.PROJECT, project);
            
    		logger.info("Return view name: " + ViewNames.EDIT);
            return ViewNames.EDIT;
    	} catch (ServiceOperationException e) {
    		logger.debug("Unexpected thing occurred: " + e.getMessage());
    		throw new BusinessOperationException(e.getMessage());
    	}
    }
    
	/**
	 * Submit modified (or new) {@link Project}.
	 */
    @RequestMapping(method = RequestMethod.POST)
    protected String onSubmitProject(
    		@ModelAttribute(ModelKeys.PROJECT) @Valid Project project,
    		BindingResult projectBindingResult, 
    		final RedirectAttributes flashAttributes,
    		Model model) {
    	
		try {
			logger.info("Trying to serve POST request on EDIT page");
			
			if (projectBindingResult.hasErrors()) {
				logger.debug("Biding error for Project: " + project);
				logger.info("Returning View: " + ViewNames.EDIT);
	    		return ViewNames.EDIT;
	    	}
			
			/* Persist and check if it success or not */
			try {
				logger.debug("Persist modified Project: " + project);
				projectService.saveOrUpdateProject(project);
				
				logger.debug("Add success info for search page to display");
				flashAttributes.addFlashAttribute(ModelKeys.IS_SUCCESS, true);
			} catch (ServiceProjectNotExistsException e) {
				logger.debug("Add failure info to search page to show");
				flashAttributes.addFlashAttribute(ModelKeys.IS_SUCCESS, false);
			}
			
			logger.info("Redirect to: " + Urls.SEARCH + Urls.SEARCH_BACK);
			return Urls.REDIRECT_PREFIX + Urls.SEARCH + Urls.SEARCH_BACK;
			
		} catch (ServiceOperationException e) {
			logger.debug("Some thing unexpected: " + e.getMessage());
			throw new BusinessOperationException(e.getMessage());
		}
    }
}
