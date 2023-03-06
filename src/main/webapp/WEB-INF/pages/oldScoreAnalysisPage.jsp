<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Old Scores Analysis</title>
</head>
<body class="old-score-page">



<div class="container-fluid mt-1">

<div class="bg-a1 fs-3 text-center text-white p-1">
	Old Scores Analysis
</div>


<div class="row">

<div class="w-50">

<select class="form-select mt-1 mb-2 old-score-dropdowns" aria-label="Default select example" id="oldTournamentsDropdown">
  <option selected value="">Select Tournaments</option>
  <c:forEach var="tournament" items="${allTournaments}">  
  	<option value="${tournament.matchId}" data-tournamentId="${tournament.matchId}">${tournament.matchId}</option>
  </c:forEach>
</select>
</div>

<div class="w-25">
<select class="form-select mt-1 mb-2 old-score-dropdowns" aria-label="Default select example" id="inningsDropdown">
  <option selected value="">Select Innings</option>
  <option value="0">Both Innings</option>
  <option value="1">1st Innings</option>
  <option value="2">2nd Innings</option>
</select>
</div>

<div class="w-25">
<select class="form-select mt-1 mb-2 old-score-dropdowns" aria-label="Default select example" id="rangeDropdown">
  <option selected value="">Select Score Range</option>
  <option value="3">3</option>
  <option value="5">5</option>
  <option value="10">10</option>
  <option value="15">15</option>
  <option value="20">20</option>
  <option value="25">25</option>
  <option value="30">30</option>
  <option value="35">35</option>
  <option value="40">40</option>
</select>
</div>

<%-- <select class="form-select mt-1 mb-2" aria-label="Default select example" id="oldMatchesDropdown">
  <option selected>Select Match</option>
  <c:forEach var="match" items="${allMatches}">  
  	<option value="${match.matchId}" data-matchId="${match.matchId}">${match.matchTitle}</option>
  </c:forEach>
</select>
</div> --%>



</div>


<div class="row">

<div class="w-50">
		<table class="table table-sm table-hover text-center table-bordered pt-1 display mt-2" id="scoreRanegAnalysisDataTable">
			<thead style="position: sticky;top: 0" class="bg-dark text-white">
			    <tr>
			      <th scope="col">Score Range</th>
			      <th scope="col">Out Count</th>
			      <th scope="col">Out Percentage</th>
			    </tr>
		  </thead>
		  <tbody></tbody>
		</table> 
</div>
<div class="w-50">
		<table class="table table-sm table-hover text-center table-bordered pt-1 display mt-2 table-dark" id="scoreAnalysisDataTable">
			<thead style="position: sticky;top: 0" class="bg-dark text-white">
			    <tr>
			      <th scope="col">Scored Less Than Equal</th>
			      <th scope="col">Out Count</th>
			      <th scope="col">Out Percentage</th>
			    </tr>
		  </thead>
		  <tbody></tbody>
		</table> 
</div>
</div>


</div>

<tags:scripts/>
<tags:javascriptVariables />
</body>
</html>