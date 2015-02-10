package ch.elca.training.controllers;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
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
import ch.elca.training.validators.ProjectValidator;

/**
 * Handle Edit project request.
 * 
 * @author DTR
 */
@Controller
@RequestMapping(Urls.EDIT)
@SessionAttributes(value = {ModelKeys.PROJECT_QUERY})
public class EditProjectController {
	
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private ProjectValidator projectValidator;
	
	/**
	 * Request parameters.
	 */
	private static final String CANCEL_PARAM = "_cancel";
	private static final String PID_PARAM = "pnumber";
	
	/**
	 * Binding for {@link Project}.
	 */
	@InitBinder(ModelKeys.PROJECT)
	public void projectBinding(WebDataBinder binder) {
		binder.setValidator(projectValidator);
		
		// CustomDateEditor for converting date string
    	SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(
                dateFormat, false));
	}
	
	/**
	 * Handle all business level errors.
	 */
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler({BusinessOperationException.class})
	public String businessOperationsFailed(Model model, Exception e) {
		model.addAttribute(ModelKeys.ERROR_MESSAGE, e.getMessage());
		return ViewNames.ERROR;
	}
	
	/**
	 * Edit form for user input modified information.
	 */
	@RequestMapping(method = RequestMethod.GET)
    protected String showEditProjectForm(
    		@RequestParam(value = CANCEL_PARAM, required = false) String isCancel,
    		@RequestParam(PID_PARAM) int pNumber,
    		final RedirectAttributes flashAttributes,
    		@ModelAttribute(ModelKeys.PROJECT_QUERY) ProjectQuery query,
    		Model model) {
    	
    	Project project = null;
    	try {
    		if (isCancel != null) {
        		redirectProjects(query, flashAttributes);
        		
        		return Urls.REDIRECT_PREFIX + ViewNames.SEARCH;
        	}
    		
    		project = projectService.getProjectByNumber(pNumber);
    	} catch (ServiceOperationException e) {
    		throw new BusinessOperationException(e.getMessage());
    	}
        
        // Model attributes for rendering this view
        model.addAttribute(ModelKeys.PROJECT, project);
        
        return ViewNames.EDIT;
    }
    
	/**
	 * Submit modified (or new) {@link Project}.
	 */
    @RequestMapping(method = RequestMethod.POST)
    protected String onSubmitProject(
    		@ModelAttribute(ModelKeys.PROJECT) @Valid Project command,
    		BindingResult projectBindingResult, 
    		@ModelAttribute(ModelKeys.PROJECT_QUERY) ProjectQuery query,
    		final RedirectAttributes flashAttributes,
    		Model model) {
    	
    	if (projectBindingResult.hasErrors()) {
    		return ViewNames.EDIT;
    	} else {
    		try {
    			projectService.saveOrUpdateProject(command);
    			redirectProjects(query, flashAttributes);
    		} catch (ServiceOperationException e) {
    			throw new BusinessOperationException(e.getMessage());
    		}
    		
    		return Urls.REDIRECT_PREFIX + ViewNames.SEARCH;
    	}
    }
    
    // ========================================================================
    // PRIVATE HELPERS
    // ========================================================================
    
    private void redirectProjects(ProjectQuery query, final RedirectAttributes flashAttributes) 
    		throws ServiceOperationException {
    	int count = projectService.countProjects();
		List<Project> projects = projectService.searchProject(query, 0, count);
		flashAttributes.addFlashAttribute(ModelKeys.PROJECTS, projects);
    }
}
