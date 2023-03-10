<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Lay Bets Summary</title>
</head>
<body class="laybets-summary-page">



<div class="container-fluid mt-1">

<div class="bg-a1 fs-3 text-center text-white p-1 mb-2">
	Lay Bets Summary
</div>



<div class="row">

<div class="w-50">
<table class="table table-sm table-hover text-center table-bordered pt-1" id="layBetsSummaryDataTable">
	<thead style="position: sticky;top: 0" class="table-dark">
	    <tr>
	      <th scope="col">Bet</th>
	      <th scope="col">Occurances</th>
	      <th scope="col">Pass</th>
	      <th scope="col">Fail</th>
	      <th scope="col">Win Rate</th>
	    </tr>
  </thead>
</table> 
</div>


<div class="w-50">

<div class="row">
<select class="form-select mt-1 mb-2 countwise-lay-bets-dropdowns w-25 me-2" aria-label="Default select example" id="layBetsCountDropdown">
  <option selected value="">Select Bet Count</option>
  <option value="1">1 bet</option>
  <option value="2">2 bets</option>
  <option value="3">3 bets</option>
  <option value="4">4 bets</option>
  <option value="5">5 bets</option>
  <option value="6">6 bets</option>
</select>

<select class="form-select mt-1 mb-2 countwise-lay-bets-dropdowns w-50" aria-label="Default select example" id="layBetsInningsDropdown">
  <option selected value="">Select Innings</option>
  <option value="0">Both Innings</option>
  <option value="1">1st Innings</option>
  <option value="2">2nd Innings</option>
</select>
</div>

<table class="table table-sm table-hover text-center table-bordered pt-1 table-dark" id="layBetsCouintWiseDataTable">
	<thead style="position: sticky;top: 0" class="">
	    <tr>
	      <th scope="col">Bets</th>
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