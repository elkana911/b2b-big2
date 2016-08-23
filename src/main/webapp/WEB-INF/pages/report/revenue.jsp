<%@ include file="/common/taglibs.jsp" %>

<head>
    <title><fmt:message key="report.revenue.title"/></title>

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

<form method="get" action="${ctx}/report/revenue" id="searchForm" class="form-inline">
	<legend class="accordion-heading">
	    <a data-toggle="collapse" href="#collapse-searchform"><fmt:message key="report.revenue.heading"/></a>
	</legend>
	<div id="collapse-searchform"  class="row accordion-body collapse in" style="padding-left: 0px">	
	   	<div class="form-group col-xs-6 col-sm-2">
			<label for="fromdate"><fmt:message key="report.revenue.fromDate"/></label>	
			<input type="text" name="from" id="fromdate" value="${fromdate}" class="form-control" required>
		</div>
	    <div class="form-group col-xs-6 col-sm-2">
			  <label for="todate"><fmt:message key="report.revenue.toDate"/></label>
			  <input type="text" name="to" id="todate" value="${todate}" class="form-control" required>
		</div>
		<div class="form-group col-sm-1">
			<label>&nbsp;</label>
			<button class="btn btn-primary" type="submit" style="font-weight:bolder; text-transform: uppercase;">SEARCH</button>
		</div>
	</div>
</form>

<div class="row" style="margin-top: 1em">
        <display:table name="revenueList" cellspacing="0" cellpadding="0" requestURI=""
	    			decorator="com.big.web.b2b_big2.decorator.ReportDecorator"
                   defaultsort="1" id="agents" pagesize="25" class="table table-condensed table-striped table-hover" export="true">
        <display:column property="rowNum" sortable="true" titleKey="report.no" media="all"/>    
        <display:column property="airline" escapeXml="true" sortable="true" titleKey="report.revenue.airline" style="width: 18%"
                        media="all"/>
        <display:column property="bookingCode" escapeXml="true" sortable="true" titleKey="report.revenue.bookingCode"
                        style="width: 17%" media="all"/>    
        <display:column property="dateIssued" sortable="true" titleKey="report.revenue.issuedDate" style="width: 15%" autolink="true"
                        media="all"/>
        <display:column property="commFare" sortable="true" titleKey="report.revenue.fareComm" style="width: 15%" autolink="true"
                        media="all"/>  
        <display:column property="commInsurance" sortable="true" titleKey="report.revenue.insComm" style="width: 15%" autolink="true"
                        media="all"/>  
        <display:column property="feeService" sortable="true" titleKey="report.revenue.serviceFee" style="width: 15%" autolink="true"
                        media="all"/>  
        <display:column property="total" sortable="true" titleKey="report.revenue.total" style="width: 10%" autolink="true"
                        media="all"/>  

        <display:setProperty name="paging.banner.item_name"><fmt:message key="report.revenue"/></display:setProperty>
        <display:setProperty name="paging.banner.items_name"><fmt:message key="report.revenues"/></display:setProperty>

        <display:setProperty name="export.excel.filename" value="RevenueList.xls"/>
        <display:setProperty name="export.csv.filename" value="RevenueList.csv"/>
        <display:setProperty name="export.pdf.filename" value="RevenueList.pdf"/>
                                              
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
					$('#todate').focus();
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