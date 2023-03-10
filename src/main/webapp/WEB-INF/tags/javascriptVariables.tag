<%@ tag language="java" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  


<c:url scope="session" var="fancyRecordsByMatchUrl" value="/fancyplayer/data/"/>    
<c:url scope="session" var="batsmanScoreByMatchPageUrl" value="/batsmanscore/data/"/> 
<c:url scope="session" var="analyzeFancyByMatchUrl" value="/fancyplayer/analyzeMatch/"/> 
<c:url scope="session" var="batsmanScoreByMatchDataUrl" value="/batsmanscore/get/"/> 
<c:url scope="session" var="wktNotSummartyReportDataUrl" value="/wktnot-report/matchpnl"/> 
<c:url scope="session" var="wktNotSummartyReportPageUrl" value="/wktnot-report/summary"/>
<c:url scope="session" var="betsCounterDataUrl" value="/betcounter/tracker"/>
<c:url scope="session" var="betsCounterPageUrl" value="/betcounter/summary"/>
<c:url scope="session" var="layBetsSummaryDataUrl" value="/laybets/analyze"/>
<c:url scope="session" var="layBetsSummaryPageUrl" value="/laybets/summary"/>
<c:url scope="session" var="scoreRangeAnalysisDataUrl" value="/oldscorebaords/analyzescorerange"/>
<c:url scope="session" var="scoreAnalysisDataUrl" value="/oldscorebaords/analyzescore"/>
<c:url scope="session" var="tournamentOutCountPageUrl" value="/oldscorebaords/summary"/>
<c:url scope="session" var="laybetTestingAnalysisUrl" value="/laybettest/analyze"/>
<c:url scope="session" var="layBetTesterPageUrl" value="/laybettest/summary"/>
<c:url scope="session" var="laybetTestingMatchwisePnLAnalysisUrl" value="/laybettest/pnlstats"/>
<c:url scope="session" var="laybetTestingMatchwisePnLAnalysisSummaryUrl" value="/laybettest/pnlstats/summary"/>
<c:url scope="session" var="layBetsCountWiseDataUrl" value="/laybets/coutwise-bets"/>

<script type="text/javascript">
	var fancyRecordsByMatchUrl = "${fancyRecordsByMatchUrl}";
	var batsmanScoreByMatchPageUrl = "${batsmanScoreByMatchPageUrl}";
	var analyzeFancyByMatchUrl = "${analyzeFancyByMatchUrl}";
	var batsmanScoreByMatchDataUrl = "${batsmanScoreByMatchDataUrl}";
	var wktNotSummartyReportDataUrl = "${wktNotSummartyReportDataUrl}";
	var wktNotSummartyReportPageUrl = "${wktNotSummartyReportPageUrl}";
	var betsCounterDataUrl = "${betsCounterDataUrl}";
	var betsCounterPageUrl = "${betsCounterPageUrl}";
	var layBetsSummaryDataUrl = "${layBetsSummaryDataUrl}";
	var layBetsCountWiseDataUrl = "${layBetsCountWiseDataUrl}";
	var layBetsSummaryPageUrl = "${layBetsSummaryPageUrl}";
	var scoreRangeAnalysisDataUrl = "${scoreRangeAnalysisDataUrl}";
	var scoreAnalysisDataUrl = "${scoreAnalysisDataUrl}";
	var tournamentOutCountPageUrl = "${tournamentOutCountPageUrl}";
	var laybetTestingAnalysisUrl = "${laybetTestingAnalysisUrl}";
	var layBetTesterPageUrl = "${layBetTesterPageUrl}";
	var laybetTestingMatchwisePnLAnalysisUrl = "${laybetTestingMatchwisePnLAnalysisUrl}";
	var laybetTestingMatchwisePnLAnalysisSummaryUrl = "${laybetTestingMatchwisePnLAnalysisSummaryUrl}";
</script>