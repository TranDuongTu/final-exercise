<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="edit-panel">
	<!-- Edit Form -->
	<form:form role="form" class="form-horizontal" method="POST" 
		action="${pageContext.request.contextPath}/edit"
		commandName="project">

		<!-- Choosing form legend -->
		<legend>
			<c:choose>
				<c:when test="${project.id > 0}">
					<b>Edit Project</b>
				</c:when>
				<c:otherwise>
					<b>New Project</b>
				</c:otherwise>
			</c:choose>
		</legend>

		<!-- Checking for errors -->
		<div class="errors-panel">
			<spring:hasBindErrors name="project">
				<c:forEach items="${errors.allErrors}" var="error">
					<spring:message code="${error.code}" var="errorMessage" />
					<div class="alert alert-dismissible alert-danger">
						<button type="button" class="close" data-dismiss="alert">×</button>
						<strong><c:out value="${errorMessage}" /></strong>
					</div>
				</c:forEach>
			</spring:hasBindErrors>
		</div>

		<!-- Project ID. -->
		<form:hidden path="id" />
		
		<!-- Project number -->
		<div class="form-group">
			<label for="inputNumber" class="col-md-2 control-label">
				<spring:message code="label.editpage.number" />
			</label>
			<div class="col-md-10">
				<c:choose>
					<c:when test="${project.id > 0}">
						<form:hidden path="number" id="pnumber" />
						<form:input path="number" cssClass="form-control" id="inputNumber"
							type="number" placeholder="Project number" disabled="true" />
					</c:when>
					<c:otherwise>
						<form:input path="number" cssClass="form-control" id="inputNumber"
							type="number" placeholder="Project number" />
					</c:otherwise>
				</c:choose>
			</div>
		</div>

		<!-- Input name -->
		<div class="form-group">
			<label for="inputName" class="col-md-2 control-label">
				<spring:message code="label.editpage.name" />
			</label>
			<div class="col-md-10">
				<form:input path="name" cssClass="form-control" id="inputName"
					placeholder="Project name" />
			</div>
		</div>

		<!-- Input customer -->
		<div class="form-group">
			<label for="inputCustomer" class="col-md-2 control-label">
				<spring:message code="label.editpage.customer" />
			</label>
			<div class="col-md-10">
				<form:input path="customer" cssClass="form-control"
					id="inputCustomer" placeholder="Customer" />
			</div>
		</div>

		<!-- Input status -->
		<div class="form-group">
			<label for="inputStatus" class="col-md-2 control-label">
				<spring:message code="label.editpage.status" />
			</label>
			<div class="col-md-10">
				<form:select path="status" cssClass="form-control" id="inputStatus">
					<form:option value="" disabled="true" selected="selected">
						<spring:message code="label.editpage.default_status" />
					</form:option>
					<form:options />
				</form:select>
			</div>
		</div>
		
		<!-- Input start date -->
		<div class="form-group">
			<label for="inputStartDate" class="col-md-2 control-label">
				<spring:message code="label.editpage.startdate" />
			</label>
			<div class="col-md-10">
				<form:input path="startDate" cssClass="form-control"
					id="inputStartDate" placeholder="Start Date" readonly="true" />
			</div>
		</div>

		<!-- Input end date -->
		<div class="form-group">
			<label for="inputEndDate" class="col-md-2 control-label">
				<spring:message code="label.editpage.enddate" />
			</label>
			<div class="col-md-10">
				<form:input path="endDate" cssClass="form-control" id="inputEndDate"
					placeholder="End Date" readonly="true" />
			</div>
		</div>

		<!-- Button group -->
		<div class="form-group text-center">
			<div class="btn-group">
				<button type="submit" class="btn btn-danger">
					<b><spring:message code="button.editpage.save" /></b>
				</button>
				<a href="javascript:onCancel()" class="btn btn-default">
					<b><spring:message code="button.editpage.cancel" /></b>
				</a>
			</div>
		</div>
		
		<!-- I18N date format -->
		<div id="dateFormatString" style="display: none;">
			<spring:message code="format.date" />
		</div>
	</form:form>
	
	<!-- JS -->
	<script type="text/javascript">
		$(function() {
			var dateFormat = $("#dateFormatString").html();
			dateFormat = dateFormat.toLowerCase().replace("yy", "");
			
			$("#inputStartDate").datepicker({
				dateFormat: dateFormat
			});
			$("#inputEndDate").datepicker({
				dateFormat: dateFormat
			});
		});
	
	    function onCancel() {
	        var newURL = document.URL 
	        	+ "&pnumber=" + $("#pnumber").val() 
	        	+ "&_cancel";
	        document.location=newURL;
	    }
	</script>
</div>