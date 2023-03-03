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
		});
	
});

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
					  { data: 'matchId' },
					  { data: 'matchTitle' },
					  { data: 'inning' },
					  { data: 'batsman' },
					  { data: 'batsmanScore' },
					  { data: 'layBet' },
					  { data: 'betPnL' }
				
				],
				footerCallback: function( tfoot, data, start, end, display ) {
			        var api = this.api();
			        $(api.column(6).footer()).html(
			            api.column(6).data().reduce(function ( a, b ) {
			                return a + b;
			            }, 0)
			        );
			    }
			});
			
			layBetTesterDataTable.on('xhr',function(){
					let jsonData = layBetTesterDataTable.ajax.json();
					getSummaryCounts(jsonData);
			});
			
			
		}	
}

function getSummaryCounts(jsonData)
{
	var failCount = jsonData.data.filter(function (el)
    {
      return el.betPnL === -100;
    });
	var passCount = jsonData.data.filter(function (el)
    {
      return el.betPnL === 100;
    });
	let winRate = (passCount.length*100)/jsonData.data.length;
	let failRate = (failCount.length*100)/jsonData.data.length;
	
	$('#layBetsTestCountsDiv').html('Total bets <span class="badge bg-dark">'+jsonData.data.length
								+'</span> Pass <span class="badge bg-dark">'+passCount.length+
									'</span> Fail <span class="badge bg-dark">'+failCount.length
									+'</span> WinRate <span class="badge bg-dark">'
									+winRate.toFixed(2)+
									'%</span> FailRate <span class="badge bg-dark">'+failRate.toFixed(2)+'%</span>');
}

function isNull(value)
{
	if(typeof value === "undefined" || value === null || value === ""  || value.length==0){
		return true;
	}else{
		return false;
	}
}