<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
	 integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
<style>
.has-error{
color:red;
}

</style>
</head>
<body>
<nav class="navbar navbar-dark bg-dark" style="background-color: black">
    <div class="navbar-header">
      <a class="navbar-brand" style="color:white"  href="${pageContext.request.contextPath}/home">PasswordManagementTool</a>
  </div>
    <ul class="nav">
      <li><a style=color:white;text-decoration:none" href="${pageContext.request.contextPath}/logout"> Logout</a></li>
    </ul>
</nav>
<br>
<br>
	<h1 style=text-align:center> ${type}</h1>
	
	<c:if test="${errorMessage != null}">
					<div class="alert alert-danger" role="alert">${errorMessage}</div>
				</c:if>
	<form:form action="addGroupDetails" method="post" modelAttribute="groupBean">
	
	<form:hidden path="groupId"/>
	<div class="row">
	<div class="col">
			<label for="exampleInputEmail1">Group Name</label> 
				<form:input path="groupName" class="form-control" placeholder="Group Name"/><b style="color: red">
				 <form:errors path="groupName" cssClass="has-error"></form:errors></b>
				 <br>
				<input style="text-align:center" class="btn btn-primary" type="submit" value="${type}" />
	</form:form>
	</div>
	</div>
</body>
</html>