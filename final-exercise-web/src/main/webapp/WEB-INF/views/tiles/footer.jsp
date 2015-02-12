<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<div class="text-center">
	<div><spring:message code="label.footer.company" /></div>
	<div class="btn-group btn-group-xs" role="group">
		<a href="javascript:onChangeLocale('en')" class="btn btn-default"><spring:message code="languages.english" /></a>
		<a href="javascript:onChangeLocale('vn')" class="btn btn-default"><spring:message code="languages.vietnamese" /></a>
	</div>
	
	<script type="text/javascript">
		function onChangeLocale(lang) {
			var newURL = document.URL;
			
			var questionMarkLocation = newURL.indexOf("?");
			if (questionMarkLocation == -1) {
				newURL = newURL + "?";
			}
	
			var localeIndex = newURL.indexOf("locale");
			var nextAmp = newURL.indexOf("&", localeIndex + 1);
			if (localeIndex != -1) {
				if (nextAmp != -1) {
					newURL = newURL.replace(newURL.substring(localeIndex, nextAmp), "");
				} else {
					newURL = newURL.replace(newURL.substring(localeIndex, newURL.length), "");
				}
			}
			
			if (newURL.lastIndexOf("&") == -1) {
				newURL = newURL + "&locale=" + lang;
			} else {
				newURL = newURL + "locale=" + lang;
			}
			
	
			document.location = newURL;
		}
	</script>
</div>