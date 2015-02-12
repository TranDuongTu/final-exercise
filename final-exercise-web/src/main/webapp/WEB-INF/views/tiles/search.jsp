<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="search-panel">
	<!-- Form -->
	<form:form id="searchForm" role="form" class="form-horizontal" method="POST"
		action="${pageContext.request.contextPath}/search"
		commandName="projectQuery">

		<!-- Form title -->
		<legend>
			<b><spring:message code="title.searchpage" /></b>
		</legend>
		
		<!-- Message back from EDIT page -->
		<c:if test="${not empty isSuccess}">
			<div>
				<c:choose>
					<c:when test="${isSuccess}">
						<div class="alert alert-success text-center">
							<strong>Successfully update Project</strong>
						</div>
					</c:when>
					<c:otherwise>
						<div class="alert alert-warning text-center">
							<strong>Unsuccessfully update Project</strong>
						</div>
					</c:otherwise>
				</c:choose>
			</div>
		</c:if>

		<!-- Check error and show error message -->
		<div class="errors-panel">
			<spring:hasBindErrors name="projectQuery">
				<c:forEach items="${errors.allErrors}" var="error">
					<spring:message code="${error.code}" var="errorMessage" />
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
				<button type="submit" class="btn btn-danger" onclick="toFirst()">
					<span class="glyphicon glyphicon-search" aria-hidden="true"></span> 
					<b><spring:message code="button.searchpage.search" /></b>
				</button>
				<button type="reset" class="btn btn-default">
					<b><spring:message code="button.searchpage.reset" /></b> 
					<span class="glyphicon glyphicon-unchecked" aria-hidden="true"></span>
				</button>
			</div>
		</div>
	

		<!-- Query result -->
		<c:if test="${notFound}">
			<div class="well well-lg text-center">
				<strong><spring:message code="label.searchpage.search.notfound" /></strong>
			</div>
		</c:if>
		<c:if test="${not empty projects}">
			<div class="query-result">
				<br />
				<!-- Format string -->
				<spring:message code="format.date" var="dateFormat" />
				
				<!-- Top pager and delete button -->
				<div>
					<button type="submit" formaction="${pageContext.request.contextPath}/search/delete" class="btn btn-default btn-sm col-md-2">
						<span class="glyphicon glyphicon-trash" aria-hidden="true"></span> Delete
					</button>
					
					<!-- Bottom pager -->
					<div class="pager-bottom pull-right">
						<button type="submit" class="btn" onclick="toFirst()">
							<span class="glyphicon glyphicon-fast-backward" aria-hidden="true"></span> 
							<b><spring:message code="button.searchpage.first" /></b>
						</button>
						<button type="submit" class="btn" onclick="previous()">
							<span class="glyphicon glyphicon-step-backward" aria-hidden="true"></span> 
							<b><spring:message code="button.searchpage.previous" /></b>
						</button>
						<button type="submit" class="btn" onclick="next()">
							<b><spring:message code="button.searchpage.next" /></b> 
							<span class="glyphicon glyphicon-step-forward" aria-hidden="true"></span>
						</button>
						<button type="submit" class="btn" onclick="toLast()">
							<b><spring:message code="button.searchpage.last" /></b> 
							<span class="glyphicon glyphicon-fast-forward" aria-hidden="true"></span>
						</button>
					</div>
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
							<!-- For formating date -->
							<fmt:formatDate value="${project.startDate}" pattern="${dateFormat}" var="formattedStartDate" />
							
							<!-- Row data -->
							<tr>
								<td><form:checkbox path="deletes[${project.number}]" /></td>
								<td><a href="${pageContext.request.contextPath}/edit?pnumber=${project.number}">
									<c:out value="${project.number}" />
								</a></td>
								<td><c:out value="${project.name}" /></td>
								<td><c:out value="${project.status}" /></td>
								<td><c:out value="${project.customer}" /></td>
								<td><c:out value="${formattedStartDate}" /></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
	
				<!-- Bottom pager -->
				<div class="pager-bottom pull-right">
					<button type="submit" class="btn" onclick="toFirst()">
						<span class="glyphicon glyphicon-fast-backward" aria-hidden="true"></span> 
						<b><spring:message code="button.searchpage.first" /></b>
					</button>
					<button type="submit" class="btn" onclick="previous()">
						<span class="glyphicon glyphicon-step-backward" aria-hidden="true"></span> 
						<b><spring:message code="button.searchpage.previous" /></b>
					</button>
					<button type="submit" class="btn" onclick="next()">
						<b><spring:message code="button.searchpage.next" /></b> 
						<span class="glyphicon glyphicon-step-forward" aria-hidden="true"></span>
					</button>
					<button type="submit" class="btn" onclick="toLast()">
						<b><spring:message code="button.searchpage.last" /></b> 
						<span class="glyphicon glyphicon-fast-forward" aria-hidden="true"></span>
					</button>
				</div>
			</div>
		</c:if>
		
		<form:hidden id="totalField" path="total" />
		<form:hidden id="startField" path="start" />
		<form:hidden id="maxField" path="max" />
	</form:form>
	
	<!-- JS -->
	<script type="text/javascript">
		function toFirst() {
			document.getElementById('startField').value = 0;
		}
		
		function toLast() {
			var total = parseInt(document.getElementById('totalField').value);
			var max = parseInt(document.getElementById('maxField').value);
			document.getElementById('startField').value = total - (total % max);
		}
		
		function previous() {
			var currentStart = parseInt(document.getElementById('startField').value);
			var max = parseInt(document.getElementById('maxField').value);
			if (currentStart - max >= 0) {
				document.getElementById('startField').value = currentStart - max;
			}
		}
		
		function next() {
			var total = parseInt(document.getElementById('totalField').value);
			var max = parseInt(document.getElementById('maxField').value);
			var currentStart = parseInt(document.getElementById('startField').value);
			if (currentStart + max < total) {
				document.getElementById('startField').value = currentStart + max;
			}
		}
	</script>
</div>