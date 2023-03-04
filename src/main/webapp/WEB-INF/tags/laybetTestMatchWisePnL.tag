<%@ tag language="java" pageEncoding="ISO-8859-1"%>


<div class="modal fade" id="layBetMatchwisePnLModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog modal-xl">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLabel">Match Wise Profit & Loss</h5>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      </div>
      <div class="modal-body">
       			<table class="table table-sm table-hover text-center table-bordered pt-1 w-100" id="layBetTesterMatchWisePnLDataTable">
					<thead style="position: sticky;top: 0" class="bg-light">
					    <tr>
					      <th scope="col">Match Title</th>
					      <th scope="col">Total PnL</th>
					    </tr>
				  </thead>
				</table>
      </div>
    </div>
  </div>
</div>