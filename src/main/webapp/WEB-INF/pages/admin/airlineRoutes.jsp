<%@ include file="/common/taglibs.jsp" %>

<head>
    <title><fmt:message key="routeList.title"/></title>
    <meta name="menu" content="AdminMenu"/>
</head>

<c:if test="${not empty searchError}">
    <div class="alert alert-danger alert-dismissable">
        <a href="#" data-dismiss="alert" class="close">&times;</a>
        <c:out value="${searchError}"/>
    </div>
</c:if>

<div class="col-sm-10">
    <h2><fmt:message key="routeList.heading"/></h2>

    <form method="get" action="${ctx}/admin/airlineroutes" id="searchForm" class="form-inline">
	    <div id="search" class="text-right">
	        <span class="col-sm-9">
	            <input type="text" size="20" name="q" id="query" value="${param.q}"
	                   placeholder="<fmt:message key="search.enterTerms"/>" class="form-control input-sm">
	        </span>
	        <button id="button.search" class="btn btn-default btn-sm" type="submit">
	            <i class="icon-search"></i> <fmt:message key="button.search"/>
	        </button>
	    </div>
    </form>
    
	<div class="row">
	    <form method="get" action="${ctx}/admin/airlineroutes" id="searchByAirline" class="form-inline">
			<div class="col-xs-9 form-horizontal">
				<label><fmt:message key="menu.airline"/></label>
				<select name="code" class="form-control" style="width:14em; display: inline;"  >
				 	<option value="all" <c:if test="${empty param.code || param.code == 'all'})"> 'selected' </c:if> >ALL</option>
	                <c:forEach items="${airlines}" var="airline">
	                	<option value="${airline.code}" ${fn:contains(param.code, airline.code) ? 'selected' : ''}>${airline.name}</option>
                	</c:forEach>
	            </select>
		        <button id="button.search" class="btn btn-primary" type="submit">
		            <i class="icon-search"></i> <fmt:message key="button.search"/>
		        </button>
			</div>
		</form>
	</div>
	<div class="row">
	<div class="col-xs-12">
    <display:table name="routes" cellspacing="0" cellpadding="0" requestURI=""
                   defaultsort="1" id="routeId" pagesize="25" class="table table-condensed table-striped table-hover" export="true">

        <display:column property="airline.airlineName" escapeXml="true" sortable="true" titleKey="airline.name" 
                        group="1" media="csv xml excel pdf"/>
		<display:column titleKey="airline.name" sortable="true" group="1" media="html" >
            <a href="<c:url value='/admin/airlines?q=${routeId.airline.airlineName}'/>">
            	<c:out value="${routeId.airline.airlineName}"/>
            </a>
        </display:column>

        <display:column property="route.from.iataCode" escapeXml="true" sortable="true" titleKey="flight.cityFrom" 
                        group="2" media="csv xml excel pdf"/>
		<display:column titleKey="flight.cityFrom" sortable="true" group="2" media="html">
            <a href="<c:url value='/admin/airports?q=${routeId.route.from.iataCode}'/>">
            	<c:out value="${routeId.route.from.iataCode} - ( ${routeId.route.from.city} )"/>
            </a>
        </display:column>

        <display:column property="route.to.iataCode" escapeXml="true" sortable="true" titleKey="flight.cityArrive" 
                        media="csv xml excel pdf"/>
		<display:column titleKey="flight.cityArrive" sortable="true" media="html">
            <a href="<c:url value='/admin/airports?q=${routeId.route.to.iataCode}'/>">
            <c:out value="${routeId.route.to.iataCode} - ( ${routeId.route.to.city} )"/>
            </a>
        </display:column>

                        
 		<display:column title="" media="html">
 			<a class="btn btn-success btn-sm" href="<c:url value='/flight/b2b/search?dep=${routeId.route.from.iataCode}&dest=${routeId.route.to.iataCode}&airline=${routeId.airline.airlineCode}&action=0'/>">Search</a>
		</display:column>                       

        <display:setProperty name="paging.banner.item_name"><fmt:message key="routeList.route"/></display:setProperty>
        <display:setProperty name="paging.banner.items_name"><fmt:message key="routeList.routes"/></display:setProperty>

        <display:setProperty name="export.excel.filename" value="AirlineRoutesList.xls"/>
        <display:setProperty name="export.csv.filename" value="AirlineRoutesList.csv"/>
        <display:setProperty name="export.pdf.filename" value="AirlineRoutesList.pdf"/>
                                              
	</display:table>
	</div>
	</div>    
</div>