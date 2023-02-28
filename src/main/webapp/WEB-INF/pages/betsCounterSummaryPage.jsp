<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Bets Counter</title>
</head>
<body class="betscounter-page">

<h1>Bets Counter</h1>

<div class="container-fluid mt-1">




<table class="table table-sm table-hover text-center table-bordered pt-1 caption-top" id="betsCounterSummaryDataTable">
	<caption class="fs-5 fw-bold text-primary">Innings 1 and 2 Combined</caption>
	<thead style="position: sticky;top: 0" class="table-dark">
	    <tr>
	      <th scope="col">Bet Count</th>
	      <th scope="col">Occurances</th>
	      <th scope="col">Pass</th>
	      <th scope="col">Fail</th>
	      <th scope="col">Win Rate</th>
	    </tr>
  </thead>
</table> 

<div class="row">
	<div class="w-50">
			<table class="table table-sm table-hover text-center table-bordered pt-1 caption-top" id="betsCounterSummaryDataTableInn1">
				<caption class="fs-5 fw-bold text-primary">1st Innings</caption>
				<thead style="position: sticky;top: 0" class="table-dark">
				    <tr>
				      <th scope="col">Bet Count</th>
				      <th scope="col">Occurances</th>
				      <th scope="col">Pass</th>
				      <th scope="col">Fail</th>
				      <th scope="col">Win Rate</th>
				    </tr>
			  </thead>
			</table> 
	</div>
	<div class="w-50">
			<table class="table table-sm table-hover text-center table-bordered pt-1 caption-top" id="betsCounterSummaryDataTableInn2">
				<caption class="fs-5 fw-bold text-primary">2nd Innings</caption>
				<thead style="position: sticky;top: 0" class="table-dark">
				    <tr>
				      <th scope="col">Bet Count</th>
				      <th scope="col">Occurances</th>
				      <th scope="col">Pass</th>
				      <th scope="col">Fail</th>
				      <th scope="col">Win Rate</th>
				    </tr>
			  </thead>
			</table> 
	</div>
</div>


</div>


<tags:scripts/>
<tags:javascriptVariables />
</body>
</html>