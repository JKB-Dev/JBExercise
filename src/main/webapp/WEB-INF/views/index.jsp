<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>HTML input</title>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootswatch/4.1.3/minty/bootstrap.min.css" />
</head>
<body>

<h1> Enter the name of an HTML file (omitting extension) located at this project's root directory: </h1>

<div style="margin-left: 50px; margin-top: 50px;">
	<form action="/parse">
		<input type=text name="htmlfile" placeholder="file name">
		<input type="submit" value="submit">
	</form>
</div> 

${tag}

</body>
</html>