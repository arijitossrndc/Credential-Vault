<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<!Doctype html>

<html>

<head><title> Login</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
	 integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
</head>
<body>
<nav class="navbar navbar-dark bg-dark" style="background-color: black">
  
    <div class="navbar-header">
      <a class="navbar-brand" style="color:white"  href="${pageContext.request.contextPath}/home">PasswordManagementTool</a>
  </div>
</nav>
<br>
<br>
<div class="container">
		<div class="card mx-auto vcenter ">
			<div class="card-header">
				<h2 class="card-title">Login</h2>
			</div>
			<div class="card-body">
				<c:if test="${param.error != null}">
					<div class="alert alert-danger" role="alert">Invalid Username / Password</div>
				</c:if>
				
				<c:if test="${param.logout != null}">
					<div class="alert alert-danger" role="alert">You Logout Successfully!!</div>
				</c:if>
				<form:form action="perform_login" method="post">
					<div class="form-group">
						<label for="exampleInputEmail1">Username</label> 						
						<input
							name="username" required type="text" class="form-control"
							id="exampleInputEmail1" aria-describedby="emailHelp"
							placeholder="Enter username">
					</div>
					<div class="form-group">
						<label for="exampleInputPassword1">Password</label> 
						<input
							required name="password" type="password" class="form-control"
							id="exampleInputPassword1" placeholder="Password">
					</div>
					<button type="submit" class="btn btn-primary">Login</button>
				</form:form>
			</div>

			<div class="card-footer">
				<div class="d-flex justify-content-center links">
					Don't have an account?<a
						href="${pageContext.request.contextPath}/register">Sign Up</a>
				</div>
			</div>

		</div>
	</div>

</body>