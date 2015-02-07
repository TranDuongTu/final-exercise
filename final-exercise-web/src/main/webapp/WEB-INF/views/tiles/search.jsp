<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="search-panel">
	<h1>{pageName}</h1>
	<div class="search-criteria">
		<!-- Input criteria form -->
		<form:form method="POST" action="${pageContext.request.contextPath}/search" commandName="projectSearchCriteria">
			<!-- Criteria Inputs -->
			<div class="row">
				<!-- Left half -->
				<div class="col-sm-6">
					<!-- Number -->
					<div class="row">
						<div class="col-sm-6">
							<spring:message code="number_label" />
						</div>
						<div class="col-sm-6">
							<form:input path="projectNumber" />
						</div>
					</div>
					
					<!-- Name -->
					<div class="row">
						<div class="col-sm-6">
							<spring:message code="name_label" />
						</div>
						<div class="col-sm-6">
							<form:input path="projectName" />
						</div>
					</div>
				</div>
				
				<!-- Eight half -->
				<div class="col-sm-6">
					<!-- Customer -->
					<div class="row">
						<div class="col-sm-6">
							<spring:message code="customer_label" />
						</div>
						<div class="col-sm-6">
							<form:input path="customer" />
						</div>
					</div>
					
					<!-- Status -->
					<div class="row">
						<div class="col-sm-6">
							<spring:message code="status_label" />
						</div>
						<div class="col-sm-6">
							<form:input path="projectStatus" />
						</div>
					</div>
				</div>
			</div>
			
			<!-- Buttons -->
			<div class="row">
				<div class="col-sm-6">
					<form:button><spring:message code="search_btn_label"/></form:button>
				</div>
				<div class="col-sm-6">
					<form:button><spring:message code="reset_btn_label"/></form:button>
				</div>
			</div>
		</form:form>
	</div>

	<div class="search-result"></div>
</div>