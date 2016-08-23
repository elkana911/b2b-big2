<%@ include file="/common/taglibs.jsp"%>

<head>
    <title><fmt:message key="topup.title"/></title>
    <meta name="menu" content="TopUpMenu"/>
</head>

<div class="row">
	<div class="col-sm-8">
	    <h2><fmt:message key="topup.heading"/></h2>
	</div>
</div>

<div class="row">
<div class="col-sm-5">

    <form:form commandName="maTopUp" method="post" action="../agent/topup" 
        onsubmit="return validateForm(this)" cssClass="well" style="padding:2em" autocomplete="false" >
        
        <div class="row">
	        <spring:bind path="maTopUp.paymentMethod">
	        <div class="form-group${(not empty status.errorMessage) ? ' has-error' : ''}">
	        </spring:bind>
		        <appfuse:label key="topup.paymentMethod" styleClass="control-label"/>
				<form:select path="paymentMethod" cssClass="form-control input-sm"> 
					<form:option value="0" label="--- Select Payment ---" />                                 
				    <c:forEach items="${paymentMethods}" var="item">
				        <form:option value="${item.id}">${item.nama}</form:option>
				    </c:forEach>
				</form:select>
	        </div>
	    </div>
        
        <div id ="row_atm" class="row">
	        <div class="controls form-group form-inline">
		        <appfuse:label key="topup.bank" styleClass="control-label"/>
	        	<form:select path="bankCodeATM" cssClass="form-control input-sm">
				    <c:forEach items="${paymentBanks}" var="item">
				        <form:option value="${item.code}">${item.aka}</form:option>
				    </c:forEach>
	  			</form:select>   
	        </div>
        </div>
        
        <div id="row_va" class="row">
	        <div class="controls form-group form-inline">
		        <appfuse:label key="topup.bank" styleClass="control-label"/>
	        	<form:select path="bankCodeVA" cssClass="form-control input-sm">
				    <c:forEach items="${paymentVA}" var="item">
				        <form:option value="${item.code}">${item.aka}</form:option>
				    </c:forEach>
	  			</form:select>   
	        </div>
        </div>

		<div id="row_amount" class="row">
        <spring:bind path="maTopUp.amount">
        <div class="form-group${(not empty status.errorMessage) ? ' has-error' : ''}">
        </spring:bind>
            <appfuse:label key="topup.amount" styleClass="control-label"/>
            Rp.&nbsp;
            <form:input path="amount" cssClass="form-control" style="width:auto;display:inline;text-align: right;" onkeypress="return /\d/.test(String.fromCharCode(((event||window.event).which||(event||window.event).which)));" />
            .000 
            <form:errors path="amount" cssClass="help-block" />
        </div>
        </div>
        
        <div class="form-group">
            <button type="submit" name="kirim" class="btn btn-primary" onclick="bCancel=false">
                <i class="icon-upload icon-white"></i> <fmt:message key="button.submit"/>
            </button>
        </div>
    </form:form>
</div>
</div>

<c:set var="scripts" scope="request">
<v:javascript formName="topUp" staticJavascript="false"/>
<script type="text/javascript" src="<c:url value="/scripts/validator.jsp"/>"></script>

<script type="text/javascript">

	$("#paymentMethod").change(function(){
	    $( "select option:selected").each(function(){
	    	
	        if($(this).attr("value")== 0){
	            $("#row_atm").hide();
	            $("#row_va").hide();
	            $("#row_amount").hide();
	        }
	        if($(this).attr("value")== 1){
	            $("#row_atm").show();
	            $("#row_va").hide();
	            $("#row_amount").show();
	        }
	        if($(this).attr("value")== 2){
	            $("#row_atm").hide();
	            $("#row_va").show();
	            $("#row_amount").hide();
	        }
	    });
	}).change();

function validateForm(form) {  
	var method = document.getElementById("paymentMethod");
	if (method.value == 0){
		alert("Please select payment method !");
		method.focus();
		return false;
	}

	if (method.value == 0){
		var amt = document.getElementById("amount");
		if (amt.value.trim().length < 1 || !amt.value.trim().match(/^\d+$/)){
			alert("Invalid amount !");
			amt.focus();
			return false;
		}

		var r = confirm("TOPUP for " + amt.value.trim() + ".000 IDR ?");
		if (r == false) {
		    return false;
		} 
		
	}


    return true;
}

</script>

</c:set>