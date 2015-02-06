<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title><spring:message code="project_detail_title" />${project.number}</title>

<style type="text/css">
	input[type="text"] {
		width: 200px;
	}
	.error {
	    color: #ff0000;
	}
	.errorblock {
	    color: #000;
	    background-color: #ffEEEE;
	    border: 3px solid #ff0000;
	    padding: 8px;
	    margin: 16px;
	}
</style>
</head>

<body>
<form:form id="editProjectForm" action="" method="POST" commandName="project">
    <form:errors path="*" cssClass="errorblock" element="div" />
    
    <%-- Hidden values --%>
    <form:hidden path="id" />
    <form:hidden path="number" />
    
    <table>
        <tr>
            <td><spring:message code="project_number" /></td>
            <td><form:input path="number" disabled="true"/></td>
            <td><form:errors path="number" cssClass="error" /></td>
        </tr>
        <tr>
            <td><spring:message code="name" /></td>
            <td><form:input path="name" /></td>
            <td><form:errors path="name" cssClass="error" /></td>
        </tr>
        <tr>
            <td><spring:message code="project_leader" /></td>
            <td>
            	<form:select path="leader">
	                <option value="-1">Please select ...</option>
	                <c:forEach var="employee" items="${allEmployees}">
	                    <option value="${employee.id}" <c:if test="${project.leader.id == employee.id}">selected="selected"</c:if>>
							${employee.visa}
	                    </option>
	                </c:forEach>
            	</form:select>
            </td>
            <td><form:errors path="leader" cssClass="error" /></td>
        </tr>
        <tr>
            <td><spring:message code="group" /></td>
            <td>
	            <form:select path="group">
	                <option value="-1">Please select ...</option>
	                <c:forEach var="group" items="${allGroups}">
	                    <option value="${group.id}" <c:if test="${project.group.id == group.id}">selected="selected"</c:if>>
	                    	${group.leader.visa}
	                    </option>
	                </c:forEach>
	            </form:select></td>
            <td><form:errors path="group" cssClass="error" /></td>
        </tr>
        <tr>
            <td><spring:message code="finished_by" /></td>
            <td><form:input path="finishingDate" /></td>
            <td><form:errors path="finishingDate" cssClass="error" /></td>
        </tr>
        <tr>
            <td><form:button><spring:message code="save"/></form:button></td>
            <td><input type="button" value="<spring:message code="cancel"/>" onclick="javascript:onCancel()" /></td>
        </tr>
    </table>
</form:form>

<!-- JS -->
<script type="text/javascript">
    function onCancel() {
        var newURL = document.URL + "&_cancel";
        document.location=newURL;
    }
</script>
</body>

</html>