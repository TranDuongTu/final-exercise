/*
 * SearchProjectsController.java
 *
 * Project: TECHWATCH - TESTING TECHNOLOGIES
 *
 * Copyright 2008 by ELCA Informatique SA
 * Av. de la Harpe 22-24, 1000 Lausanne 13
 * All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of ELCA Informatique SA. ("Confidential Information"). You
 * shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license
 * agreement you entered into with ELCA.
 */

package ch.elca.training.controllers;

import java.util.List;

import javax.validation.Valid;

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
	
	/**
	 * General {@link InitBinder} for {@link CustomEditor}.
	 */
	 @InitBinder
	 public void generalBinding(WebDataBinder binder) {
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
		model.addAttribute(ModelKeys.ERROR_MESSAGE, e.getMessage());
		return ViewNames.ERROR;
	}

	/**
	 * GET request for showing search form.
	 */
	@RequestMapping(method = RequestMethod.GET)
	protected String showSearchForm(Model model) {
		
		/* Adding default search */
		if (!model.containsAttribute(ModelKeys.PROJECT_QUERY)) {
			model.addAttribute(ModelKeys.PROJECT_QUERY, ProjectQuery.defaultCriteria());
		}

		return ViewNames.SEARCH;
	}

	/**
	 * POST request for projects matching search criteria.
	 * Using POST-REDIRECT-GET pattern.
	 */
	@RequestMapping(method = RequestMethod.POST)
    protected String submitSearch(
    		@ModelAttribute(ModelKeys.PROJECT_QUERY) @Valid ProjectQuery projectSearchCriteria, 
    		BindingResult queryBindingResult, 
    		Model model,
    		RedirectAttributes flashAttributes) {
    	
		/* Criteria need to be validated and retained in session */
    	if (queryBindingResult.hasErrors()) {
    		return ViewNames.SEARCH;
    	}
    	model.addAttribute(ModelKeys.PROJECT_QUERY, projectSearchCriteria);
    	
    	/* Actual querying projects */
    	List<Project> projects = null;
    	try {
    		projects = projectService.searchProject(projectSearchCriteria);
    	} catch (ServiceOperationException e) {
    		throw new BusinessOperationException(e.getMessage());
    	}

        /* Flash attributes for redirecting context */
        flashAttributes.addFlashAttribute(ModelKeys.PROJECTS, projects);

        return Urls.REDIRECT_PREFIX + ViewNames.SEARCH;
    }
}