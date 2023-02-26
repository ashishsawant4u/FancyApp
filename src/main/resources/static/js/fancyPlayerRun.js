$( document ).ready(function() {
	
	
	$('#matchesDropdown').on('change', function() {
	  	getFancyPlayerRun(this.value);
		getFancyPlayerRunAnalysis(this.value);
	});
	
	$("#matchesDropdown").val($("#matchesDropdown option:eq(1)").val());
	$("#matchesDropdown").trigger('change');
	
	
	$('#batsmanScoresForFancyLink').on('click', function() {
		let matchId = $('#matchesDropdown').val();
		let url = 'http://localhost:7041/fancyapp/batsmanscore/data/'+matchId;
		window.open(url , '_blank');
	});
	
});

function getFancyPlayerRun(matchId)
{
	if ($("body").hasClass("fancy-player-run-page")) 
	{
			
			$('#fancyRunDataTable').dataTable().fnDestroy();
		
			
			var fancyPlayerRunTable = $('#fancyRunDataTable').DataTable({
			    ajax: 'http://localhost:7041/fancyapp/fancyplayer/data/'+matchId,
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
				$('#matchDateDisplay').text(matchDate.getDate()+'-'+ matchMonth +"-"+matchDate.getFullYear());

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
			
			$.ajax({
		            type: "GET",
		            url: 'http://localhost:7041/fancyapp/fancyplayer/analyzeMatch/'+matchId,
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
										+ '<td>' + response[i].startegy3BetPnL+ '</td>' + '</tr>';
										
							
							 $('#fancyRunAnalysisDataTable tbody').append(trStr);
							
							startegy1BetPnLTotal = startegy1BetPnLTotal + response[i].startegy1BetPnL;
							startegy2BetPnLTotal = startegy2BetPnLTotal + response[i].startegy2BetPnL;
							startegy3BetPnLTotal = startegy3BetPnLTotal + response[i].startegy3BetPnL;
						}
						
						let startegy1BetPnLTotalClass = (startegy1BetPnLTotal < 0) ? 'bg-danger' : 'bg-success';
						let startegy2BetPnLTotalClass = (startegy2BetPnLTotal < 0) ? 'bg-danger' : 'bg-success';
						let startegy3BetPnLTotalClass = (startegy3BetPnLTotal < 0) ? 'bg-danger' : 'bg-success';
						
						let trStr = '<tr>'+'<td></td>'
										+ '<td></td>'+ '<td></td>'+ '<td></td>'
										+ '<td class="fw-bold">Total</td>'
										+ '<td class="text-white '+startegy1BetPnLTotalClass+'">' + startegy1BetPnLTotal + '</td>'
										+ '<td class="text-white '+startegy2BetPnLTotalClass+'">' + startegy2BetPnLTotal + '</td>'
										+ '<td class="text-white '+startegy3BetPnLTotalClass+'">' + startegy3BetPnLTotal + '</td>' + '</tr>';
										
							 $('#fancyRunAnalysisDataTable tbody').append(trStr);
		            }
		 		});
	}
			
				
}