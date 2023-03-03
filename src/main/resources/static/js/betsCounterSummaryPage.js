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
		
		
		betsCounterSummaryDataTable.on('xhr',function(){
				let bestBetCount = getBestBetCount(betsCounterSummaryDataTable.ajax.json());
				$('#bestBetCaption-inn').html('Best Bet Count is <span class="badge bg-dark">'+bestBetCount.bet + '</span>  Win Rate <span class="badge bg-success">'+bestBetCount.winrate+ '</span>');
			//	//$('#betsCounterSummaryDataTable tr').find('td:first').filter(function () { return ($(this).text() === bestBetCount.bet) }).closest('tr').addClass('bg-success text-white');
			});		
		
		betCounterInning1();
		betCounterInning2();
		
	}	
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
			
			
			betsCounterSummaryDataTableInn1.on('xhr',function(){
				let bestBetCount = getBestBetCount(betsCounterSummaryDataTableInn1.ajax.json());
				$('#bestBetCaption-inn1').html('Best Bet Count is <span class="badge bg-dark">'+bestBetCount.bet + '</span>  Win Rate <span class="badge bg-success">'+bestBetCount.winrate+ '</span>');
				//$('#betsCounterSummaryDataTableInn1 tr').find('td:first').filter(function () { return ($(this).text() === bestBetCount.bet) }).closest('tr').addClass('bg-success text-white');
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
			
			
			betsCounterSummaryDataTableInn2.on('xhr',function(){
				let bestBetCount = getBestBetCount(betsCounterSummaryDataTableInn2.ajax.json());
				$('#bestBetCaption-inn2').html('Best Bet Count is <span class="badge bg-dark">'+bestBetCount.bet + '</span>  Win Rate <span class="badge bg-success">'+bestBetCount.winrate+ '</span>');
				//$('#betsCounterSummaryDataTableInn2 tr').find('td:first').filter(function () { return ($(this).text() === bestBetCount.bet) }).closest('tr').addClass('bg-success text-white');
			});	
}


function getBestBetCount(json)
{
	var diff = 0;
	var bestBet = 0;
	var betBestPass = 0;
	for (let bet of json.data) 
	{
	    var currentBetCount = bet.betCount;
	    var pass = bet.passCounter;
	    var allOthers = bet.failCounter;
	    for (let d of json.data) 
		{
	        if (d.betCount !== currentBetCount) 
			{
	            allOthers = allOthers + d.betCounter;
		    }
	    }
	    if ((pass - allOthers) > diff) 
		{
	        diff = (pass - allOthers);
	        bestBet = currentBetCount;
			betBestPass = pass;
	    }
	}
	
	let otherFails = (betBestPass / (betBestPass-diff));
	
	let bestBetObj = {
		bet : bestBet,
		diff : diff,
		winrate : (otherFails * 100).toFixed(2) + '%'
	}
	
	if(diff === 0)
	{
		return { bet : 0, diff : 0,winrate : 0 + '%' }
	}
	
	return bestBetObj;
}