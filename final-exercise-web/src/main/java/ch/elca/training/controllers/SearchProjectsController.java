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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ch.elca.training.constants.UrlConstants;
import ch.elca.training.services.ProjectService;

@Controller
@RequestMapping(UrlConstants.SEARCH_PROJECTS_URL)
public class SearchProjectsController {

	@Autowired
	private ProjectService projectService;

	@RequestMapping(method = RequestMethod.GET)
	protected String showForm(Model model) {
		if (!model.containsAttribute(UrlConstants.COMMAND_OBJECT_QUERY)) {
		}

		return UrlConstants.SEARCH_VIEW;
	}

	@RequestMapping(method = RequestMethod.POST)
	protected String onSubmit(BindingResult queryBindingResult,
			final RedirectAttributes flashAttributes, Model model) {

		return UrlConstants.REDIRECT_PREFIX + UrlConstants.SEARCH_PROJECTS_URL;
	}
}