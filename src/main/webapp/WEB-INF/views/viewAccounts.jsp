<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!Doctype html>
<html>
<head>
	<title>Accounts List</title>
	<head>
	<title>Account List</title>
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
	 integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
</head>
<title>Accounts List</title>
</head>
<body>
<body>
<nav class="navbar navbar-dark bg-dark" style="background-color: black">
    <div class="navbar-header">
      <a class="navbar-brand" style="color:white"  href="${pageContext.request.contextPath}/home">PasswordManagementTool</a>
  </div>
    <ul class="nav">
      <li><a style=color:white;text-decoration:none" href="${pageContext.request.contextPath}/group/viewGroupDetails">View Groups</a></li>&nbsp &nbsp
      <li><a style=color:white;text-decoration:none" href="addAccountDetails">Add Account</a></li>&nbsp &nbsp &nbsp
      <li><a style=color:white;text-decoration:none" href="${pageContext.request.contextPath}/logout"> Logout</a></li>
    </ul>
</nav>
<br>
<div id="wrapper">
	<div id="header"> 
		<h2 style="text-align:center">AccountsList</h2>
	</div>
</div>
<br><br>

<h2><b><p style="text-align:center">${accountBean.groupBean.groupName}</p></b></h2>

<div class="row">
 	<c:forEach var="tempAccount" items="${accountsList}">
 
  <div class="col-sm-6">
    <div class="card">
      <div class="card-body">
        <h5 class="card-title">${tempAccount.accountName}</h5>
        <p class="card-text">Username: ${tempAccount.username}</p>
        <p class="card-text">Password: ${tempAccount.password}</p>
        <p class="card-text">URL: ${tempAccount.url}</p>
        <p class="card-text">Comments: ${tempAccount.comments}</p>
   
        
        
         <table>
       <tr>
       	  <th><form:form action="updateAccountDetails" method="post" modelAttribute="${tempAccount}">
        	<input type="hidden" name="accountId" value="${tempAccount.accountId}"/>
        <input type="submit" class="btn btn-primary" value="Update Account">
        </form:form></th>
        <br>
        <th> <form:form action="deleteAccountDetails" method="post" modelAttribute="${tempAccount}">
        	<input type="hidden" name="accountId" value="${tempAccount.accountId}"/>
        <input type="submit" class="btn btn-primary" value="Delete Account">
        </form:form></th>
        <br>
       </tr>
       </table>
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
      </div>
    </div>
  </div>
  </c:forEach>
 </div>


<!--<div id="container">
	<div id="content">
	
	<table>
	<tr>
		<th>Account Name</th>
		<th>Username</th>
		<th>Password</th>
		<th>url</th>
		<th>comments</th>
		<th>Action</th>
	</tr>
	<c:forEach var="tempAccount" items="${accountsList}">
	
	<c:url var="updateLink" value="updateAccountDetails">
		<c:param name="accountId" value="${tempAccount.accountId}"/>
	</c:url>
	<tr>
		<td>${tempAccount.accountName}</td>
		<td>${tempAccount.username}</td>
		<td>${tempAccount.password}</td>
		<td>${tempAccount.url}</td>
		<td>${tempAccount.comments}</td>
		<td><a href="${updateLink}" class="btn btn-primary"> Update Account</a></td>
	</tr>
	</c:forEach>
	
	</table>
	
	</div>
</div>
-->
</body>
</html>