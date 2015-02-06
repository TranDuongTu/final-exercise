/*
 * EditProjectController.java
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

import ch.elca.training.constants.UrlConstants;
import ch.elca.training.dom.Project;
import ch.elca.training.services.ProjectService;

@Controller
@RequestMapping(UrlConstants.EDIT_PROJECT_URL)
@SessionAttributes(value = {UrlConstants.SESSION_QUERY})
public class EditProjectController {
    
    @Autowired
    private ProjectService projectService;
    
    @InitBinder
    public void initBinderEmployees(WebDataBinder binder) {
    }
    
    @RequestMapping(method = RequestMethod.GET)
    protected String showEditProjectForm(
    		@RequestParam(value = UrlConstants.REQUEST_PARAM_CANCEL, required = false) String isCancel,
    		@RequestParam(UrlConstants.REQUEST_PARAM_PID) long pid, 
    		final RedirectAttributes flashAttributes,
    		Model model) {
        
        return UrlConstants.EDIT_VIEW;
    }
    
    @RequestMapping(method = RequestMethod.POST)
    protected String onSubmitProject(
    		@ModelAttribute(UrlConstants.COMMAND_OBJECT_PROJECT) @Valid Project command,
    		BindingResult projectBindingResult, 
    		final RedirectAttributes flashAttributes,
    		Model model) {
    	return null;
    }
}