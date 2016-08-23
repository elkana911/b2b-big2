<%@ include file="/common/taglibs.jsp" %>

<head>
    <title><fmt:message key="monitor.title"/></title>
    <meta name="menu" content="AdminMenu"/>
	<link rel="stylesheet" href="<c:url value="/styles/jquery-ui.css"/>" />
	<link rel="stylesheet" href="<c:url value="/styles/style.css"/>" />    
	<link rel="stylesheet" href="<c:url value="/styles/flags.css"/>" />    
</head>

<style>
.monitor{
	font-size: smaller;
}

.loader-med{
	width:40px;
	height:40px;
	text-align:center;
	background-color: rgba(192, 192, 192, 0.0);
	background-image: url(../images/page-loader.gif);
	background-size: 30px 30px;
	background-repeat: no-repeat;
	background-position: center;
}

.float-right{
	float:right;
	margin-top:16px;
}

</style>
<body>

<div class="col-sm-10">
    <h2><fmt:message key="monitor.heading"/></h2>

    <p><fmt:message key="monitor.message"/></p>

    <div id="actions" class="form-group">
        <a href="${ctx}/admin/monitor" class="btn btn-primary">
            <i class="icon-ok icon-white"></i> <fmt:message key="button.refresh"/></a>
    </div>

    <h2><fmt:message key="monitor.heading.users"/></h2>
    <display:table name="applicationScope.userNames" id="user" cellspacing="0" cellpadding="0"
                   defaultsort="1" class="table table-condensed table-striped table-hover monitor" pagesize="50" requestURI="">
        <display:column property="username" escapeXml="true" titleKey="user.username"
                        sortable="true"/>
        <display:column titleKey="activeUsers.fullName" sortable="true">
            <c:out value="${user.firstName} ${user.lastName}" escapeXml="true"/>
            <c:if test="${not empty user.email}">
                <a href="mailto:<c:out value="${user.email}"/>">
                    <img src="<c:url value="/images/iconEmail.gif"/>"
                         alt="<fmt:message key="icon.email"/>" class="icon"/></a>
            </c:if>
        </display:column>
        <display:column property="ipAddress" escapeXml="true" titleKey="user.ip"
                        sortable="true"/>
        <display:column property="browser" escapeXml="true" titleKey="user.browser"
                        sortable="true"/>
        <display:column property="ipAddress" titleKey="user.address.city"
						decorator="com.big.web.b2b_big2.decorator.MonitorAreaColumnDecorator"                        
                        sortable="true"/>
        <display:setProperty name="paging.banner.item_name" value="user"/>
        <display:setProperty name="paging.banner.items_name" value="users"/>
    </display:table>

<hr>
	<div class="container">
		<div class="row">
		    <h3 style="float:left"><fmt:message key="monitor.heading.agent"/></h3>
		    <button id="refreshAgency" class="btn btn-primary float-right">Refresh</button>
	    	<div id="progressbarAgencyInfo" class="loader-med float-right"></div>
	    </div>
	    <div class="row">
			<table border="0" cellpadding="0" width="100%" id='agencyInfo' class="table-bordered table-condensed table-striped table-hover monitor" >
			</table>
		</div>
	</div>

	<div class="container" style="margin-top: 1em">
		<div class="row">
	    	<h3 style="float:left"><fmt:message key="monitor.heading.flight"/></h3>
	    	<button id="refreshFlight" class="btn btn-primary float-right">Refresh</button>
	    	<div id="progressbarFlightInfo" class="loader-med float-right"></div>
	    </div>
	    <div class="row">
			<table border="0" cellpadding="0" width="100%" id='flightInfo' class="table-bordered table-condensed table-striped table-hover monitor" >
			</table>
		</div>
	</div>

	<div class="container" style="margin-top: 1em">
		<div class="row">
	    	<h3 style="float:left"><fmt:message key="monitor.heading.airline"/></h3>
		    <button id="refreshAirline" class="btn btn-primary float-right">Refresh</button>
	    	<div id="progressbarAirlineInfo" class="loader-med float-right"></div>
	    </div>
    	<div class="row">
			<table border="0" cellpadding="0" width="100%" id='airlineInfo' class="table-bordered table-condensed table-striped table-hover monitor" >
			</table>
		</div>
	</div>

	<div class="container" style="margin-top: 1em">
		<div class="row">
		    <h3 style="float:left"><fmt:message key="monitor.heading.bookingFlight"/></h3>
		    <button id="refreshBookingFlight" class="btn btn-primary float-right">Refresh</button>
	    	<div id="progressbarFlightBookingInfo" class="loader-med float-right"></div>
	    </div>
    	<div class="row">
			<table border="0" cellpadding="0" width="100%" id='flightBookingInfo' class="table-bordered table-condensed table-striped table-hover monitor" >
			</table>
		</div>
	</div>

	<div class="container" style="margin-top: 1em">
		<div class="row">
	    	<h3 style="float:left"><fmt:message key="monitor.heading.finance"/></h3>
	    	<button id="refreshFinance" class="btn btn-primary float-right">Refresh</button>
	    	<div id="progressbarFinanceInfo" class="loader-med float-right"></div>
	    </div>
    	<div class="row">
			<table border="0" cellpadding="0" width="100%" id='financeInfo' class="table-bordered table-condensed table-striped table-hover monitor" >
			</table>
		</div>
	</div>
	
	<div class="container" style="margin-top: 1em">
		<div class="row">
	    	<h3 style="float:left"><fmt:message key="monitor.heading.sysinfo"/></h3>
	    	<button id="refreshSysInfo" class="btn btn-primary float-right">Refresh</button>
	    	<div id="progressbarSysInfo" class="loader-med float-right"></div>
	    </div>
    	<div class="row">
			<table border="0" cellpadding="0" width="100%" id='sysInfo' class="table-bordered table-condensed table-striped table-hover monitor" >
			</table>
		</div>
	</div>	
</div>

<script type="text/javascript" src="<c:url value="/scripts/jquery-ui.js"/>"></script>
<%@ include file="/scripts/session.js"%>
<script>var myContextPath = "${pageContext.request.contextPath}"</script>
<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.3.0-beta.3/angular.min.js"></script>
<script type="text/javascript" src="<c:url value="/scripts/monitor.js"/>"></script>

</body>
