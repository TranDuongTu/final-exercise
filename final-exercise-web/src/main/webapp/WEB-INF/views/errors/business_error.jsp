<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<div class="error-message well">
	<p><spring:message code="label.errorpage.message" />: ${errorMessage}.</p>
	<p><spring:message code="label.errorpage.advise" /></p>
</div>