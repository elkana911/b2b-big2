<%@ include file="/common/taglibs.jsp"%>

<head>
	<title><fmt:message key="menu.search" />&nbsp;<fmt:message
		key="menu.flight" /></title>
    <meta name="menu" content="TicketMenu"/>
	<link rel="stylesheet" href="<c:url value="/styles/jquery-ui.css"/>" />
	<link rel="stylesheet" href="<c:url value="/styles/style.css"/>" />
	<link rel="stylesheet" href="<c:url value="/styles/citiesPicker.css"/>" />

<style>

.flightInfoCell{
 	font-size:11px;  	
}

.flightCell{
	border-width: 1px;border-style:solid;border-color: #D7D4D4;
	text-align: center;vertical-align: middle;
}

.flightCellAirline{
	border-width: 1px;border-style:solid;border-color: #D7D4D4;
	text-align: center;vertical-align: middle;
	width: 2%;
	font-weight: bolder;
}

.flightCellDepart{
	background-color: rgba(155, 155, 155, 1);
	color: #FFF;
	text-shadow:  1px 1px #000;
	width: 1%;
}

.flightCellArrival{
	background-color: rgba(155, 155, 155, 1);
	color: #FFF;
	text-shadow:  1px 1px #000;
	width: 1%;
}

.sortable{
	text-align:center;
}

.departureTitle{
	font-weight: bold;
}

.returnTitle{
	font-weight: bold;
}

.small-cell{
	padding-left: 3px;
	padding-right: 3px;
}

.ui-menu .ui-menu-item a{
    background: rgb(252, 252, 242);
    font-size:11px;
    margin: 2px;
}


#progressbar .ui-progressbar-value {
	background-color: #ccc;
}
#ui-datepicker-div{
	font-size: smaller;
}

.summary-ticket{
	background-color:#FFF;
}
.summary-ticket tr td {
	font-size: 11px;
	color:#000;
	font-weight:normal;
	border: thin solid #CCC;
	text-align: center;	
}
.summary-ticket .ticketdtl-table tr .summary-header {
	font-weight: bold;
	font-size: 12px;
	background-color: #FFC;
}

.summary-title{
	color:#000;
	font-size: 16px;
	font-weight: bold;
}

.modul-header {
	font-weight: bold;
}

.seatEconomy{
	background-color: rgba(99, 182, 239, 0.21);
	border-radius: 4px;
}
.seatBusiness{
	background-color: rgba(26, 242, 7, 0.21);
	border-radius: 4px;
}
.seatFirst{
	background-color: rgba(239, 99, 99, 0.21);
	border-radius: 4px;
}

#returnFlights .odd .flightCell{
	background-color: rgb(195, 252, 205);
}

#returnFlights .odd .flightCellDepart{
	background-color: rgba(155, 155, 155, 1);
	color: white;
}

#returnFlights .odd .flightCellArrival{
	background-color: rgba(155, 155, 155, 1);
	color: white;
}

/* #returnFlights .even .flightCell{ */
/* 	background-color: rgb(222, 254, 225); */
/* } */

#departFlights .odd .flightCell{
	background-color: rgb(219, 233, 255);
}

#departFlights .odd .flightCellDepart{
	background-color: rgba(155, 155, 155, 1);
	color: white;
}
#departFlights .odd .flightCellArrival{
	background-color: rgba(155, 155, 155, 1);
	color: white;
}

/* #departFlights .even .flightCell{ */
/* 	background-color: rgb(208, 218, 242); */
/* } */

#departFlights > thead >tr>th {
	font-weight: bold;
	color: blue;
	text-align: center;
}

#departFlights.fixed {
	table-layout:fixed;
}

#returnFlights > thead >tr>th {
	font-weight: bold;
	color: blue;
	text-align: center;
}

.fontLion{
	color: rgb(93, 8, 8);
}

.fontSriwijaya{
	color: rgb(27, 25, 106);
}
.fontCitilink{
	color: rgb(19, 70, 17);
}
.fontGaruda{
	color: rgb(44, 58, 112);
}
.fontExpress{
	color: rgb(147, 82, 71);
}
.fontSusi{
	color: rgb(78, 79, 90);
}

.cheapest{
	font-weight: bolder;
}

#tableContact tr td {
	vertical-align: top;
}
.submenu
{
	background: #fff;
	position: absolute;
	top: -1em;
	left: -1em;
	z-index: 100;
	width: 235px;
	display: none;
	margin-left: 10px;
	margin-top:-1em;
	padding: 1em 0 0;
	border-radius: 6px;
	box-shadow: 0 2px 8px rgba(0, 0, 0, 0.45);
	font-size:smaller;
	
	max-height:300px;
	overflow: auto;
}
.speedButton{
	font-size: smaller;
}
</style>

<meta name="menu" content="FlightMenu" />
</head>

<c:set var="hintsearchfrom">
	<fmt:message key="hint.search.flight.from" />
</c:set>
<c:set var="hintsearchto">
	<fmt:message key="hint.search.flight.to" />
</c:set>
<c:set var="hintsearchtoday">
	<fmt:message key="hint.search.flight.today" />
