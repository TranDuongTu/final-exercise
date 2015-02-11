package ch.elca.training.controllers;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import ch.elca.training.constants.ModelKeys;
import ch.elca.training.constants.ViewNames;
import ch.elca.training.exceptions.BusinessOperationException;

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
}
