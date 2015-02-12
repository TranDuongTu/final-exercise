package ch.elca.training.controllers;

import java.util.Date;
import java.util.Locale;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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
import ch.elca.training.services.exceptions.ServiceOperationException;
import ch.elca.training.services.exceptions.ServiceProjectNotExistsException;
import ch.elca.training.validators.ProjectValidator;

/**
 * Handle Edit (or New) project requests.
 * 
 * @author DTR
 */
@Controller
@RequestMapping(Urls.EDIT)
@SessionAttributes(value = {ModelKeys.PROJECT_QUERY})
public class EditProjectController extends BaseController {
	
	@Autowired
	private ProjectValidator projectValidator;
	
	/*****************
	 * URL parameters.
	 *****************/
	
	/* Project number to be edited */
	private static final String PNUM_PARAM = "pnumber";
	
	/* For canceling the edit page. */
	private static final String CANCEL_PARAM = "_cancel";
	
	/******************************
	 * Binding for {@link Project}.
	 ******************************/
	@InitBinder(ModelKeys.PROJECT)
	public void projectBinding(WebDataBinder binder, Locale locale) {
		binder.setValidator(projectValidator);
	}
	
	/*******************************************************************************
	 * Showing EDIT form for user to modified Project.
	 *******************************************************************************/
	@RequestMapping(method = RequestMethod.GET)
    protected String showEditProjectForm(Model model,
    		@RequestParam(value = CANCEL_PARAM, required = false) String isCancel,
    		@RequestParam(PNUM_PARAM) int pNumber) {
    	
    	try {
    		logger.info("Going to serve GET request on EDIT page");
    		
    		if (isCancel != null) return cancelEditAndReturn();
    		
    		Project project = getProjectToBeEdit(pNumber);
    		model.addAttribute(ModelKeys.PROJECT, project);

            return showPage(ViewNames.EDIT, model);
    	} catch (ServiceOperationException e) {
    		logger.debug("Unexpected thing occurred: " + e.getMessage());
    		throw new BusinessOperationException(e.getMessage());
    	}
    }
    
	/*******************************************************************************
	 * Submit modified (or new) {@link Project}.
	 *******************************************************************************/
    @RequestMapping(method = RequestMethod.POST)
    protected String onSubmitProject(Model model,
    		@ModelAttribute(ModelKeys.PROJECT) @Valid Project project,
    		BindingResult projectBindingResult, 
    		final RedirectAttributes redirectAttributes) {
    	
		try {
			logger.info("Trying to serve POST request on EDIT page");
			
			if (projectBindingResult.hasErrors()) {
				return returnForBindingErrors(ViewNames.EDIT, project, projectBindingResult, model);
			}
			
			return updateProjectAndRedirect(redirectAttributes, project);
		} catch (ServiceOperationException e) {
			logger.debug("Some thing unexpected: " + e.getMessage());
			throw new BusinessOperationException(e.getMessage());
		}
    }
    
    // ============================================================================================
    // PRIVATE HELPERS
    // ============================================================================================
    
    /**
     * When user cancel editing.
     */
    private String cancelEditAndReturn() {
    	logger.debug("User cancel editing project");
		logger.info("Redirect to: " + Urls.SEARCH);
		return Urls.REDIRECT_PREFIX + Urls.SEARCH;
    }
    
    /**
     * Query Project to be modified.
     */
    private Project getProjectToBeEdit(int pNumber) throws ServiceOperationException {
		Project project;
		try {
			logger.debug("Query details of the Project with number: " + pNumber);
			project = projectService.getProjectByNumber(pNumber);
			logger.debug("Project found. Entering Edit Project page");
		} catch (ServiceProjectNotExistsException e) {
			logger.debug("project not found. Entering New Project page");
			project = new Project();
			project.setStartDate(new Date());
			project.setEndDate(new Date());
		}
		return project;
    }
    
    /**
     * Update Project and redirect.
     */
    private String updateProjectAndRedirect(RedirectAttributes redirectAttributes, Project project) 
    		throws ServiceOperationException {
		try {
			logger.debug("Persist modified Project: " + project);
			projectService.saveOrUpdateProject(project);
			
			logger.debug("Successful! Add success info for search page to display");
			redirectAttributes.addFlashAttribute(ModelKeys.IS_SUCCESS, true);
		} catch (ServiceProjectNotExistsException e) {
			logger.debug("Unsuccessful! Add failure info to search page to show");
			redirectAttributes.addFlashAttribute(ModelKeys.IS_SUCCESS, false);
		}
		
		logger.info("Redirect to: " + Urls.SEARCH + Urls.SEARCH_BACK);
		return Urls.REDIRECT_PREFIX + Urls.SEARCH + Urls.SEARCH_BACK;
    }
}
