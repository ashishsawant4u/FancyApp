<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Batsman Score</title>
</head>
<body class="batsman-score-page">


<div class="container-fluid mt-1">
<div class="bg-a1 fs-3 text-center text-white p-1">
	Batsman Score
</div>

<div class="w-50">

<span class="badge rounded-pill bg-primary mb-2 mt-2"  id="matchTitleForBatsmanScore"></span>


<table class="table table-sm table-hover text-center table-bordered pt-1" id="batsmanScoreDataTable">
	<thead style="position: sticky;top: 0" class="bg-light">
	    <tr>
	      <th scope="col">Player Name</th>
	      <th scope="col">Inning</th>
	      <th scope="col">Player Run</th>
	    </tr>
  </thead>
</table> 


</div>

</div>


<tags:scripts/>
<tags:javascriptVariables />
</body>
</html>