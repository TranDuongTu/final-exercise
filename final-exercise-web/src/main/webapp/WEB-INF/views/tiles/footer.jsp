<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<div class="text-center">
	<div>© ELCA Informatique SA</div>
	<div class="btn-group btn-group-xs" role="group">
		<a href="javascript:onChangeLocale('en')" class="btn btn-default"><spring:message code="languages.english" /></a>
		<a href="javascript:onChangeLocale('vn')" class="btn btn-default"><spring:message code="languages.vietnamese" /></a>
	</div>
	
	<script type="text/javascript">
		function onChangeLocale(lang) {
			var newURL = "";
			var questionMarkLocation = document.URL.indexOf("?");
			if (questionMarkLocation != -1) {
				newURL = document.URL.substring(0, questionMarkLocation);
			} else {
				newURL = document.URL;
			}
	
			newURL = newURL + "?locale=" + lang;
	
			document.location = newURL;
		}
	</script>
</div>