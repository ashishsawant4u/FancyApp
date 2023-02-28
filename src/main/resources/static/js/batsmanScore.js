$( document ).ready(function() {
	
	let matchId = window.location.pathname.split("/").pop();
	getBatsmanScoreByMatch(matchId);
	
});

function getBatsmanScoreByMatch(matchId)
{
	if ($("body").hasClass("batsman-score-page")) 
	{
			
			$('#batsmanScoreDataTable').dataTable().fnDestroy();
		
			
			var batsmanScoreTable = $('#batsmanScoreDataTable').DataTable({
			    ajax: batsmanScoreByMatchDataUrl+matchId,
				dataSrc:"",
				ordering: false,
				pagingType: "full",
				columns: [
					  { data: 'playerName' },
					  { data: 'inning' },
					  { data: 'playerRun' }
				
				]
			});
			
			batsmanScoreTable.on( 'xhr', function () {
			    var json = batsmanScoreTable.ajax.json();
				    //console.log( json.data.length +' row(s) were loaded' );
				$('#matchTitleForBatsmanScore').text(json.data[0].matchTitle);

			});
		}	
}