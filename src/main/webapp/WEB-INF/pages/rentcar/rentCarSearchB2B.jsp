<%@ include file="/common/taglibs.jsp"%>

<head>
	<title><fmt:message key="menu.search" />&nbsp;<fmt:message
		key="menu.hotel" /></title>
    <meta name="menu" content="TicketMenu"/>
	<link rel="stylesheet" href="<c:url value="/styles/jquery-ui.css"/>" />
	<link rel="stylesheet" href="<c:url value="/styles/style.css"/>" />
	<link rel="stylesheet" href="<c:url value="/styles/citiesPicker.css"/>" />

</head>


<form:form commandName="maBookingForm" method="post" autocomplete="off"
	action="/hotel/b2b/search"
		id="formsearchb2b"
	onsubmit="return validateHotelSearch(this)"
	style="padding-bottom:0px">

    <legend class="accordion-heading">
        <a data-toggle="collapse" href="#collapse-searchform"><fmt:message key="menu.ticketSearchAccommodation"/></a>
    </legend>
	<div id="collapse-searchform"  class="row well accordion-body collapse in" style="padding-left: 0px">
		<%@ include file="../hotel/inner/searchform-top.jsp" %>
	</div>

	<div id="listhotels">
		<%@ include file="../hotel/inner/list-hotelquery-b2b.jsp" %>
	</div>	
			
</form:form>

<c:set var="scripts" scope="request">

	<v:javascript formName="hotelSearch" staticJavascript="false" />
	<script type="text/javascript" src="<c:url value="/scripts/validator.jsp"/>"></script>
	<script type="text/javascript" src="<c:url value="/scripts/jquery-ui.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/scripts/hotelcommon.js"/>"></script>

	<%@ include file="/scripts/session.js"%>

	<script type="text/javascript">

	</script>
	 	 
</c:set>

