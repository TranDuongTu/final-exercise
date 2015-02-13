package ch.elca.training.controllers;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import ch.elca.training.constants.ModelKeys;
import ch.elca.training.constants.ViewNames;
import ch.elca.training.dom.Status;
import ch.elca.training.propertyeditors.StatusEditor;
import ch.elca.training.services.ProjectService;

/**
 * Based controller for PIM application.
 * 
 * @author DTR
 */
public abstract class BaseController implements MessageSourceAware {
	
	protected static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
	protected static final String FORMAT_DATE_KEY = "format.date";
	
	protected Logger logger = Logger.getLogger(getClass());
	
	@Autowired
	protected ProjectService projectService;
	
	/**
	 * Message source for supporting I18N.
	 */
	@Autowired
	protected MessageSource messageSource;

	@Override
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}
	
	/**
	 * General Binding for common types.
	 */
	 @InitBinder
	 public void generalBinding(WebDataBinder binder, Locale locale) {
		 /* For converting between Status objects */
		 binder.registerCustomEditor(Status.class, new StatusEditor());
		 
		// CustomDateEditor for converting date string
		String formatString = messageSource.getMessage(
				FORMAT_DATE_KEY, null, DEFAULT_DATE_FORMAT, locale);
    	SimpleDateFormat dateFormat = new SimpleDateFormat(formatString);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(
                dateFormat, false));
	 }
	
	/**
	 * Handler for all business level errors.
	 */
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler({Exception.class})
	public ModelAndView businessOperationsFailed(Exception e) {
		logger.debug("Handle error from our own server: " + e);
		return handleBusinessError(e);
	}
	
	// ==================================================================================
	// PROTECTED HELPERS
	// ==================================================================================
	
	/**
	 * Handle general business error helper.
	 */
	private ModelAndView handleBusinessError(Exception e) {
		ModelAndView mav = new ModelAndView();
		mav.addObject(ModelKeys.ERROR_MESSAGE, e.getMessage());
		
		logger.debug("Goto error page: " + ViewNames.ERROR);
		mav.setViewName(ViewNames.ERROR);
		
		return mav;
	}
	
	/**
	 * Indicate view to show.
	 */
	protected String showPage(String viewName, Model model) {
		logger.info("Resolve view: " + viewName);
		
		if (viewName == ViewNames.SEARCH) {
			model.addAttribute(ModelKeys.PAGE, "search");
		} else if (viewName == ViewNames.EDIT) {
			model.addAttribute(ModelKeys.PAGE, "edit");
		} else {
			throw new IllegalArgumentException("Not currently supported page");
		}
		
		return viewName;
	}
	
	/**
	 * Return view name that should be rendered if errors present (null if not).
	 */
	protected String returnForBindingErrors(String viewName, Object commandObject, 
			BindingResult queryBindingResult, Model model) {
		logger.debug("Binding errors for query binding: " + commandObject);
		logger.info("Resolve for view name: " + viewName);
		
		if (viewName == ViewNames.SEARCH) {
			model.addAttribute(ModelKeys.PAGE, "search");
		} else if (viewName == ViewNames.EDIT) {
			model.addAttribute(ModelKeys.PAGE, "edit");
		} else {
			throw new IllegalArgumentException("Currently not supported page");
		}
		return viewName;
	}
}
