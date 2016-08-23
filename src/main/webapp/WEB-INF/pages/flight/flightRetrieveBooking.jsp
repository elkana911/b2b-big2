<%@ include file="/common/taglibs.jsp"%>

<head>
<title><fmt:message key="menu.retrieveBooking" /></title>

<style>

.ui-menu .ui-menu-item a{
    background: rgb(252, 252, 242);
    font-size:11px;
    margin: 2px;
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

<body>

<div class="row">
<div class="col-sm-6">
	<form method="post" action="<c:url value='/flight/b2b/retrievebooking'/>" class="well" autocomplete="off">
        <div class="form-group">
        	<label class="control-label"><fmt:message key="flight.b2b.retrievebooking"/></label>
            <input type="text" class="form-control" name="bookingcode" id="bookingcode" required>
        </div>
		<button type="submit" name="searchParam" class="btn btn-primary"
			onclick="bCancel=false">
			<i class="icon-upload icon-white"></i>
			<fmt:message key="button.search" />
		</button>

	</form>
</div>				
</div>			

</body>

<c:set var="scripts" scope="request">
	<v:javascript formName="bookFlight" staticJavascript="false" />
	<script type="text/javascript"
		src="<c:url value="/scripts/validator.jsp"/>"></script>

</c:set>
