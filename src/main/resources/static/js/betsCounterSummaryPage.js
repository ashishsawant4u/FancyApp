$( document ).ready(function() {
	
	betsCounterSummary();
	
});

function betsCounterSummary()
{
	if ($("body").hasClass("betscounter-page")) 
	{
			
			$('#betsCounterSummaryDataTable').dataTable().fnDestroy();
		
			
			var betsCounterSummaryDataTable = $('#betsCounterSummaryDataTable').DataTable({
			    ajax: betsCounterDataUrl+'/0',
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
						targets: [4]
				  },
				],	
				columns: [
					  { data: 'betCount' },
					  { data: 'betCounter' },
					  { data: 'passCounter' },
					  { data: 'failCounter' },
					  { data: 'winRate' }
				
				]
			});
		}	
		
		betCounterInning1();
		betCounterInning2();
}

function betCounterInning1()
{
	$('#betsCounterSummaryDataTableInn1').dataTable().fnDestroy();
		
			
			var betsCounterSummaryDataTableInn1 = $('#betsCounterSummaryDataTableInn1').DataTable({
			    ajax: betsCounterDataUrl+'/1',
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
						targets: [4]
				  },
				],	
				columns: [
					  { data: 'betCount' },
					  { data: 'betCounter' },
					  { data: 'passCounter' },
					  { data: 'failCounter' },
					  { data: 'winRate' }
				
				]
			});
}

function betCounterInning2()
{
	$('#betsCounterSummaryDataTableInn2').dataTable().fnDestroy();
		
			
			var betsCounterSummaryDataTableInn2 = $('#betsCounterSummaryDataTableInn2').DataTable({
			    ajax: betsCounterDataUrl+'/2',
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
						targets: [4]
				  },
				],	
				columns: [
					  { data: 'betCount' },
					  { data: 'betCounter' },
					  { data: 'passCounter' },
					  { data: 'failCounter' },
					  { data: 'winRate' }
				
				]
			});
}