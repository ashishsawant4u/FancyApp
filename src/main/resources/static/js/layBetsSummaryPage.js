$( document ).ready(function() {
	
	layBetsSummary();
	
	$('.countwise-lay-bets-dropdowns').on('change', function() {
			  	let betCount = $('#layBetsCountDropdown').val();
				let inning = $('#layBetsInningsDropdown').val();
				
				if(!isNull(betCount) && !isNull(inning))
				{
					countWiseLayBets(inning,betCount);
					
				}
				else
				{
					alert('Please select all fields');
				}
		});
	
});

function layBetsSummary()
{
	if ($("body").hasClass("laybets-summary-page")) 
	{
		
			let inning = window.location.pathname.split("/").pop();
			
			if(null === inning)
			{
				inning = 0;
			}
			
			$('#layBetsSummaryDataTable').dataTable().fnDestroy();
		
			
			var layBetsSummaryDataTable = $('#layBetsSummaryDataTable').DataTable({
			    ajax: layBetsSummaryDataUrl+'/'+inning,
				dataSrc:"",
				ordering: true,
				pagingType: "full",
				info:true,
				columnDefs: [
					 {
						"render": function ( data, type, row ) {
		                    return data + '%';
		                },
						targets: [4]
				  },
				],	
				columns: [
					  { data: 'bet' },
					  { data: 'betCounter' },
					  { data: 'passCounter' },
					  { data: 'failCounter' },
					  { data: 'winRate' }
				
				]
			});
			
			
		}
}

function countWiseLayBets(inning,betCount)
{
			$('#layBetsCouintWiseDataTable').dataTable().fnDestroy();
		
			
			var layBetsCouintWiseDataTable = $('#layBetsCouintWiseDataTable').DataTable({
			    ajax: layBetsCountWiseDataUrl+'/'+inning+'/'+betCount,
				dataSrc:"",
				ordering: true,
				pagingType: "full",
				info:true,
				columnDefs: [
					 {
						"render": function ( data, type, row ) {
							 let output='';
							 $.each(data, function(index,item) {
			                    
			                    output+= data[index]+' ';
			                  });
							
		                    return output;
		                },
						targets: [0]
				  }
				],	
				columns: [
					  { data: 'layBets' }
				
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