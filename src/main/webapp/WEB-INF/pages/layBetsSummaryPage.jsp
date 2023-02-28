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

<h1>Lay Bets Summary</h1>

<div class="container-fluid mt-1">




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


<tags:scripts/>
<tags:javascriptVariables />
</body>
</html>