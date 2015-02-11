<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>

<!DOCTYPE html>
<html lang="en">
<head>
<title>Project Information Management</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">

<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css">
<link rel="stylesheet" href="resources/css/jquery-ui.css">
	
<link rel="stylesheet" type="text/css" href="resources/css/template.css">
<link rel="stylesheet" type="text/css" href="resources/css/header.css">
<link rel="stylesheet" type="text/css" href="resources/css/menu.css">
<link rel="stylesheet" type="text/css" href="resources/css/search.css">
<link rel="stylesheet" type="text/css" href="resources/css/edit.css">

<script src="resources/js/jquery-1.10.2.js"></script>
<script src="resources/js/jquery-ui.js"></script>

<script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>

</head>

<body>
	<div class="container">
		<div class="row">
			<!-- Left side menu -->
			<div class="menu-panel col-sm-2">
				<tiles:insertAttribute name="menu" />
			</div>

			<!-- Right side header + body -->
			<div class="col-sm-10">
				<div class="header-panel"><tiles:insertAttribute name="header" /></div>
				<div class="content-panel"><tiles:insertAttribute name="content" /></div>
			</div>
		</div>
	</div>

	<!-- JavaScripts - bootstrap -->
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
	<script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>
		
	<!-- JavaScripts - jQuery -->
	<script src="//code.jquery.com/jquery-1.10.2.js"></script>
  	<script src="//code.jquery.com/ui/1.11.2/jquery-ui.js"></script>
</body>
</html>