<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Fancy Player Run</title>
</head>
<body class="fancy-player-run-page">

<h1>Fancy Player Run</h1>

<div class="container-fluid mt-1">

<div class="row">
<div class="w-50">

<select class="form-select mt-1 mb-2" aria-label="Default select example" id="matchesDropdown">
  <option selected>Select Match</option>
  <c:forEach var="match" items="${allMatches}">
  	<option value="${match.matchId}" data-matchId="${match.matchId}">${match.matchTitle}</option>
  </c:forEach>
</select>
</div>
<div class="w-50">
<span class="badge rounded-pill bg-primary mb-2"  id="matchDateDisplay"></span>
</div>

</div>

<tags:strategyDescriptor/>
<tags:fancyWktNotRecords/>


<a class="btn btn-primary" href="#" role="button" id="batsmanScoresForFancyLink">Batsman Scores</a>






<table class="table table-sm table-hover text-center table-bordered pt-1 display mt-2" id="fancyRunAnalysisDataTable">
	<thead style="position: sticky;top: 0" class="bg-light">
	    <tr>
	      <th scope="col">Player Name</th>
	      <th scope="col">Inning</th>
	      <th scope="col">Lay Bets</th>
	      <th scope="col">Total Bets</th>
	      <th scope="col">Batsman Scored</th>
	      <th scope="col">Strategy 1</th>
	      <th scope="col">Strategy 2</th>
	      <th scope="col">Strategy 3</th>
	    </tr>
  </thead>
  <tbody></tbody>
</table> 





</div>

<tags:scripts/>
</body>
</html>