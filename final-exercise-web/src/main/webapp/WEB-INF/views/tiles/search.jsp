<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="search-panel">
	<!-- Form -->
	<form:form role="form" class="form-horizontal" method="POST"
		action="${pageContext.request.contextPath}/search"
		commandName="projectQuery">

		<!-- Form title -->
		<legend>
			<b><spring:message code="title.searchpage" /></b>
		</legend>

		<!-- Check error and show error message -->
		<div class="errors-panel">
			<spring:hasBindErrors name="projectQuery">
				<c:forEach items="${errors.allErrors}" var="error">
					<c:if test="${error.code eq 'QueryNoCriteriaSet'}">
						<c:set var="errorMessage" value="<spring:message code='error.querynocriteriaset'/>" />
					</c:if>
					<c:if test="${error.code eq 'QueryNumberNull'}">
						<c:set var="errorMessage" value="<spring:message code='error.querynumbernull'/>" />
					</c:if>
					<c:if test="${error.code eq 'QueryNumberNegative'}">
						<c:set var="errorMessage" value="<spring:message code='error.querynumbernegative'/>" />
					</c:if>
					<c:if test="${error.code eq 'QueryNameNull'}">
						<c:set var="errorMessage" value="<spring:message code='error.querynamenull'/>" />
					</c:if>
					<c:if test="${error.code eq 'QueryNameLengthExceed'}">
						<c:set var="errorMessage" value="<spring:message code='error.querynamelengthexceed'/>" />
					</c:if>
					<div class="alert alert-dismissible alert-danger">
						<button type="button" class="close" data-dismiss="alert">×</button>
						<strong><c:out value="${errorMessage}" /></strong>
					</div>
				</c:forEach>
			</spring:hasBindErrors>
		</div>

		<div class="row">
			<!-- Input number -->
			<div class="form-group col-sm-6">
				<label for="inputNumber" class="col-md-4 control-label"> 
					<spring:message code="label.searchpage.number" />
				</label>
				<div class="col-md-8">
					<form:input path="projectNumber" cssClass="form-control"
						id="inputNumber" type="number" placeholder="Project number" />
				</div>
			</div>

			<!-- Input name -->
			<div class="form-group col-sm-6">
				<label for="inputName" class="col-md-4 control-label"> 
					<spring:message code="label.searchpage.name" />
				</label>
				<div class="col-md-8">
					<form:input path="projectName" cssClass="form-control"
						id="inputName" placeholder="Project name" />
				</div>
			</div>
		</div>

		<div class="row">
			<!-- Input customer -->
			<div class="form-group col-sm-6">
				<label for="inputCustomer" class="col-md-4 control-label"> <spring:message
						code="label.searchpage.customer" />
				</label>
				<div class="col-md-8">
					<form:input path="customer" cssClass="form-control"
						id="inputCustomer" placeholder="Customer" />
				</div>
			</div>

			<!-- Input status -->
			<div class="form-group col-sm-6">
				<label for="inputStatus" class="col-md-4 control-label"> <spring:message
						code="label.searchpage.status" />
				</label>
				<div class="col-md-8">
					<form:select path="projectStatus" cssClass="form-control"
						id="inputStatus">
						<form:option value="" selected="selected">Please choose</form:option>
						<form:options />
					</form:select>
				</div>
			</div>
		</div>

		<!-- Button group -->
		<div class="form-group text-center">
			<div class="btn-group">
				<button type="submit" class="btn btn-danger">
					<b><spring:message code="button.searchpage.search" /></b>
				</button>
				<button type="reset" class="btn btn-default">
					<b><spring:message code="button.searchpage.reset" /></b>
				</button>
			</div>
		</div>
	</form:form>

	<!-- Query result -->
	<c:if test="${not empty projects}">
		<div class="query-result">
			<br />
			<!-- Top pager and delete button -->
			<div>
				<button type="submit" class="btn btn-default btn-sm col-md-2">Delete</button>
				<ul class="pager col-md-offset-2">
					<li><a href="#"><spring:message code="button.searchpage.first" /></a></li>
					<li><a href="#"><spring:message code="button.searchpage.previous" /></a></li>
					<li><a href="#"><spring:message code="button.searchpage.next" /></a></li>
					<li><a href="#"><spring:message code="button.searchpage.last" /></a></li>
				</ul>
			</div>

			<!-- Result table -->
			<table class="table table-striped table-hover">
				<thead>
					<tr>
						<th></th>
						<th><spring:message code="label.searchpage.table.no" /></th>
						<th><spring:message code="label.searchpage.table.name" /></th>
						<th><spring:message code="label.searchpage.table.status" /></th>
						<th><spring:message code="label.searchpage.table.customer" /></th>
						<th><spring:message code="label.searchpage.table.startdate" /></th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${projects}" var="project" varStatus="row">
						<tr>
							<td><input type="checkbox" /></td>
							<td><a href="${pageContext.request.contextPath}/edit?pnumber=${project.number}">
								<c:out value="${project.number}" />
							</a></td>
							<td><c:out value="${project.name}" /></td>
							<td><c:out value="${project.status}" /></td>
							<td><c:out value="${project.customer}" /></td>
							<td><c:out value="${project.startDate}" /></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>

			<!-- Bottom pager -->
			<ul class="pager">
				<li><a href="#"><spring:message code="button.searchpage.first" /></a></li>
				<li><a href="#"><spring:message code="button.searchpage.previous" /></a></li>
				<li><a href="#"><spring:message code="button.searchpage.next" /></a></li>
				<li><a href="#"><spring:message code="button.searchpage.last" /></a></li>
			</ul>
		</div>
	</c:if>
</div>