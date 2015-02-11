<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="project-menu">
	<!-- ELCA Brand -->
	<div class="menu-logo">
		<img src="resources/images/elca.jpg" class="img-responsive" />
	</div>
	
	<!-- Check item active -->
	<c:set var="searchActive" value="" /> 
	<c:set var="searchActive" value="" /> 
	<c:if test="${page eq 'search'}">
		<c:set var="searchActive" value="class=\"active\"" /> 
	</c:if>
	<c:if test="${page eq 'edit'}">
		<c:set var="editActive" value="class=\"active\"" /> 
	</c:if>

	<!-- Navigation menu -->
	<div class="menu-content">
		<div>
			<h5>
				<a href="#"><b><spring:message code="label.menu.menuhead" /></b></a>
			</h5>
		</div>
		<div>
			<h5 style="color: #ca3928">
				<b><spring:message code="label.menu.searchhead" /></b>
			</h5>
			<ul class="nav nav-pills nav-stacked">
				<li ${searchActive}>
					<a href="${pageContext.request.contextPath}/search">
						<spring:message code="label.menu.searchhead.project" />
					</a>
				</li>
				<li><a href="#"><spring:message code="label.menu.searchhead.affair" /></a></li>
				<li><a href="#"><spring:message code="label.menu.searchhead.customer" /></a></li>
				<li><a href="#"><spring:message code="label.menu.searchhead.account" /></a></li>
				<li><a href="#"><spring:message code="label.menu.searchhead.supplier" /></a></li>
			</ul>
		</div>
		<div>
			<h5 style="color: #ca3928">
				<b><spring:message code="label.menu.newhead" /></b>
			</h5>
			<ul class="nav nav-pills nav-stacked">
				<li ${editActive}>
					<a href="${pageContext.request.contextPath}/edit?pnumber=0">
						<spring:message code="label.menu.newhead.project" />
					</a>
				</li>
				<li><a href="#"><spring:message code="label.menu.newhead.customer" /></a></li>
				<li><a href="#"><spring:message code="label.menu.newhead.supplier" /></a></li>
			</ul>
		</div>
	</div>
</div>