</c:set>
<c:set var="hintsearchtomm">
	<fmt:message key="hint.search.flight.tommorrow" />
</c:set>
<c:set var="errorsagreeterms">
	<fmt:message key="errors.agree.terms" />
</c:set>
<c:set var="errordepartflight">
	<fmt:message key="flight.error.choosedepartflight" />
</c:set>
<c:set var="errorreturnflight">
	<fmt:message key="flight.error.choosereturnflight" />
</c:set>
<c:set var="popuptitle">
	<fmt:message key="menu.ticketDetails" />
</c:set>

<input id="errorsagreeterms" type="hidden" value="${errorsagreeterms}" />
<input id="errordepartflight" type="hidden" value="${errordepartflight}" />
<input id="errorreturnflight" type="hidden" value="${errorreturnflight}" />

<!-- <a id="OpenDialog" href="#">Click here to open dialog</a> -->
<div id="popupFare" title="${popuptitle}">
    <p></p>
</div>

<script type="text/javascript" src="<c:url value="/scripts/jquery.auderoContextMenu.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/scripts/jquery.contextMenu.js"/>"></script>
<script type="text/javascript">
	var dewasa, anak, bayi, ccust;

	function showDewasa(index, ttl, fullName, idCard, bDay, nationality, ssr){
		$('input[name="adult[' + index + '].fullName"]').val(fullName);
		
		$('input[name="adult[' + index + '].idCard"]').val(idCard == 'null' ? '' : idCard);

		$('input[name="adult[' + index + '].birthday"]').val(bDay == 'null' ? '' : bDay);
		
		if (ttl != 'null')
		$('select[name="adult[' + index + '].title"]').val(ttl);
		if (nationality != 'null')
		$('select[name="adult[' + index + '].countryCode"]').val(nationality);
		if (ssr != 'null')
		$('select[name="adult[' + index + '].specialRequest"]').val(ssr);
	}
	function showAnak(index, ttl, fullName, idCard, bDay, ssr){
		$('input[name="child[' + index + '].fullName"]').val(fullName);
		
		$('input[name="child[' + index + '].idCard"]').val(idCard == 'null' ? '' : idCard);

		$('input[name="child[' + index + '].birthday"]').val(bDay == 'null' ? '' : bDay);

		if (ttl != 'null')
		$('select[name="child[' + index + '].title"]').val(ttl);
		if (ssr != 'null')
		$('select[name="child[' + index + '].specialRequest"]').val(ssr);
	}
	function showBayi(index, ttl, fullName, idCard, bDay, ssr){
		$('input[name="infant[' + index + '].fullName"]').val(fullName);
		
		$('input[name="infant[' + index + '].idCard"]').val(idCard == 'null' ? '' : idCard);

		$('input[name="infant[' + index + '].birthday"]').val(bDay == 'null' ? '' : bDay);

		if (ttl != 'null')
		$('select[name="infant[' + index + '].title"]').val(ttl);
		if (ssr != 'null')
		$('select[name="infant[' + index + '].specialRequest"]').val(ssr);
	}
	function showCCust(fullName, phone1, phone2){
		$('input[name="customer.fullName"]').val(fullName);
		
		$('input[name="customer.phone1"]').val(phone1 == 'null' ? '' : phone1);
					   
		$('input[name="customer.phone2"]').val(phone2 == 'null' ? '' : phone2);

	}
	
	if (${maBookingForm.searchForm.adult} > 0)
	$.getJSON("${pageContext.request.contextPath}/getFormAdults")
		.done(function (data){
	    	dewasa = data;
    		<c:forEach items="${maBookingForm.adult}" varStatus="loop">    		
		    	var _item = '<ul>';
	    		$.each(dewasa, function (i, value){
	    			_item += "<li><span onclick=\"javascript:showDewasa(" + ${loop.count-1} 
	    				  + ",'" + value.title + "'" 
	    				  + ",'" + value.fullName + "'" 
	    				  + ",'" + value.idCard + "'" 
	    				  + ",'" + value.birthday + "'" 
	    				  + ",'" + value.countryCode + "'" 
	    				  + ",'" + value.specialRequest + "');\">" 
	    				  + value.fullName + "</span></li>";
	    		});
	    		_item += '</ul>';	    		
	
	    		$("#myMenuDewasa${loop.count-1}").html(_item);
			</c:forEach>
	});

	if (${maBookingForm.searchForm.children} > 0)
	$.getJSON("${pageContext.request.contextPath}/getFormChildren")
		.done(function (data){
	    	anak = data;
    		<c:forEach items="${maBookingForm.child}" varStatus="loop">    		
		    	var _item = '<ul>';
	    		$.each(anak, function (i, value){
	    			_item += "<li><span onclick=\"javascript:showAnak(" + ${loop.count-1} 
  				  + ",'" + value.title + "'" 
  				  + ",'" + value.fullName + "'" 
  				  + ",'" + value.idCard + "'" 
  				  + ",'" + value.birthday + "'" 
  				  + ",'" + value.specialRequest + "');\">" 
  				  + value.fullName + "</span></li>";
	    			
	    			//_item += "<li><span onclick=\"javascript:showAnak(" + ${loop.count-1} + ",'" + value.uid + "');\">" + value.fullName + "</span></li>";
	    		});
	    		_item += '</ul>';	    		
	
	    		$("#myMenuAnak${loop.count-1}").html(_item);
			</c:forEach>
	});

	if (${maBookingForm.searchForm.infants} > 0)
	$.getJSON("${pageContext.request.contextPath}/getFormInfants")
		.done(function (data){
	    	bayi = data;
    		<c:forEach items="${maBookingForm.infant}" varStatus="loop">    		
		    	var _item = '<ul>';
	    		$.each(bayi, function (i, value){
	    			_item += "<li><span onclick=\"javascript:showBayi(" + ${loop.count-1} 
  				  + ",'" + value.title + "'" 
  				  + ",'" + value.fullName + "'" 
  				  + ",'" + value.idCard + "'" 
  				  + ",'" + value.birthday + "'" 
  				  + ",'" + value.specialRequest + "');\">" 
  				  + value.fullName + "</span></li>";
	    			
//	    			_item += "<li><span onclick=\"javascript:showBayi(" + ${loop.count-1} + ",'" + value.uid + "');\">" + value.fullName + "</span></li>";
	    		});
	    		_item += '</ul>';	    		
	
	    		$("#myMenuBayi${loop.count-1}").html(_item);
			</c:forEach>

	});
	
	$.getJSON("${pageContext.request.contextPath}/getFormCCust")
	.done(function (data){
    	ccust = data;
    	var _item = '<ul>';
   		$.each(ccust, function (i, value){
   			_item += "<li><span onclick=\"javascript:showCCust('" + value.fullName + "'" 
			  + ",'" + value.phone1 + "'" 
			  + ",'" + value.phone2 + "');\">" 
			  + value.fullName + "</span></li>";
   		});
   		_item += '</ul>';	    		

   		$("#myMenuCCust").html(_item);

	});

