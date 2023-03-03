$( document ).ready(function() {
	
	getOldScoreRangeSummary();
	
});

function getOldScoreRangeSummary()
{
	if ($("body").hasClass("old-score-page")) 
	{
		
		$('.old-score-dropdowns').on('change', function() {
			  	let tournamentId = $('#oldTournamentsDropdown').val();
				let inning = $('#inningsDropdown').val();
				let scoreRange = $('#rangeDropdown').val();
				
				if(!isNull(tournamentId) && !isNull(inning) && !isNull(scoreRange))
				{
					scoreRangeOutCountAnalysis(tournamentId,inning,scoreRange);
					scoreOutCountAnalysis(tournamentId,inning,scoreRange);
				}
				else
				{
					alert('Please select all fields');
				}
		});
		
			
	}	
}

function scoreRangeOutCountAnalysis(tournamentId,inning,scoreRange)
{
			let matchId = tournamentId;
			
			$('#scoreRanegAnalysisDataTable').dataTable().fnDestroy();
		
			
			var scoreRanegAnalysisDataTable = $('#scoreRanegAnalysisDataTable').DataTable({
			    ajax: scoreRangeAnalysisDataUrl+'/'+matchId+'/'+inning+'/'+scoreRange,
				dataSrc:"",
				ordering: false,
				pagingType: "full",
				searching: false,
				paging: false,
				info:false,
				columnDefs: [
					 {
						"render": function ( data, type, row ) {
		                    return data + '%';
		                },
						targets: [2]
				  },
				],	
				columns: [
					  { data: 'scoreRange' },
					  { data: 'outCount' },
					  { data: 'outRate' }
				
				]
			});
}

function scoreOutCountAnalysis(tournamentId,inning,scoreRange)
{
			let matchId = tournamentId;
			
			$('#scoreAnalysisDataTable').dataTable().fnDestroy();
		
			
			var scoreAnalysisDataTable = $('#scoreAnalysisDataTable').DataTable({
			    ajax: scoreAnalysisDataUrl+'/'+matchId+'/'+inning+'/'+scoreRange,
				dataSrc:"",
				ordering: false,
				pagingType: "full",
				searching: false,
				paging: false,
				info:false,
				columnDefs: [
					 {
						"render": function ( data, type, row ) {
		                    return data + '%';
		                },
						targets: [2]
				  },
				],	
				columns: [
					  { data: 'scoreRange' },
					  { data: 'outCount' },
					  { data: 'outRate' }
				
				]
			});
}

function isNull(value)
{
	if(typeof value === "undefined" || value === null || value === ""  || value.length==0){
		return true;
	}else{
		return false;
	}
}