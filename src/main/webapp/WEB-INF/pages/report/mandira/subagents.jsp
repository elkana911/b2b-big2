<%@ include file="/common/taglibs.jsp" %>

<head>
    <title><fmt:message key="report.topup.title"/></title>

<link rel="stylesheet" href="<c:url value="/styles/jquery-ui.css"/>" />
    <meta name="menu" content="ReportMenu"/>
</head>

<style>
	.table-striped > tbody > tr:nth-child(2n+1) > td, .table-striped > tbody > tr:nth-child(2n+1) > th {
		background-color: #E8E8E8;
	}
	
	.ui-datepicker {  
		width: 216px;  
		height: auto;  
		margin: 5px auto 0;  
		font: 9pt Verdana, sans-serif;  
		-webkit-box-shadow: 0px 0px 10px 0px rgba(0, 0, 0, .2);  
		-moz-box-shadow: 0px 0px 10px 0px rgba(0, 0, 0, .2);  
		box-shadow: 0px 0px 10px 0px rgba(0, 0, 0, .2);  
	}  

</style>

<c:if test="${not empty searchError}">
    <div class="alert alert-danger alert-dismissable">
        <a href="#" data-dismiss="alert" class="close">&times;</a>
        <c:out value="${searchError}"/>
    </div>
</c:if>

<form method="get" action="${ctx}/report/topuphist" id="searchForm" class="form-inline">
	<legend class="accordion-heading">
	    <a data-toggle="collapse" href="#collapse-searchform">
	    
			<c:choose>
			  <c:when test="${empty virtualaccount}">
		    	<fmt:message key="report.topup.heading"/>
			  </c:when>
			  <c:otherwise>
		    	<fmt:message key="report.topup.headingVA">
					<fmt:param value="${virtualaccount}" />
		    	</fmt:message>
			  </c:otherwise>
			</c:choose>
			
	    </a>
	
	</legend>

	<div id="collapse-searchform"  class="accordion-body collapse in" style="padding-left: 0px">
		<div class="row">	
		    <div class="col-xs-6 col-sm-3 col-md-2">
					<label for="fromdate"><fmt:message key="report.topup.fromDate"/></label>	
					<input type="text" name="from" id="fromdate" value="${fromdate}" class="form-control" required>
			</div>
	        <div class="col-xs-6 col-sm-3 col-md-2">
					  <label for="todate"><fmt:message key="report.topup.toDate"/></label>
					  <input type="text" name="to" id="todate" value="${todate}" class="form-control" required>
			</div>
			<div class="col-xs-6 col-sm-3 col-md-2">
					<label class="control-label"><fmt:message key="report.topup.status"/></label>
					<select name="status" class="form-control">
					 	<option value="" ${fn:containsIgnoreCase(status, '') ? 'selected' : ''}>ALL</option>
					 	<option value="NOTPAID" ${fn:containsIgnoreCase(status, 'NOTPAID') ? 'selected' : ''}>NOT PAID</option>
					 	<option value="PROCESSED" ${fn:containsIgnoreCase(status, 'PROCESSED') ? 'selected' : ''}>PROCESSED</option>
					</select>
			</div>
			<div class="col-xs-6 col-sm-3 col-md-2">
				<label class="control-label">&nbsp;</label>
				<button class="form-control btn btn-primary" type="submit" style="font-weight:bolder; text-transform: uppercase;">Search</button>
			</div>
		</div>

	</div>
</form>

<div class="row" style="margin-top: 1em">
    <display:table name="topuplist" cellspacing="0" cellpadding="0" requestURI=""
    			decorator="com.big.web.b2b_big2.flight.decorator.ReportTopUpDecorator"
    			defaultsort="1" id="agents" pagesize="25" class="table table-condensed table-striped table-hover" export="true">
        <display:column property="rowNum" sortable="true" titleKey="report.no" media="all"/>    
        <display:column property="bankCode" sortable="true" titleKey="report.topup.bankCode" style="width: 15%" autolink="true"
                        media="all"/>  
        <display:column property="bankName" escapeXml="true" sortable="true" titleKey="report.topup.bank" style="width: 25%"
                        media="all"/>
        <display:column property="lastUpdate" escapeXml="true" sortable="true" titleKey="report.topup.createdDate"
                        style="width: 17%" media="all"/>    
        <display:column property="timeToPay" sortable="true" titleKey="report.topup.timeToPay" style="width: 17%" autolink="true"
                        media="all"/>
        <display:column property="amountToPaid" sortable="true" titleKey="report.topup.amount" style="width: 15%" autolink="true"
                        media="all" decorator="com.big.web.b2b_big2.flight.decorator.CurrencyColumnDecorator"/>  
<%--         <display:column property="feeAdmin" sortable="true" titleKey="report.topup.adminFee" style="width: 17%" autolink="true" --%>
<%--                         media="all"/>   --%>
<%--         <display:column property="total" sortable="true" titleKey="report.topup.total" style="width: 15%" autolink="true" --%>
<%--                         media="all"/>   --%>
        <display:column property="status" sortable="true" titleKey="report.topup.status" style="width: 13%" autolink="true"
                         media="all" decorator="com.big.web.b2b_big2.flight.decorator.TopUpStatusColumnDecorator" />
		
        <display:setProperty name="paging.banner.item_name"><fmt:message key="report.topup"/></display:setProperty>
        <display:setProperty name="paging.banner.items_name"><fmt:message key="report.topups"/></display:setProperty>

<%--         <display:setProperty name="export.excel.filename" value="TopUpList.xls"/> --%>
<%--         <display:setProperty name="export.csv.filename" value="TopUpList.csv"/> --%>
<%--         <display:setProperty name="export.pdf.filename" value="TopUpList.pdf"/> --%>
                                              
	</display:table>    
</div>

<script type="text/javascript" src="<c:url value="/scripts/jquery-ui.js"/>"></script>
<script type="text/javascript">

var checkin = $('#fromdate').datepicker(
		{
			numberOfMonths : 2,
			showButtonPanel : true,
			dateFormat : "dd/mm/yy",
			//showAnim: "drop",
			//showOptions: {direction: "up"},
			currentText : "Bulan ini",
			closeText : "Tutup",
			monthNames : [ "Januari", "Februari", "Maret", "April",
					"Mei", "Juni", "July", "Agustus", "September",
					"Oktober", "November", "Desember" ],
			dayNamesMin : [ "M", "S", "S", "R", "K", "J", "S" ],
			showOtherMonths : true,
			
			onClose: function(){
				if ($("#travelRadio2").is(":checked")){
					$('#return1').focus();
				}
			}
		});

var checkout = $('#todate').datepicker(
		{
			numberOfMonths : 2,
			showButtonPanel : true,
			dateFormat : "dd/mm/yy",
			//showAnim: "drop",
			//showOptions: {direction: "up"},
			currentText : "Bulan ini",
			closeText : "Tutup",
			monthNames : [ "Januari", "Februari", "Maret", "April",
					"Mei", "Juni", "July", "Agustus", "September",
					"Oktober", "November", "Desember" ],
			dayNamesMin : [ "M", "S", "S", "R", "K", "J", "S" ],
			showOtherMonths : true
		}).on('changeDate', function(ev) {
	//$('#checkout1')[0].focus();
});

</script>