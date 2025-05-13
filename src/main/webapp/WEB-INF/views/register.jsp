<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<!Doctype html>

<html>

<head><title> Registration</title>
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
  <div class="container-fluid">
    <div class="navbar-header">
      <a class="navbar-brand" style="color:white"  href="${pageContext.request.contextPath}/home">PasswordManagementTool</a>
  </div>
</nav>
<br>
<br>
<div class="container">
		<div class="card mx-auto vcenter ">
			<div class="card-header">
				<h2 class="card-title">Register</h2>
			</div>
			<div class="card-body">
				<c:if test="${usernameError != null}">
					<div class="alert alert-danger" role="alert">${usernameError}</div>
				</c:if>
				<c:if test="${emailError != null}">
					<div class="alert alert-danger" role="alert">${emailError}</div>
				</c:if>
				<form:form modelAttribute="userBean" method="post">
					<div class="form-group">
						<label for="exampleInputEmail1">Email</label> 
						<input type="text" name="email" class="form-control" required placeholder="email" value="${userBean.email}">
						<form:errors cssClass="has-error" path="email"/>
						
					</div>
					<div class="form-group">
						<label for="exampleInputEmail1">User Name</label> 
						<input type="text" name="username" class="form-control" required placeholder="user name" value="${userBean.username}">
						<form:errors cssClass="has-error" path="username" />
					</div>
					<div class="form-group">
						<label for="exampleInputPassword1">Password</label> 
						<input type="text" name="password" class="form-control" required placeholder="password" value="${userBean.password}">
						<form:errors cssClass="has-error" path="password" />
					</div>
					<button type="submit" class="btn btn-primary">${type}</button>
				</form:form>
			</div>
			<div class="card-footer">
				<div class="d-flex justify-content-center links">
					Already Registered?<a
						href="${pageContext.request.contextPath}/login">Login</a>
				</div>
			</div>

		</div>
	</div>

</body>
</html>