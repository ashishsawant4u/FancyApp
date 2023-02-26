<%@ tag language="java" pageEncoding="ISO-8859-1"%>


<button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#wktNotFancyRecordsModal">
  Wkt Not Fancy Records
</button>






<div class="modal fade" id="wktNotFancyRecordsModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog modal-xl">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLabel">Wicket Not Fancy Records</h5>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      </div>
      <div class="modal-body">
       			<table class="table table-sm table-hover text-center table-bordered pt-1" id="fancyRunDataTable" style="width:100%">
					<thead style="position: sticky;top: 0" class="bg-light">
					    <tr>
					      <th scope="col">Player Name</th>
					      <th scope="col">Inning</th>
					      <th scope="col">Player Run</th>
					      <th scope="col">Lay Run</th>
					      <th scope="col">Lay Odd</th>
					      <th scope="col">Back Run</th>
					      <th scope="col">Back Odd</th>
					    </tr>
				  </thead>
				</table> 
      </div>
    </div>
  </div>
</div>