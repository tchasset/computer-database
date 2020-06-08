<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 

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
            <a class="navbar-brand" href="dashboard.html"> Application - Computer Database </a>
        </div>
    </header>

    <section id="main">
        <div class="container">
            <h1 id="homeTitle">
                ${nbComputer} Computers found
            </h1>
            <div id="actions" class="form-horizontal">
                <div class="pull-left">
                    <form id="searchForm" action="#" method="GET" class="form-inline">

                        <input type="search" id="searchbox" name="search" class="form-control" placeholder="Search name" />
                        <input type="submit" id="searchsubmit" value="Filter by name"
                        class="btn btn-primary" />
                    </form>
                </div>
                <div class="pull-right">
                    <a class="btn btn-success" id="addComputer" href="addComputer">Add Computer</a> 
                    <a class="btn btn-default" id="editComputer" href="#" onclick="$.fn.toggleEditMode();">Edit</a>
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
                        <th>
                            Computer name
                        </th>
                        <th>
                            Introduced date
                        </th>
                        <!-- Table header for Discontinued Date -->
                        <th>
                            Discontinued date
                        </th>
                        <!-- Table header for Company -->
                        <th>
                            Company
                        </th>

                    </tr>
                </thead>
                <!-- Browse attribute computers -->
                <tbody id="results">
                	<c:forEach items="${ computerList }" var="computer">
						<tr>
	                        <td class="editMode">
	                            <input type="checkbox" name="cb" class="cb" value="0">
	                        </td>
	                        <td>
	                            <a href="editComputer.html" onclick="">${computer.getName()}</a>
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
	            <a href="dashboard?page=1#">Retour première page</a>
	        </div>
            <ul class="pagination">
            	<c:set var="page" value="${currentPage}"/>
            	<li>
                    <c:if test="${page!=1}"><a href="dashboard?page=${page-1}#" aria-label="Previous">
                      <span aria-hidden="true">&laquo;</span>
                  	</a></c:if>
             	</li>
            	
            	<c:choose>
				    <c:when test="${maxPage > 5}">
				    	<c:if test="${page<=maxPage-5}">
					    	<li><a href="dashboard?page=${page}#">${page}</a></li>
				            <li><a href="dashboard?page=${page+1}#">${page+1}</a></li>
				            <li><a >...</a></li>
				            <li><a href="dashboard?page=${maxPage-1}#">${maxPage-1}</a></li>
				            <li><a href="dashboard?page=${maxPage}#">${maxPage}</a></li>
				        </c:if> 
				        <c:if test="${page>maxPage-5}">
					    	<li><a href="dashboard?page=${maxPage-4}#">${maxPage-4}</a></li>
				            <li><a href="dashboard?page=${maxPage-3}#">${maxPage-3}</a></li>
				            <li><a href="dashboard?page=${maxPage-2}#">${maxPage-2}</a></li>
				            <li><a href="dashboard?page=${maxPage-1}#">${maxPage-1}</a></li>
				            <li><a href="dashboard?page=${maxPage}#">${maxPage}</a></li>
				        </c:if>   
				    </c:when>
				    <c:otherwise>
				        <c:forEach var="i" begin="1" end="${maxPage}" >
				        	<li><a href="dashboard?page=${i}#">${i}</a></li>
				        </c:forEach>
				    </c:otherwise>
				</c:choose>
            
                    
              <!-- <li><a href="dashboard?page=${page}#">1</a></li>
              <li><a href="dashboard?page=${page}#">2</a></li>
              <li><a href="dashboard?page=${page}#">3</a></li>
              <li><a href="dashboard?page=${page}#">4</a></li>
              <li><a href="dashboard?page=${page}#">5</a></li>-->
              <li>
                <c:if test="${page!=maxPage}"><a href="dashboard?page=${page+1}#" aria-label="Next">
                    <span aria-hidden="true">&raquo;</span>
                </a></c:if>
            </li>
        </ul>

        <div class="btn-group btn-group-sm pull-right" role="group" >
            <a href="dashboard?size=10&page=1#"><button  type="button" class="btn btn-default">10</button></a>
            <a href="dashboard?size=50&page=1#"><button type="button" class="btn btn-default">50</button></a>
            <a href="dashboard?size=100&page=1#"><button type="button" class="btn btn-default">100</button></a>
        </div>

    </footer>
<script src="js/jquery.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<script src="js/dashboard.js"></script>

</body>
</html>