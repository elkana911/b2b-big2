<%@ include file="/common/taglibs.jsp" %>

<head>
    <title><fmt:message key="airlineList.title"/></title>
    <meta name="menu" content="AdminMenu"/>
</head>

<c:if test="${not empty searchError}">
    <div class="alert alert-danger alert-dismissable">
        <a href="#" data-dismiss="alert" class="close">&times;</a>
        <c:out value="${searchError}"/>
    </div>
</c:if>

<div class="col-sm-10">
    <h2><fmt:message key="airlineList.heading"/></h2>

    <form method="get" action="${ctx}/admin/airlines" id="searchForm" class="form-inline">
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
    
    <div id="actions" class="btn-group">
        <a class="btn btn-primary" href="<c:url value='/admin/airlineform?method=Add&from=list'/>">
            <i class="icon-plus icon-white"></i> <fmt:message key="button.add"/></a>

        <a class="btn btn-default" href="<c:url value='/home'/>">
            <i class="icon-ok"></i> <fmt:message key="button.done"/></a>
    </div>
    
    <display:table name="airlineList" cellspacing="0" cellpadding="0" requestURI=""
                   defaultsort="1" id="airlines" pagesize="25" class="table table-condensed table-striped table-hover" export="true">
       <display:column property="name" escapeXml="true" sortable="true" titleKey="airline.name" style="width: 25%"
                        url="/admin/airlineform?from=list" paramId="id" paramProperty="airline_id" media="all"/>
        <display:column property="hq" escapeXml="true" sortable="true" titleKey="airline.hq"
                        style="width: 35%" media="all"/>    
        <display:column property="code" sortable="true" titleKey="airline.code" style="width: 15%" autolink="true"
                        media="all"/>
        <display:column property="website1" sortable="true" titleKey="airline.website1" style="width: 25%" autolink="true"
                        media="all"/>  

        <display:setProperty name="paging.banner.item_name"><fmt:message key="airlineList.airline"/></display:setProperty>
        <display:setProperty name="paging.banner.items_name"><fmt:message key="airlineList.airlines"/></display:setProperty>

        <display:setProperty name="export.excel.filename" value="AirlineList.xls"/>
        <display:setProperty name="export.csv.filename" value="AirlineList.csv"/>
        <display:setProperty name="export.pdf.filename" value="AirlineList.pdf"/>
                                              
	</display:table>    
</div>