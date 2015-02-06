/*
 * UrlConstants.java
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

package ch.elca.training.constants;

public interface UrlConstants {
	
    public static final String REDIRECT_PREFIX = "redirect:";
    
    public static final String HOME_URL = "/home";
    public static final String SEARCH_PROJECTS_URL = "/search";
    public static final String EDIT_PROJECT_URL = "/edit";
    
    public static final String HOME_VIEW = "home";
    public static final String SEARCH_VIEW = "search";
    public static final String EDIT_VIEW = "edit";
    public static final String ERROR_VIEW = "error";
    
    public static final String COMMAND_OBJECT_QUERY = "query";
    public static final String COMMAND_OBJECT_PROJECT = "project";
    
    public static final String SESSION_QUERY = "query";
    
    public static final String FLASH_PROJECTS = "projects";
    
    public static final String MODEL_PROJECT = "project";
    public static final String MODEL_EMPLOYEES = "allEmployees";
    public static final String MODEL_GROUPS = "allGroups";
    
    public static final String REQUEST_PARAM_CANCEL = "_cancel";
    public static final String REQUEST_PARAM_PID = "pid";
}
