<%@ include file="/common/taglibs.jsp" %>

<head>
    <title><fmt:message key="agentList.title"/></title>
    <meta name="menu" content="AdminMenu"/>
</head>

<c:if test="${not empty searchError}">
    <div class="alert alert-danger alert-dismissable">
        <a href="#" data-dismiss="alert" class="close">&times;</a>
        <c:out value="${searchError}"/>
    </div>
</c:if>

<div class="col-sm-10">
    <h2><fmt:message key="agentList.heading"/></h2>

    <form method="get" action="${ctx}/admin/agents" id="searchForm" class="form-inline">
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
        <a class="btn btn-primary" href="<c:url value='/agent/agentform?method=Add&from=list'/>">
            <i class="icon-plus icon-white"></i> <fmt:message key="button.add"/></a>

        <a class="btn btn-default" href="<c:url value='/home'/>">
            <i class="icon-ok"></i> <fmt:message key="button.done"/></a>
    </div>
    
    <display:table name="agentList" cellspacing="0" cellpadding="0" requestURI=""
                   defaultsort="1" id="agents" pagesize="25" class="table table-condensed table-striped table-hover" export="true">
       <display:column property="agent_Name" escapeXml="true" sortable="true" titleKey="agent.name" style="width: 25%"
                        url="/agent/agentform?from=list" paramId="idParam" paramProperty="id" media="all"/>
        <display:column property="address1" escapeXml="true" sortable="true" titleKey="agent.address1"
                        style="width: 35%" media="all"/>    
        <display:column property="city" sortable="true" titleKey="agent.city" style="width: 15%" autolink="true"
                        media="all"/>
        <display:column property="website1" sortable="true" titleKey="agent.website1" style="width: 25%" autolink="true"
                        media="all"/>  

        <display:setProperty name="paging.banner.item_name"><fmt:message key="agentList.agent"/></display:setProperty>
        <display:setProperty name="paging.banner.items_name"><fmt:message key="agentList.agents"/></display:setProperty>

        <display:setProperty name="export.excel.filename" value="AgentList.xls"/>
        <display:setProperty name="export.csv.filename" value="AgentList.csv"/>
        <display:setProperty name="export.pdf.filename" value="AgentList.pdf"/>
                                              
	</display:table>    
</div>