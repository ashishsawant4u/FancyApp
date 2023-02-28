$( document ).ready(function() {
	
	getWicketNotSummaryReport();
	
});

function getWicketNotSummaryReport()
{
	if ($("body").hasClass("wktnot-report-page")) 
	{
		
			let inning = window.location.pathname.split("/").pop();
			
			if(null === inning)
			{
				inning = 0;
			}
			
			$('#wktNotReportSummaryDataTable').dataTable().fnDestroy();
		
			
			var wktNotSummartyReportTable = $('#wktNotReportSummaryDataTable').DataTable({
			    ajax: wktNotSummartyReportDataUrl+'/'+inning,
				dataSrc:"",
				ordering: false,
				pagingType: "full",
				columnDefs: [
					 {
						"createdCell": function (td, cellData, rowData, row, col) {
		                    if(cellData >= 0)
							{
						        $(td).addClass('bg-success text-white');
						    }
							else
							{
								$(td).addClass('bg-danger text-white');
							}
		                },
						targets: [2,3,4,5,6]
				  },
				],	
				columns: [
					  { data: 'matchTitle' },
					  { data: 'matchDate' },
					  { data: 'strategy1PnL' },
					  { data: 'strategy2PnL' },
					  { data: 'strategy3PnL' },
					  { data: 'strategy4PnL' },
					  { data: 'strategy5PnL' }
				
				],
				footerCallback: function( tfoot, data, start, end, display ) {
			        var api = this.api();
			        $(api.column(2).footer()).html(
			            api.column(2).data().reduce(function ( a, b ) {
			                return a + b;
			            }, 0)
			        );
					$(api.column(3).footer()).html(
			            api.column(3).data().reduce(function ( a, b ) {
			                return a + b;
			            }, 0)
			        );
					$(api.column(4).footer()).html(
			            api.column(4).data().reduce(function ( a, b ) {
			                return a + b;
			            }, 0)
			        );
					$(api.column(5).footer()).html(
			            api.column(5).data().reduce(function ( a, b ) {
			                return a + b;
			            }, 0)
			        );
					$(api.column(6).footer()).html(
			            api.column(6).data().reduce(function ( a, b ) {
			                return a + b;
			            }, 0)
			        );
			    }
			});
		}	
}