</script>

<form:form commandName="maBookingForm" method="post" autocomplete="off"
	action="../b2b/search"
		id="formsearchb2b"
	onsubmit="return validateFlightSearch(this)"
	style="padding-bottom:0px">

    <legend class="accordion-heading">
        <a data-toggle="collapse" href="#collapse-searchform"><fmt:message key="menu.ticketSearchAgent"/></a>
    </legend>
	<div id="collapse-searchform"  class="row well accordion-body collapse in" style="padding-left: 0px">
		<%@ include file="../flight/inner/searchform-top.jsp" %>
	</div>

	<div id="listflights">
	<%@ include file="../flight/inner/list-depart-return-b2b.jsp" %>
	</div>	
	
	<%@ include file="../flight/inner/contacts-b2b.jsp" %>
			
</form:form>

<c:forEach items="${maBookingForm.adult}" varStatus="loop">
	<div id="myMenuDewasa${loop.count-1}" class="submenu">
	</div>
</c:forEach>
<c:forEach items="${maBookingForm.adult}" varStatus="loop">
	<div id="myMenuAnak${loop.count-1}" class="submenu">
	</div>
</c:forEach>
<c:forEach items="${maBookingForm.adult}" varStatus="loop">
	<div id="myMenuBayi${loop.count-1}" class="submenu">
	</div>
</c:forEach>
<div id="myMenuCCust" class="submenu">
</div>

<c:set var="scripts" scope="request">

	<v:javascript formName="flightSearch" staticJavascript="false" />
	<script type="text/javascript" src="<c:url value="/scripts/validator.jsp"/>"></script>
	<script type="text/javascript" src="<c:url value="/scripts/jquery-ui.js"/>"></script>

	<%@ include file="/scripts/session.js"%>
	<%@ include file="/scripts/flightcommon.js"%>
	<%@ include file="/scripts/flightsearchb2b.js"%>
	<script type="text/javascript" src="<c:url value="/scripts/jquery-fsearchb2b.js"/>"></script>
 	<script type="text/javascript" src="<c:url value="/scripts/jquery.citiesPicker.js"/>"></script>
	<script type="text/javascript">
		$('#departCity1').citiesPicker();
		$('#destCity1').citiesPicker();

		$("#popupFare").dialog({autoOpen : false, modal : false, show : "blind", hide : "blind"});

		$( "#progressbar" ).progressbar({
			value: false
		});
				
		$('#selectAll1').click(function(event){
			if (this.checked){
				$(".cbairline").attr('checked', true);
			}else{
				$(".cbairline").attr('checked', false);
			}
		});

		<c:forEach items="${maBookingForm.adult}" varStatus="loop">
			$('#speedAdultButton${loop.count-1}').auderoContextMenu({
		         idMenu: "myMenuDewasa${loop.count-1}",
		         bindLeftClick: true
		      });
		</c:forEach>
		

		$('#speedCCustButton').auderoContextMenu({
	         idMenu: "myMenuCCust",
	         bindLeftClick: true
	      });
	</script>
	 	 
</c:set>
