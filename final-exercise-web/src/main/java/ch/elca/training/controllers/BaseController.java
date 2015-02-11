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
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import ch.elca.training.constants.ModelKeys;
import ch.elca.training.constants.ViewNames;
import ch.elca.training.dom.Status;
import ch.elca.training.exceptions.BusinessOperationException;
import ch.elca.training.propertyeditors.StatusEditor;

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
	protected MessageSource messageSource;

	@Override
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}
	
	/**
	 * General {@link InitBinder} for common types.
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
	 * Handle all business level errors.
	 */
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler({BusinessOperationException.class})
	public ModelAndView businessOperationsFailed(Exception e) {
		logger.debug("Handle error in Handler Method: " + e.getMessage());
		
		ModelAndView mav = new ModelAndView();
		mav.addObject(ModelKeys.ERROR_MESSAGE, e.getMessage());
		
		logger.debug("Goto error page: " + ViewNames.ERROR);
		mav.setViewName(ViewNames.ERROR);
		
		return mav;
	}
}
