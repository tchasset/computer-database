<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 

<!DOCTYPE html>
<html>
<head>
	<title>Computer Database</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<!-- Bootstrap -->
	<link href="css/bootstrap.min.css" rel="stylesheet" media="screen">
	<link href="css/font-awesome.css" rel="stylesheet" media="screen">
	<link href="css/main.css" rel="stylesheet" media="screen">
	<script type="text/javascript" src="js/jquery.min.js"></script>
	<script type="text/javascript" src="js/validate.js"></script>
</head>

<body>
    <header class="navbar navbar-inverse navbar-fixed-top">
        <div class="container">
            <a class="navbar-brand" href="dashboard"> Application - Computer Database </a>
        </div>
    </header>
    <section id="main">
        <div class="container">
            <div class="row">
                <div class="col-xs-8 col-xs-offset-2 box">
                    <div class="label label-default pull-right">
                        id:${computer.id}
                    </div>
                    <h1>Edit Computer</h1>
					<div class="ici"></div>
					<c:if test="${error!=null}">
						<div class="alert alert-danger" >
						<c:forEach items="${ error }" var="err">
							<b>${err}</b><br/>
						</c:forEach>  
						</div>
					</c:if>
					
                    <form action="editComputer?id=${computer.id}" method="POST" name="form" onsubmit="return valider()">
                        <input type="hidden" value="${computer.id}" id="id" name="id"/>
                        <fieldset>
                            <div class="form-group">
                                <label for="computerName">Computer name</label>
                                <input type="text" class="form-control" name="computerName" id="computerName" placeholder="Computer name" value="${computer.getName()}">
                            </div>
                            <div class="form-group">
                                <label for="introduced">Introduced date</label>
                                <input type="date" class="form-control" name="introduced" id="introduced" placeholder="Introduced date" value="${computer.getIntroduced()}">
                            </div>
                            <div class="form-group">
                                <label for="discontinued">Discontinued date</label>
                                <input type="date" class="form-control" name="discontinued" id="discontinued" placeholder="Discontinued date" value="${computer.getDiscontinued()}">
                            </div>  
                            <div class="form-group">
                                <label for="companyId">Company</label>
                                <select class="form-control" id="companyId" name="companyId" >
                                    <option value="${computer.getCompanyDTO().getId() }">${computer.getCompanyDTO().getName() }</option>
                                  	<c:forEach items="${ companyName }" var="company">
										<option value="${ company.getId() }">
											<c:out value="${ company.getName() }" /> </option>
									</c:forEach>          
                                </select>
                            </div>          
                        </fieldset>
                        <div class="actions pull-right">
                            <input type="submit" value="Edit" class="btn btn-primary">
                            or
                            <a href="dashboard" class="btn btn-default">Cancel</a>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </section>
</body>
</html>