$( document ).ready(function() {
	
	$('.lay-bet-test-dropdowns').on('change', function() {
			  	let tournamentId = $('#layBetTestTournamentsDropdown').val();
				let inning = $('#layBettestInningsDropdown').val();
				
				if(!isNull(tournamentId) && !isNull(inning))
				{
					getLaybetTestingSummaryPage(tournamentId,inning);
					
				}
				else
				{
					alert('Please select all fields');
				}
				
				$('#layBetTesterMatchwisePnlModalBtn').addClass('d-none');
				$('#layBetTestSummaryStatsTable').addClass('d-none');
		});
		
		$('#layBetTesterMatchwisePnlModalBtn').on('click', function() {
			
			$('#layBetMatchwisePnLModal').modal('show'); 
		});
		
		
	
});

function showLayBetTestSummryStats()
{
	$('#layBetTestSummaryStatsTable').empty();
	
	$.ajax({  
				url: laybetTestingMatchwisePnLAnalysisSummaryUrl,  
				type: 'GET',  
				success: function(item) 
				{  
						var trHTML = 
						'<tr><td class="fw-bold">Total Bets</td><td>'+item.totalBets+'</td></tr>'
						+'<tr><td class="fw-bold">Pass</td><td>'+item.passCount+'</td></tr>'
						+'<tr><td class="fw-bold">Fail</td><td>'+item.failCount+'</td></tr>'
						+'<tr><td class="fw-bold">WinRate</td><td>'+item.winRate+'%</td></tr>'
						+'<tr><td class="fw-bold">FailRate</td><td>'+item.failRate+'%</td></tr>'
						+'<tr><td class="fw-bold">Max Profit In Match</td><td>'+item.maxProfitInMatch+'</td></tr>'
						+'<tr><td class="fw-bold">Max Loss In Match</td><td>'+item.maxLossInMatch+'</td></tr>';
		        
				        $('#layBetTestSummaryStatsTable').append(trHTML);
						$('#layBetTestSummaryStatsTable').removeClass('d-none');
			    }  
		}); 
}



function  getLaybetTestingMatchwisePnL()
{
	$('#layBetTesterMatchWisePnLDataTable').dataTable().fnDestroy();
		
			
			var layBetTesterDataTable = $('#layBetTesterMatchWisePnLDataTable').DataTable({
			    ajax: laybetTestingMatchwisePnLAnalysisUrl,
				dataSrc:"",
				ordering: false,
				pagingType: "full",
				columns: [
					  { data: 'matchTitle' },
					  { data: 'totalPnL' }
				
				],
				initComplete :  function(settings, json) {
						$('#layBetTesterMatchwisePnlModalBtn').removeClass('d-none');
						showLayBetTestSummryStats();
			  	}
			});
			
			
			
			
}

function getLaybetTestingSummaryPage(tournamentId,inning)
{
	if ($("body").hasClass("lay-bet-tester-page")) 
	{
			
			$('#layBetTesterDataTable').dataTable().fnDestroy();
		
			
			var layBetTesterDataTable = $('#layBetTesterDataTable').DataTable({
			    ajax: laybetTestingAnalysisUrl+'/'+tournamentId+'/'+inning,
				dataSrc:"",
				ordering: false,
				pagingType: "full",
				columns: [
					  //{ data: 'matchId' },
					  { data: 'matchTitle' },
					  { data: 'inning' },
					  { data: 'batsman' },
					  { data: 'batsmanScore' },
					  { data: 'layBet' },
					  { data: 'betPnL' }
				
				],
				footerCallback: function( tfoot, data, start, end, display ) {
			        var api = this.api();
			        $(api.column(5).footer()).html(
			            api.column(5).data().reduce(function ( a, b ) {
			                return a + b;
			            }, 0)
			        );
			    },
				initComplete :  function(settings, json) {
			    		getLaybetTestingMatchwisePnL();
			  	}
			});
			
		
			
		}	
}




function isNull(value)
{
	if(typeof value === "undefined" || value === null || value === ""  || value.length==0){
		return true;
	}else{
		return false;
	}
}