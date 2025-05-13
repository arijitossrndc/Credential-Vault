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
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.13.0/css/all.min.css">
<style>
.field-icon {
  float: right;
  margin-left: -30px;
  margin-top: -25px;
  position: relative;
  z-index: 2;
}

.container{
  padding-top:50px;
  margin: auto;
}
.has-error{
color:red;
}
</style>
<c:url value="/js/jquery.js" var="jstJs" />
<script src="${jstJs}" type="text/javascript"></script>
</head>
<body>
<nav class="navbar navbar-dark bg-dark" style="background-color: black">
    <div class="navbar-header">
      <a class="navbar-brand" style="color:white"  href="${pageContext.request.contextPath}/home">PasswordManagementTool</a>
  </div>
</nav>
<br>
<br>
	<h1 style=text-align:center>${type}</h1>
	
	<c:if test="${errorMessage != null}">
					<div class="alert alert-danger" role="alert">${errorMessage}</div>
				</c:if>
	
	<div class="container">
	<div class="row">
  		<div class="col-md-12 col-md-offset-2">
   		<div class="panel panel-default">
      	<div class="panel-body">
	<form:form action="addAccountDetails" method="post" modelAttribute="accountBean" class="form-horizontal">
		<form:hidden path="accountId"/>
	
		<div class="form-group">
		<label class="col-md-4 control-label">Account Name</label>
				<div class="col-md-6">
				 <form:input path="accountName" class="form-control"/><b style="color: red">
				 <form:errors path="accountName" class="form-control"  cssClass="has-error"></form:errors></b>
				 </div>
		</div>
		<div class="form-group">
		<label class="col-md-4 control-label">Username</label>
				<div class="col-md-6">
				<form:input path="username" class="form-control"/><b style="color: red">
				 <form:errors path="username" class="form-control"  cssClass="has-error"></form:errors></b>
				 </div>
		</div>
		
		<div class="form-group">
		<label class="col-md-4 control-label">Password</label>
				<div class="col-md-6">
			<form:password id="id_password" path="password" class="form-control" value="${accountBean.password}"/>
				 <span class="far fa-eye field-icon" id="togglePassword" cursor:pointer;></span>
				<b style="color: red"><form:errors path="password" class="form-control"  cssClass="has-error"></form:errors></b>
				</div>
		</div>
		<div class="form-group">
		<label class="col-md-4 control-label">URL(Optional)</label>
				<div class="col-md-6">
				<form:input path="url" class="form-control"/><b style="color: red">
				 <form:errors path="url" class="form-control"  cssClass="has-error"></form:errors></b>
				 </div>
		</div>
			
		<div class="form-group">
		<label class="col-md-4 control-label">Comments(Optional)</label>
				<div class="col-md-6">
				<form:input path="comments" class="form-control"/><b style="color: red">
				 <form:errors path="comments" class="form-control"  cssClass="has-error"></form:errors></b>
				 </div>
		</div>
			
		<div class="form-group">
		<label class="col-md-4 control-label">Groups Available</label>
		<div class="col-md-6">
				<form:select path="groupId" class="form-control">
				<form:options items="${accountBean.groupOptions}"/>
				</form:select>
		</div>
		</div>
		<input type="submit" value="${type}" class="btn btn-primary" style="margin-left: 20%"/>
	</form:form>
	
	<script>
const togglePassword = document.querySelector('#togglePassword');
  const password = document.querySelector('#id_password');
 
  togglePassword.addEventListener('click', function (e) {
    // toggle the type attribute
    const type = password.getAttribute('type') === 'password' ? 'text' : 'password';
    password.setAttribute('type', type);
    // toggle the eye slash icon
    this.classList.toggle('fa-eye-slash');
});
</script>
	<br>
	</div>
	</div>
	</div>
	</div>
	</div>
</body>
</html>