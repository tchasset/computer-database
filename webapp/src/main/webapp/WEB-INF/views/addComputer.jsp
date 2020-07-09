<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
    
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
            <a class="navbar-brand" href="dashboard"> <spring:message code="label.title"/> </a>
            <div class="btn-group btn-group-sm pull-right">
	        	<a class="navbar-brand" href="logout"> <spring:message code="label.logout"/></a>
	        </div>
        </div>
    </header>

    <section id="main">
        <div class="container">
            <div class="row">
                <div class="col-xs-8 col-xs-offset-2 box">
                    <h1><spring:message code="label.addComputer"/></h1>
                    <div class="ici"></div>
                    <c:if test="${error!=null}">
						<div class="alert alert-danger" >
						<c:forEach items="${ error }" var="err">
							<b>${err}</b><br/>
						</c:forEach>  
						</div>
					</c:if>
                    <form action="addComputer" method="POST"  name="form" onsubmit="return valider()">
                        <fieldset>
                            <div class="form-group">
                                <label for="computerName"><spring:message code="label.computerName"/></label>
                                <input type="text" class="form-control" id="computerName" name="computerName" placeholder="<spring:message code="label.computerName"/>" required>
                            </div>
                            <div class="form-group">
                                <label for="introduced"><spring:message code="label.introduced"/></label>
                                <input type="date" class="form-control" id="introduced" name="introduced" placeholder="Introduced date">
                            </div>
                            <div class="form-group">
                                <label for="discontinued"><spring:message code="label.discontinued"/></label>
                                <input type="date" class="form-control" id="discontinued" name="discontinued" placeholder="Discontinued date">
                            </div>
                            <div class="form-group">
                                <label for="companyId"><spring:message code="label.company"/></label>
                                <select class="form-control" id="companyId" name="companyId" >
                                    <option value="0">--</option>
                                  	<c:forEach items="${ companyName }" var="company">
										<option value="${ company.getId() }">
											<c:out value="${ company.getName() }" /> </option>
									</c:forEach>          
                                </select>
                            </div>                  
                        </fieldset>
                        <div class="actions pull-right">
                            <input type="submit" value="<spring:message code="label.add"/>" class="btn btn-primary">
                            or
                            <a href="dashboard" class="btn btn-default"><spring:message code="label.cancel"/></a>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </section>	
    <footer class="navbar-fixed-bottom">
    	<div class="container text-center">
		    <div class="btn-group btn-group-sm pull-right" role="group" >
	            <select id="locales">
				    <option><spring:message code="lang.change"/></option>
				    <option value="en"> <spring:message code="lang.en"/></option>
				    <option value="fr"> <spring:message code="lang.fr"/></option>
				</select>
	        </div>
		</div>
    </footer>
    
    <script type="text/javascript">
	$(document).ready(function() {
	    $("#locales").change(function () {
	        var selectedOption = $('#locales').val();
	        if (selectedOption != ''){
	            window.location.replace('?lang=' + selectedOption);
	        }
	    });
	});
	</script>
</body>
</html>