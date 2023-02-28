$( document ).ready(function() {
	
	layBetsSummary();
	
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
				ordering: false,
				pagingType: "full",
				info:false,
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

