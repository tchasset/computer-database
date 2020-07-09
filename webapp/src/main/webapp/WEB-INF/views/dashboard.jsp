<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>   

<!DOCTYPE html>
<html>
<head>
	<title>Computer Database</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta charset="utf-8">
	<!-- Bootstrap -->
	<link href="css/bootstrap.min.css" rel="stylesheet" media="screen">
	<link href="css/font-awesome.css" rel="stylesheet" media="screen">
	<link href="css/main.css" rel="stylesheet" media="screen">
</head>

<body>
    <header class="navbar navbar-inverse navbar-fixed-top">
        <div class="container">
            <a class="navbar-brand" href="dashboard?currentPage=1&size=${size}"> <spring:message code="label.title"/></a>
            <div class="btn-group btn-group-sm pull-right">
	        	<a class="navbar-brand" href="logout"> <spring:message code="label.logout"/></a>
	        </div>
        </div>
    </header>

    <section id="main">
        <div class="container">
	        <c:if test="${addSuccess!=null}">
				<div class="alert alert-success" >
					<b><spring:message code="event.addSuccess"/></b><br/>
				</div>
			</c:if>
			<c:if test="${editSuccess!=null}">
				<div class="alert alert-success" >
					<b><spring:message code="event.editSuccess"/></b><br/>
				</div>
			</c:if>
            <h1 id="homeTitle">
                ${nbComputer} <spring:message code="label.computerFound"/>
            </h1>
            <div id="actions" class="form-horizontal">
                <div class="pull-left">
                    <form id="searchForm" action="#" method="GET" class="form-inline">

                        <input type="search" id="searchbox" name="search" class="form-control" placeholder="Search name" />
                        <input type="submit" id="searchsubmit" value="<spring:message code="label.filter"/>"
                        class="btn btn-primary" />
                    </form>
                </div>
                <div class="pull-right">
                    <a class="btn btn-success" id="addComputer" href="addComputer"><spring:message code="label.addComputer"/></a> 
                    <a class="btn btn-default" id="editComputer" href="#" onclick="$.fn.toggleEditMode();"><spring:message code="label.edit"/></a>
                </div>
            </div>
        </div>

        <form id="deleteForm" action="#" method="POST">
            <input type="hidden" name="selection" value="">
        </form>

        <div class="container" style="margin-top: 10px;">
            <table class="table table-striped table-bordered">
                <thead>
                    <tr>
                        <!-- Variable declarations for passing labels as parameters -->
                        <!-- Table header for Computer Name -->

                        <th class="editMode" style="width: 60px; height: 22px;">
                            <input type="checkbox" id="selectall" /> 
                            <span style="vertical-align: top;">
                                 -  <a href="#" id="deleteSelected" onclick="$.fn.deleteSelected();">
                                        <i class="fa fa-trash-o fa-lg"></i>
                                    </a>
                            </span>
                        </th>
						
                        <th id="computerName">
                            <spring:message code="label.computerName"/>
                            <a href="dashboard?currentPage=1&size=${size}&orderByName=ASC"><i class="fa fa-arrow-up"></i></a>
                            <a href="dashboard?currentPage=1&size=${size}&orderByName=DESC"><i class="fa fa-arrow-down"></i></a>
                        </th>
                        <th>
                            <spring:message code="label.introduced"/>
                        </th>
                        <!-- Table header for Discontinued Date -->
                        <th>
                            <spring:message code="label.discontinued"/>
                        </th>
                        <!-- Table header for Company -->
                        <th id="companyName">
                            <spring:message code="label.company"/> 
                            <a href="dashboard?currentPage=1&size=${size}&orderByCompany=ASC"><i class="fa fa-arrow-up"></i></a>
                            <a href="dashboard?currentPage=1&size=${size}&orderByCompany=DESC"><i class="fa fa-arrow-down"></i></a>
                        </th>

                    </tr>
                </thead>
                <!-- Browse attribute computers -->
                <tbody id="results">
                	<c:forEach items="${ computerList }" var="computer">
						<tr>
	                        <td class="editMode">
	                            <input type="checkbox" name="cb" class="cb" value="${computer.getId()}">
	                        </td>
	                        <td>
	                            <a href="editComputer?id=${computer.getId()}" onclick="">${computer.getName()}</a>
	                        </td>
	                        <td>${computer.getIntroduced()}</td>
	                        <td>${computer.getDiscontinued()}</td>
	                        <td>${computer.getCompany().getName()}</td>
	
	                    </tr>
					</c:forEach>                                 
                </tbody>
            </table>
        </div>
    </section>

    <footer class="navbar-fixed-bottom">
        <div class="container text-center">
        	<div class="btn-group btn-group-sm pull-left" role="group" >
	            <a href="dashboard?currentPage=1&&size=${size}search=${search}&${orderByName}&${orderByCompany}#"><spring:message code="label.begin"/></a>
	        </div>
            <ul class="pagination">
            
				<c:set var="orderByName" value="orderByName=${orderByName}"/>  
				<c:set var="orderByCompany" value="orderByCompany=${orderByCompany}"/>  
            
            	<c:set var="currentPage" value="${currentPage}"/>
            	<li>
                    <c:if test="${currentPage!=1}"><a href="dashboard?currentPage=${currentPage-1}&size=${size}&search=${search}&${orderByName}&${orderByCompany}#" aria-label="Previous">
                      <span aria-hidden="true">&laquo;</span>
                  	</a></c:if>
             	</li>
            	
            	<c:choose>
				    <c:when test="${maxPage > 5}">
				    	<c:if test="${currentPage<=maxPage-5}">
					    	<li><a href="dashboard?currentPage=${currentPage}&size=${size}&search=${search}&${orderByName}&${orderByCompany}#">${currentPage}</a></li>
				            <li><a href="dashboard?currentPage=${currentPage+1}&size=${size}&search=${search}&${orderByName}&${orderByCompany}#">${currentPage+1}</a></li>
				            <li><a >...</a></li>
				            <li><a href="dashboard?currentPage=${maxPage-1}&size=${size}&search=${search}&${orderByName}&${orderByCompany}#">${maxPage-1}</a></li>
				            <li><a href="dashboard?currentPage=${maxPage}&size=${size}&search=${search}&${orderByName}&${orderByCompany}#">${maxPage}</a></li>
				        </c:if> 
				        <c:if test="${currentPage>maxPage-5}">
					    	<li><a href="dashboard?currentPage=${maxPage-4}&size=${size}&search=${search}&${orderByName}&${orderByCompany}#">${maxPage-4}</a></li>
				            <li><a href="dashboard?currentPage=${maxPage-3}&size=${size}&search=${search}&${orderByName}&${orderByCompany}#">${maxPage-3}</a></li>
				            <li><a href="dashboard?currentPage=${maxPage-2}&size=${size}&search=${search}&${orderByName}&${orderByCompany}#">${maxPage-2}</a></li>
				            <li><a href="dashboard?currentPage=${maxPage-1}&size=${size}&search=${search}&${orderByName}&${orderByCompany}#">${maxPage-1}</a></li>
				            <li><a href="dashboard?currentPage=${maxPage}&size=${size}&search=${search}&${orderByName}&${orderByCompany}#">${maxPage}</a></li>
				        </c:if>   
				    </c:when>
				    <c:otherwise>
				        <c:forEach var="i" begin="1" end="${maxPage}" >
				        	<li><a href="dashboard?currentPage=${i}&size=${size}&search=${search}&${orderByName}&${orderByCompany}#">${i}</a></li>
				        </c:forEach>
				    </c:otherwise>
				</c:choose>
            
              <li>
                <c:if test="${currentPage!=maxPage}"><a href="dashboard?currentPage=${currentPage+1}&size=${size}&search=${search}&${orderByName}&${orderByCompany}#" aria-label="Next">
                    <span aria-hidden="true">&raquo;</span>
                </a></c:if>
            </li>
        </ul>
        <div class="btn-group btn-group-sm pull-right" role="group" >
            <a href="dashboard?size=10&currentPage=1#"><button  type="button" class="btn btn-default">10</button></a>
            <a href="dashboard?size=50&currentPage=1#"><button type="button" class="btn btn-default">50</button></a>
            <a href="dashboard?size=100&currentPage=1#"><button type="button" class="btn btn-default">100</button></a>
            <select id="locales">
			    <option><spring:message code="lang.change"/></option>
			    <option value="en"> <spring:message code="lang.en"/></option>
			    <option value="fr"> <spring:message code="lang.fr"/></option>
			</select>
        </div>
	</div>
    </footer>
    
	<script src="js/jquery.min.js"></script>
	<script src="js/bootstrap.min.js"></script>
	<script src="js/dashboard.js"></script>
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