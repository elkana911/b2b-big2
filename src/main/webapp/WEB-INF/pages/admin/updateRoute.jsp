<%@ include file="/common/taglibs.jsp"%>

<head>
	<title><fmt:message key="menu.admin.update.route" />&nbsp;<fmt:message
		key="menu.flight" /></title>
    <meta name="menu" content="AdminMenu"/>
	<link rel="stylesheet" href="<c:url value="/styles/jquery-ui.css"/>" />
	<link rel="stylesheet" href="<c:url value="/styles/style.css"/>" />
	<link rel="stylesheet" href="<c:url value="/styles/citiesPicker.css"/>" />
	<link rel="stylesheet" href="<c:url value="/styles/jquery-contextMenu.css"/>" />

<style>

.flightInfoCell{
 	font-size:11px;  
 	margin-top: 4px;	
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

<script type="text/javascript" src="<c:url value="/scripts/jquery.contextMenu.js"/>"></script>
<script type="text/javascript">

</script>

<div class="col-sm-10">
<form:form commandName="maSearchForm" method="post" autocomplete="off"
	action="updateroute"
		id="formsearchb2b"
		onsubmit="return validateFlightSearch(this)"
	style="padding-bottom:0px">

    <legend class="accordion-heading">
        <a data-toggle="collapse" href="#collapse-searchform"><fmt:message key="menu.admin.update.route"/></a>
    </legend>

	<div id="collapse-searchform"  class="row well accordion-body collapse in" style="padding-left: 0px">
		<div class="col-sm-8">
					<spring:bind path="tripMode">
					<div class="col-sm-2 form-group small-cell" style="font-size: small;">
					</spring:bind>
						<form:radiobutton path="tripMode" value="0" id="travelRadio1" onclick="javascript:travelModeCheck();"/><fmt:message key="flight.oneWayTrip" />&nbsp;&nbsp;&nbsp;&nbsp;
						<br><form:radiobutton path="tripMode" value="1" id="travelRadio2" onclick="javascript:travelModeCheck();"/><fmt:message key="flight.returnTrip" />
					</div>
	
					<div class="col-sm-4 form-group small-cell">				
						<div class="row">
							<spring:bind path="departCity">
							<div class="col-sm-12 form-group${(not empty status.errorMessage) ? ' has-error' : ''}">
							</spring:bind>
								<appfuse:label key="flight.cityFrom" styleClass="control-label" />
								<form:input path="departCity" cssClass="form-control input-sm" 
									id="departCity1" placeholder="${hintsearchfrom}" value="${departCity}" />
								<!-- adanya value utk autofill memungkinkan aq quick tes,jd langsung pencet search  -->
								<form:errors path="departCity" cssClass="help-block" />
							</div>
						</div>
							
						<div class="row">
							<spring:bind path="destCity">
							<div class="col-sm-12 form-group${(not empty status.errorMessage) ? ' has-error' : ''}">
							</spring:bind>
								<appfuse:label key="flight.destination" styleClass="control-label" />
								<form:input path="destCity" cssClass="form-control input-sm" 
									id="destCity1" placeholder="${hintsearchto}" value="${toCity}" />
								<form:errors path="destCity" cssClass="help-block" />
							</div>
						</div>
					</div>
					<div class="col-sm-2 form-group small-cell">
						<div class="row">
							<spring:bind path="departDate">
							<div class="col-sm-12 form-group${(not empty status.errorMessage) ? ' has-error' : ''}">
							</spring:bind>
								<appfuse:label key="flight.dateDepart" styleClass="control-label" />
								<form:input path="departDate" name="dateDepart" id="depart1" placeholder="${hintsearchtoday}"
									value="${departDate}" cssClass="form-control input-sm" />
								<form:errors path="departDate" cssClass="help-block" />
							</div>
						</div>
						<div class="row small-cell">
							<spring:bind path="returnDate">
							<div id="returnGroup" <c:if test="${tripMode != 1}">style="display:none"</c:if> class="col-sm-12 form-group${(not empty status.errorMessage) ? ' has-error' : ''}">
							</spring:bind>
								<appfuse:label key="flight.dateReturn" styleClass="control-label" />
								<form:input path="returnDate" id="return1" placeholder="${hintsearchtoday}" value="${returnDate}" cssClass="form-control input-sm" />
								<form:errors path="returnDate" cssClass="help-block" />
							</div>
						</div>
					</div>	
						<div class="row">

						</div> <!-- row -->
						<div class="row" style="text-align: right; margin-right: 2em" >
						<button type="submit" id="updateButton" class="btn btn-primary text-right"
							onclick="bCancel=false; splash();" style="width:200px; margin-top: 1.3em;font-weight: bolder;text-transform: uppercase;">
							<i class="icon-upload icon-white"></i>
							<fmt:message key="button.update" />
						</button>
						</div>
		</div>	<!-- div-col-8 -->
	
		<div class="col-sm-4" style="border-left: thin solid #3A87AD;font-size: smaller;">
			<div style="font-size: medium;font-style: italic;font-weight: bold;">Airline</div>
			<div>
				<input type="checkbox" id="selectAll1" checked="checked"><fmt:message key="button.selectall" /></input>
			</div>
			<div class="row">
			<div class="col-lg-6">
			 	<div>
		<!-- 	 	cbairline kubuat sbg trik utk selectall -->
				<label class="checkbox-inline">
			        <form:checkbox path="garuda" cssClass="cbairline"/>
			       	<fmt:message key="airline.garuda" />
			    </label>
			    </div>
			 	<div>
				<label class="checkbox-inline">
		        	<form:checkbox path="citilink" cssClass="cbairline"/>
		        	<fmt:message key="airline.citilink" />
		    	</label>
		    	</div>
			 	<div>
				<label class="checkbox-inline">
		        	<form:checkbox path="airasia" cssClass="cbairline"/>
		        	<fmt:message key="airline.airasia" />
		    	</label>
		    	</div>
			 	<div>
				<label class="checkbox-inline">
		        	<form:checkbox path="lion" cssClass="cbairline"/>
		        	<fmt:message key="airline.lion" />
		    	</label>
		    	</div>
			 	<div>
				<label class="checkbox-inline">
		        	<form:checkbox path="wings" cssClass="cbairline"/>
		        	<fmt:message key="airline.wings" />
		    	</label>
		    	</div>
			 	<div class="span2">
				<label class="checkbox-inline">
		        	<form:checkbox path="batik" cssClass="cbairline"/>
		        	<fmt:message key="airline.batik" />
		    	</label>
		    	</div>
		    	<div>
				<label class="checkbox-inline">
		        	<form:checkbox path="express" cssClass="cbairline"/>
		        	<fmt:message key="airline.express" />
		    	</label>
		    	</div>
			</div>
			
			<div class="col-lg-6">
		    	<div>
				<label class="checkbox-inline">
		        	<form:checkbox path="sriwijaya" cssClass="cbairline"/>
		        	<fmt:message key="airline.sriwijaya" />
		    	</label>
		    	</div>
		    	<div>
				<label class="checkbox-inline">
		        	<form:checkbox path="nam" cssClass="cbairline"/>
		        	<fmt:message key="airline.nam" />
		    	</label>
		    	</div>
		    	
		    	<div>
				<label class="checkbox-inline">
		        	<form:checkbox path="kalstar" cssClass="cbairline"/>
		        	<fmt:message key="airline.kalstar" />
		    	</label>
		    	</div>
		    	
		    	<div>
				<label class="checkbox-inline">
		        	<form:checkbox path="trigana" cssClass="cbairline"/>
		        	<fmt:message key="airline.trigana" />
		    	</label>
		    	</div>
	<!-- 	    	<div>    	 -->
	<!-- 			<label class="checkbox-inline"> -->
	<%-- 	        	<form:checkbox path="searchForm.susi" cssClass="cbairline"/> --%>
	<%-- 	        	<fmt:message key="airline.susi" /> --%>
	<!-- 	    	</label> -->
	<!-- 			</div> -->
		    	<div>    	
				<label class="checkbox-inline">
		        	<form:checkbox path="malindo" cssClass="cbairline"/>
		        	<fmt:message key="airline.malindo" />
		    	</label>
				</div>
		    	<div>    	
				<label class="checkbox-inline">
		        	<form:checkbox path="aviastar" cssClass="cbairline"/>
		        	<fmt:message key="airline.aviastar" />
		    	</label>
				</div>
			</div>
			</div>
		</div>
	</div>

</form:form>
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
		$('input[id=selectAll1]').attr('checked', true).triggerHandler('click');

	</script>
	 	 
</c:set>
