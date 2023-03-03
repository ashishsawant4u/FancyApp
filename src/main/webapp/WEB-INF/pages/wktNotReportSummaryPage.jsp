<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Wkt Not Summary</title>
</head>
<body class="wktnot-report-page">



<div class="container-fluid mt-1">

<div class="bg-a1 fs-3 text-center text-white p-1">
	Wkt Not Summary
</div>


<div class="mt-1">

<table class="table table-sm table-hover text-center table-bordered pt-1" id="wktNotReportSummaryDataTable">
	<thead style="position: sticky;top: 0" class="bg-light">
	    <tr>
	      <th scope="col">Match Title</th>
	      <th scope="col">Date</th>
	      <th scope="col">Strategy1 PnL</th>
	      <th scope="col">Strategy2 PnL</th>
	      <th scope="col">Strategy3 PnL</th>
	      <th scope="col">Strategy4 PnL</th>
	      <th scope="col">Strategy5 PnL</th>
	    </tr>
  </thead>
  <tfoot>
        <tr>
            <th></th>
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

</div>


<tags:scripts/>
<tags:javascriptVariables />
</body>
</html>