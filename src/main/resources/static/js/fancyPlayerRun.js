$( document ).ready(function() {
	
	
	$('#matchesDropdown').on('change', function() {
	  	getFancyPlayerRun(this.value);
		getFancyPlayerRunAnalysis(this.value);
	});
	
	$("#matchesDropdown").val($("#matchesDropdown option:eq(1)").val());
	$("#matchesDropdown").trigger('change');
	
	
	let inning = window.location.pathname.split("/").pop();
	
	$('#batsmanScoresForFancyLink').on('click', function() {
		let matchId = $('#matchesDropdown').val();
		let url = batsmanScoreByMatchPageUrl+matchId;
		window.open(url , '_blank');
	});
	
	$('#wktNotSummaryLink').on('click', function() {
		let url = wktNotSummartyReportPageUrl+'/'+inning;
		window.open(url , '_blank');
	});
	
	$('#betsCounterPageLink').on('click', function() {
		let url = betsCounterPageUrl;
		window.open(url , '_blank');
	});
	
	$('#layBetSummaryPageLink').on('click', function() {
		let url = layBetsSummaryPageUrl+'/'+inning;
		window.open(url , '_blank');
	});
	
	$('#tournamentOutCountPageLink').on('click', function() {
		let url = tournamentOutCountPageUrl;
		window.open(url , '_blank');
	});
	
	
	
	
	
});

function getFancyPlayerRun(matchId)
{
	if ($("body").hasClass("fancy-player-run-page")) 
	{
			
			$('#fancyRunDataTable').dataTable().fnDestroy();
		
			
			var fancyPlayerRunTable = $('#fancyRunDataTable').DataTable({
			    ajax: fancyRecordsByMatchUrl+matchId,
				dataSrc:"",
				ordering: false,
				pagingType: "full",
				columnDefs: [
				  {
						"createdCell": function (td, cellData, rowData, row, col) {
							$(td).addClass('bg-lay');
		                },
						targets: [3,4]
				  },
				  {
						"createdCell": function (td, cellData, rowData, row, col) {
							$(td).addClass('bg-back');
		                },
						targets: [5,6]
				  }
				],
				columns: [
					  { data: 'playerName' },
					  { data: 'inning' },
					  { data: 'playerRun' },
					  { data: 'layRun' },
			 		  { data: 'layOdd' },
					  { data: 'backRun' },
					  { data: 'backOdd' },
				
				]
			});
			
			fancyPlayerRunTable.on( 'xhr', function () {
			    var json = fancyPlayerRunTable.ajax.json();
				    //console.log( json.data.length +' row(s) were loaded' );

				let matchDate = new Date(json.data[0].createDateTime.substring(0,10));
				let matchMonth = matchDate.getMonth()+1;
				//$('#matchDateDisplay').text(matchDate.getDate()+'-'+ matchMonth +"-"+matchDate.getFullYear());

			});
		}	
}

function getFancyPlayerRunAnalysis(matchId)
{
	if ($("body").hasClass("fancy-player-run-page")) 
	{
			
			$('#fancyRunAnalysisDataTable tbody').empty();
			
			let startegy1BetPnLTotal = 0;
			let startegy2BetPnLTotal = 0;
			let startegy3BetPnLTotal = 0;
			let startegy4BetPnLTotal = 0;
			let startegy5BetPnLTotal = 0;
			
			let inning = window.location.pathname.split("/").pop();
			
			if(null === inning)
			{
				inning = 0;
			}
			
			$.ajax({
		            type: "GET",
		            url: analyzeFancyByMatchUrl+matchId+'/'+inning,
		            contentType: "application/json;",
		            success: function (response) {
		                console.log(response);
						for(i=0;i<response.length;i++)
						{
							let laybets = response[i].layBets.sort(function(a, b) {
											  return a - b;
											});
							let trStr = '<tr>'+'<td>' + response[i].playerName+ '</td>'
										+ '<td>' + response[i].inning + '</td>'
										+ '<td>' + JSON.stringify(laybets) + '</td>'
										+ '<td>' + response[i].totalBets+ '</td>'
										+ '<td>' + response[i].batsmanScored+ '</td>'
										+ '<td>' + response[i].startegy1BetPnL+ '</td>'
										+ '<td>' + response[i].startegy2BetPnL+ '</td>'
										+ '<td>' + response[i].startegy3BetPnL+ '</td>'
										+ '<td>' + response[i].startegy4BetPnL+ '</td>'
										+ '<td>' + response[i].startegy5BetPnL+ '</td>' + '</tr>';
										
							
							 $('#fancyRunAnalysisDataTable tbody').append(trStr);
							
							startegy1BetPnLTotal = startegy1BetPnLTotal + response[i].startegy1BetPnL;
							startegy2BetPnLTotal = startegy2BetPnLTotal + response[i].startegy2BetPnL;
							startegy3BetPnLTotal = startegy3BetPnLTotal + response[i].startegy3BetPnL;
							startegy4BetPnLTotal = startegy4BetPnLTotal + response[i].startegy4BetPnL;
							startegy5BetPnLTotal = startegy5BetPnLTotal + response[i].startegy5BetPnL;
						}
						
						
						let trStr = '<tr>'+'<td></td>'
										+ '<td></td>'+ '<td></td>'+ '<td></td>'
										+ '<td class="fw-bold">Total</td>'
										+ '<td class="text-white '+getPnLClass(startegy1BetPnLTotal)+'">' + startegy1BetPnLTotal + '</td>'
										+ '<td class="text-white '+getPnLClass(startegy2BetPnLTotal)+'">' + startegy2BetPnLTotal + '</td>'
										+ '<td class="text-white '+getPnLClass(startegy3BetPnLTotal)+'">' + startegy3BetPnLTotal + '</td>'
										+ '<td class="text-white '+getPnLClass(startegy4BetPnLTotal)+'">' + startegy4BetPnLTotal + '</td>'
										+ '<td class="text-white '+getPnLClass(startegy5BetPnLTotal)+'">' + startegy5BetPnLTotal + '</td>' + '</tr>';
										
							 $('#fancyRunAnalysisDataTable tbody').append(trStr);
		            }
		 		});
	}
			
				
}

function getPnLClass(pnlTotal)
{
	return (pnlTotal < 0) ? 'bg-danger' : 'bg-success';
}