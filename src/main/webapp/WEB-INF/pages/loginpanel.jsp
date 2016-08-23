<div class="row" id="rowConfirmDelete" <c:if test="${empty errPwd}"> style="display: none" </c:if>>
<!-- <div class="row" id="rowConfirmDelete" style="display: none"> -->
	<div class="col-xs-12 col-sm-6">
		<div class="form-group">
		    <p><fmt:message key="user.confirmPassword"/></p>
		
			<input id="passwd" name="passwd" class="form-control" type="password" value="1" />
		    <button type="submit" class="btn btn-default" name="delete" onclick="bCancel=true">
		        <i class="icon-ok"></i> <fmt:message key="button.submit"/>
		    </button>
            <span class="text-danger">${errPwd}</span>
		</div>
	</div>
</div>

<script type="text/javascript">
	$("#submitDelete").click(function(event){
		$(':password').val('');
		$("#rowConfirmDelete").show();	
	});
</script>