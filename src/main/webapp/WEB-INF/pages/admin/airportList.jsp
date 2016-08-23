<%@ include file="/common/taglibs.jsp" %>

<head>
    <title><fmt:message key="airportList.title"/></title>
    <meta name="menu" content="AdminMenu"/>
</head>

<c:if test="${not empty searchError}">
    <div class="alert alert-danger alert-dismissable">
        <a href="#" data-dismiss="alert" class="close">&times;</a>
        <c:out value="${searchError}"/>
    </div>
</c:if>

<div class="col-sm-10">
    <h2><fmt:message key="airportList.heading"/></h2>

    <form method="get" action="${ctx}/admin/airports" id="searchForm" class="form-inline">
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
		<div class="col-xs-3 form-group">
		    <div id="actions" class="btn-group">
		        <a class="btn btn-primary" href="<c:url value='/admin/airportform?method=Add&from=list'/>">
		            <i class="icon-plus icon-white"></i> <fmt:message key="button.add"/></a>
		
		        <a class="btn btn-default" href="<c:url value='/home'/>">
		            <i class="icon-ok"></i> <fmt:message key="button.done"/></a>
		    </div>
	    </div>
	    
	    <form method="get" action="${ctx}/admin/airports" id="searchAirportByCountry" class="form-inline">
			<div class="col-xs-9 form-horizontal">
				<label><fmt:message key="airport.country"/></label>
				<appfuse:country name="country" prompt="ALL" default="${country}" cssStyle="padding: 8px 12px;"/>
				
		        <button id="button.search" class="btn btn-primary" type="submit">
		            <i class="icon-search"></i> <fmt:message key="button.search"/>
		        </button>
			</div>
		</form>
	</div>


		    <display:table name="airportList" cellspacing="0" cellpadding="0" requestURI=""
		                   defaultsort="1" id="airports" pagesize="25" class="table table-condensed table-striped table-hover" export="true">
		       <display:column property="airport_name" escapeXml="true" sortable="true" titleKey="airport.name" style="width: 25%"
		                        url="/admin/airportform?from=list" paramId="id" paramProperty="airport_id" media="all"/>
		        <display:column property="aka" escapeXml="true" sortable="true" titleKey="airport.aka"
		                        style="width: 10%" media="all"/>    
		        <display:column property="iata" sortable="true" titleKey="airport.iata" style="width: 5%" autolink="true"
		                        media="all"/>
		        <display:column property="city" sortable="true" titleKey="airport.city" style="width: 5%" autolink="true"
		                        media="all"/>
		        <display:column property="country" sortable="true" titleKey="airport.country" style="width: 10%" autolink="true"
		                        media="all"/> 
		
		        <display:setProperty name="paging.banner.item_name"><fmt:message key="airportList.airport"/></display:setProperty>
		        <display:setProperty name="paging.banner.items_name"><fmt:message key="airportList.airports"/></display:setProperty>
		
		        <display:setProperty name="export.excel.filename" value="AirportList.xls"/>
		        <display:setProperty name="export.csv.filename" value="AirportList.csv"/>
		        <display:setProperty name="export.pdf.filename" value="AirportList.pdf"/>
		                                              
			</display:table>    
</div>