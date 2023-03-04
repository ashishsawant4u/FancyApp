<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Lay Bet Tester</title>
</head>
<body class="lay-bet-tester-page">


<div class="container-fluid mt-1">
<div class="bg-a1 fs-3 text-center text-white p-1">
	Lay Bet Tester
</div>

<div class="row mt-2">

<div class="w-50">

<select class="form-select mt-1 mb-2 lay-bet-test-dropdowns" aria-label="Default select example" id="layBetTestTournamentsDropdown">
  <option selected value="">Select Tournaments</option>
  <c:forEach var="tournament" items="${allTournaments}">  
  	<option value="${tournament.matchId}" data-tournamentId="${tournament.matchId}">${tournament.matchId}</option>
  </c:forEach>
</select>
</div>

<div class="w-25">
<select class="form-select mt-1 mb-2 lay-bet-test-dropdowns" aria-label="Default select example" id="layBettestInningsDropdown">
  <option selected value="">Select Innings</option>
  <option value="0">Both Innings</option>
  <option value="1">1st Innings</option>
  <option value="2">2nd Innings</option>
</select>
</div>

<div class="w-25">
<button type="button" class="btn btn-primary d-none" data-bs-toggle="modal" id="layBetTesterMatchwisePnlModalBtn">
  Matchwise P&L
</button>
</div>

</div>

<div class="mt-2 row">

<div class="w-75">
<table class="table table-sm table-hover text-center table-bordered pt-1 w-100" id="layBetTesterDataTable">
	<thead style="position: sticky;top: 0" class="bg-light">
	    <tr>
	      <!-- <th scope="col">Match Id</th> -->
	      <th scope="col">Match Title</th>
	      <th scope="col">Inning</th>
	      <th scope="col">Batsman</th>
	      <th scope="col">Score</th>
	      <th scope="col">Laybet</th>
	      <th scope="col">Bet PnL</th>
	    </tr>
  </thead>
  <tfoot>
        <tr>
            <!-- <th></th> -->
            <th></th>
            <th></th>
            <th></th>
            <th></th>
            <th></th>
            <th></th>
         </tr>
    </tfoot>
</table> 
</div>

<div class="w-25" id="layBetTestSummryStatsDiv">
	<table id="layBetTestSummaryStatsTable" class="table table-hover table-bordered "></table>
</div>

</div>

</div>



<tags:laybetTestMatchWisePnL/>

<tags:scripts/>
<tags:javascriptVariables />
</body>
</html>