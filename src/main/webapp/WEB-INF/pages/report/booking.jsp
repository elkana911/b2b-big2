<%@ include file="/common/taglibs.jsp" %>

<head>
    <title><fmt:message key="report.booking.title"/></title>

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

<form method="get" action="${ctx}/report/bookinghist" id="searchForm" class="form-inline">
	<legend class="accordion-heading">
	    <a data-toggle="collapse" href="#collapse-searchform"><fmt:message key="report.booking.heading"/></a>
	</legend>
	<div id="collapse-searchform"  class="accordion-body collapse in" style="padding-left: 0px">
		<div class="row">	
		   	<div class="col-xs-6 col-sm-3 col-md-2">
				<label for="fromdate"><fmt:message key="report.booking.fromDate"/></label>	
				<input type="text" name="from" id="fromdate" value="${fromdate}" class="form-control" required>
			</div>
		    <div class="col-xs-6 col-sm-3 col-md-2">
				  <label for="todate"><fmt:message key="report.booking.toDate"/></label>
				  <input type="text" name="to" id="todate" value="${todate}" class="form-control" required>
			</div>
			<div class="col-xs-6 col-sm-3 col-md-2">
					<label class="control-label"><fmt:message key="report.booking.airline"/></label>
					<select name="airline" class="form-control" style="width:14em; display: inline;"  >
					 	<option value="all" <c:if test="${empty param.airline || param.airline == 'all'})"> 'selected' </c:if> >ALL</option>
		                <c:forEach items="${airlines}" var="airline">
		                	<option value="${airline.code}" ${fn:contains(param.airline, airline.code) ? 'selected' : ''}>${airline.name}</option>
	                	</c:forEach>
	            	</select>
			</div>
			<div class="col-xs-6 col-sm-3 col-md-2">
					<label class="control-label"><fmt:message key="report.booking.status"/></label>
					<select name="status" class="form-control" >
						<option value="">ALL</option>
						<option value="HOLD" <c:if test="${status == 'HOLD'}"> <c:out value= "selected=selected" /></c:if>>HOLD</option>
						<option value="ISSUED" <c:if test="${status == 'ISSUED'}"> <c:out value= "selected=selected"/></c:if>>ISSUED</option>
						<option value="CANCEL" <c:if test="${status == 'CANCEL'}"> <c:out value= "selected=selected"/></c:if>>CANCEL</option>
						<option value="EXPIRED" <c:if test="${status == 'EXPIRED'}"> <c:out value= "selected=selected"/></c:if>>EXPIRED</option>
					</select>
			</div>
			<div class="col-xs-6 col-sm-3 col-md-2">
				<label class="control-label">&nbsp;</label>
				<button class="form-control btn btn-primary" type="submit" style="font-weight:bolder; text-transform: uppercase;">SEARCH</button>
			</div>
		</div>
	</div>
</form>

<div class="row" style="margin-top: 1em">
	<div class="col-sm-12">
        <display:table name="bookinglist" id="ticket" cellspacing="0" cellpadding="0" requestURI=""
        			decorator="com.big.web.b2b_big2.decorator.ReportDecorator"
                   defaultsort="1" pagesize="25" class="table table-condensed table-bordered table-striped table-hover" export="true">
        <display:column property="rowNum" sortable="true" titleKey="report.no" media="all"/>    
        <display:column sortable="true" titleKey="report.booking.bookingCode" 
                        media="all" style="font-family: monospace;font-size:medium;">
		    <a href="<c:url value='/flight/b2b/booking'/>/<c:out value='${ticket.id}'/>" > 
			    <c:out value='${ticket.code}'/>
		    </a>
        </display:column>
        <display:column property="status" sortable="true" titleKey="report.booking.status" 
                        media="all" decorator="com.big.web.b2b_big2.flight.decorator.BookStatusColumnDecorator"/>
        <display:column property="airlines_iata" sortable="true" titleKey="report.booking.airline"
                        media="all" style="font-family: monospace;"/>    
        <display:column property="departure_date" sortable="true" titleKey="report.booking.departDate"
                        media="all" format="{0,date,dd-MMM-yyyy}" style="font-family: monospace"/>  
        <display:column property="route" sortable="true" titleKey="report.booking.route" 
                        media="all" style="font-family: monospace;"/>  
        <display:column sortable="true" titleKey="report.booking.commission" 
                        media="all" style="font-family: monospace;text-align: right">
		       	<c:choose>
				   <c:when test="${ticket.status == 'CANCELED' || ticket.status == 'EXPIRED'}">
				   	<span style="text-decoration:line-through;"><fmt:formatNumber value="${ticket.detailTransaction.after_comm}" minFractionDigits="0"/></span>
				   </c:when>
				   <c:otherwise>
				   	<fmt:formatNumber value="${ticket.detailTransaction.after_comm}" minFractionDigits="0"/>
				   </c:otherwise>
				</c:choose>
        </display:column>  
        <display:column sortable="true" titleKey="report.booking.ntsa" 
                        media="all" style="font-family: monospace;text-align: right">
		       	<c:choose>
				   <c:when test="${ticket.status == 'CANCELED' || ticket.status == 'EXPIRED'}">
				   	<span style="text-decoration:line-through;"><fmt:formatNumber value="${ticket.detailTransaction.after_nta}" minFractionDigits="0"/></span>
				   </c:when>
				   <c:otherwise>
				   	<fmt:formatNumber value="${ticket.detailTransaction.after_nta}" minFractionDigits="0"/>
				   </c:otherwise>
				</c:choose>
        </display:column>  
        <display:column sortable="true" titleKey="report.booking.totalFare" 
                        media="all" style="font-family: monospace;text-align: right">
		       	<c:choose>
				   <c:when test="${ticket.status == 'CANCELED' || ticket.status == 'EXPIRED'}">
				   	<span style="text-decoration:line-through;"><fmt:formatNumber value="${ticket.detailTransaction.after_amount}" minFractionDigits="0"/></span>
				   </c:when>
				   <c:otherwise>
				   	<fmt:formatNumber value="${ticket.detailTransaction.after_amount}" minFractionDigits="0"/>
				   </c:otherwise>
				</c:choose>
        </display:column>  
        <display:column property="timeToPay" sortable="true" titleKey="report.booking.timeToPay" 
                        media="all" format="{0,date,dd-MMM-yyyy HH.mm.ss}" style="font-family: monospace;"/>  

        <display:setProperty name="paging.banner.item_name"><fmt:message key="report.booking"/></display:setProperty>
        <display:setProperty name="paging.banner.items_name"><fmt:message key="report.bookings"/></display:setProperty>

<%--         <display:setProperty name="export.excel.filename" value="BookingList.xls"/> --%>
<%--         <display:setProperty name="export.csv.filename" value="BookingList.csv"/> --%>
<%--         <display:setProperty name="export.pdf.filename" value="BookingList.pdf"/> --%>
                                              
		</display:table>    
	</div>
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

