<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!Doctype html>
<html>


<head>
	<title>Groups List</title>
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
	 integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
</head>


<body>
<nav class="navbar navbar-dark bg-dark" style="background-color: black">
    <div class="navbar-header">
      <a class="navbar-brand" style="color:white"  href="${pageContext.request.contextPath}/home">PasswordManagementTool</a>
  </div>
    <ul class="nav">
      <li><a style=color:white;text-decoration:none" href="addGroupDetails">Add Group</a></li>&nbsp &nbsp &nbsp
      <li><a style=color:white;text-decoration:none" href="${pageContext.request.contextPath}/logout"> Logout</a></li>
    </ul>
</nav>
<br>
<br>
<c:if test="${errorMessage != null}">
					<div class="alert alert-danger" role="alert">${errorMessage}</div>
				</c:if>
<div class="row">
 	<c:forEach var="tempGroup" items="${groupList}">
 		
  <div class="col-sm-6">
    <div class="card">
      <div class="card-body">
        <h5 class="card-title">${tempGroup.groupName}</h5>
        <p class="card-text">It's a broader card with text below as a natural lead-in to extra content. This content is a little longer.</p>
       <table>
       <tr>
       	  <th><form:form action="${pageContext.request.contextPath}/account/viewAccountDetails" method="post" modelAttribute="${tempGroup}">
        	<input type="hidden" name="groupId" value="${tempGroup.groupId}"/>
        <input type="submit" class="btn btn-primary" value="View Accounts">
        </form:form></th>
        
        <c:if test="${tempGroup.groupName != 'General' }">
			
        <th> <form:form action="updateGroupDetails" method="post" modelAttribute="${tempGroup}">
        	<input type="hidden" name="groupId" value="${tempGroup.groupId}"/>
        <input type="submit" class="btn btn-primary" value="UpdateGroup">
        </form:form></th>
      
       <th> <form:form action="deleteGroupDetails" method="post" modelAttribute="${tempGroup}">
        	<input type="hidden" name="groupId" value="${tempGroup.groupId}"/>
        <input type="submit" class="btn btn-primary" value="DeleteGroup">
        </form:form></th>
        
        </c:if>
       </tr>
       </table>
      </div>
    </div>
  </div>
  </c:forEach>
 </div>

</body>
</html>