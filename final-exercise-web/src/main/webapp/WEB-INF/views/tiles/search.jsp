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
			<b>Search Project</b>
		</legend>

		<!-- Check error and show error message -->
		<spring:hasBindErrors name="projectQuery">
			<c:forEach items="${errors.allErrors}" var="error">
				<c:if test="${error.code eq 'NoCriteriaSet'}">
					<c:set var="errorMessage"
						value="<spring:message code="errorCode"/>" />
				</c:if>
				<div class="alert alert-dismissible alert-danger">
					<button type="button" class="close" data-dismiss="alert">×</button>
					<strong><c:out value="${errorMessage}" /></strong>
				</div>
			</c:forEach>
		</spring:hasBindErrors>

		<!-- Input number -->
		<div class="form-group">
			<label for="inputNumber" class="col-md-2 control-label">Number</label>
			<div class="col-md-10">
				<form:input path="projectNumber" cssClass="form-control"
					id="inputNumber" type="number" placeholder="Project number" />
			</div>
		</div>

		<!-- Input name -->
		<div class="form-group">
			<label for="inputName" class="col-md-2 control-label">Name</label>
			<div class="col-md-10">
				<form:input path="projectName" cssClass="form-control"
					id="inputName" placeholder="Project name" />
			</div>
		</div>

		<!-- Input customer -->
		<div class="form-group">
			<label for="inputCustomer" class="col-md-2 control-label">Customer</label>
			<div class="col-md-10">
				<form:input path="customer" cssClass="form-control"
					id="inputCustomer" placeholder="Customer" />
			</div>
		</div>

		<!-- Input group -->
		<div class="form-group">
			<label for="inputStatus" class="col-md-2 control-label">Status</label>
			<div class="col-md-10">
				<form:select path="projectStatus" cssClass="form-control"
					id="inputStatus">
					<form:option value="-1" disabled="true" selected="true">Please choose</form:option>
					<form:options />
				</form:select>
			</div>
		</div>

		<!-- Button group -->
		<div class="form-group text-center">
			<div class="btn-group">
				<button type="submit" class="btn btn-danger">
					<b>Search</b>
				</button>
				<button type="reset" class="btn btn-default">
					<b>Reset Criteria</b>
				</button>
			</div>
		</div>
	</form:form>

	<!-- Query result -->
	<c:if test="${not empty projects}">
		<div>

			<!-- Top pager and delete button -->
			<div>
				<button type="submit" class="btn btn-default btn-sm col-md-2">Delete</button>
				<ul class="pager col-md-offset-2">
					<li><a href="#">First</a></li>
					<li><a href="#">Previous</a></li>
					<li><a href="#">Next</a></li>
					<li><a href="#">Last</a></li>
				</ul>
			</div>

			<!-- Result table -->
			<table class="table table-striped table-hover">
				<thead>
					<tr>
						<th></th>
						<th>No</th>
						<th>Name</th>
						<th>Status</th>
						<th>Customer</th>
						<th>Start Date</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${projects}" var="project" varStatus="row">
						<tr>
							<td><input type="checkbox" /></td>
							<td><a href="NewEditProject?pid=${project.id}"> </a></td>
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
				<li><a href="#">First</a></li>
				<li><a href="#">Previous</a></li>
				<li><a href="#">Next</a></li>
				<li><a href="#">Last</a></li>
			</ul>
		</div>
	</c:if>
</div>