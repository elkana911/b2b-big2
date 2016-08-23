<%@ include file="/common/taglibs.jsp" %>

<head>
    <title><fmt:message key="newsList.news"/></title>
    <meta name="menu" content="AdminMenu"/>
</head>

<c:if test="${not empty searchError}">
    <div class="alert alert-danger alert-dismissable">
        <a href="#" data-dismiss="alert" class="close">&times;</a>
        <c:out value="${searchError}"/>
    </div>
</c:if>

<div class="col-sm-10">
    <h2><fmt:message key="newsProfile.heading"/></h2>
	<%int rowNum=1;%>
    <form method="get" action="${ctx}/admin/newslist" id="searchForm" class="form-inline">
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
        <a class="btn btn-primary" href="<c:url value='/admin/newsform?method=Add&from=list'/>">
            <i class="icon-plus icon-white"></i> <fmt:message key="button.add"/></a>

        <a class="btn btn-default" href="<c:url value='/home'/>">
            <i class="icon-ok"></i> <fmt:message key="button.done"/></a>
    </div>
    
    <display:table name="newsList" id="newsItem" cellspacing="0" cellpadding="0" requestURI=""
                   defaultsort="1" pagesize="25" class="table table-condensed table-striped table-hover" export="false">
		<display:column sortable="true" title="No" media="all" style="width: 2%">
			<%=rowNum++%>
		</display:column>

       <display:column property="title" escapeXml="true" sortable="true" titleKey="feeds.title" style="width: 25%"
                        url="/admin/newsform?from=list" paramId="id" paramProperty="id" media="all"/>
        <display:column property="remarks" escapeXml="true" sortable="true" titleKey="feeds.remarks"
                        style="width: 35%" media="all"/>    
        <display:column property="headline" sortable="true" titleKey="feeds.headline" style="width: 2%" autolink="true"
                        media="all"/>  
        <display:column title="" media="html" style="width: 10%" >
        	<c:if test="${newsItem.thumbId != null}">
        		<img class="img-thumbnail" src="<c:url value='/feeds/getThumb?imagePath=${newsItem.thumbId}' />" width="100px" height="auto" />
        	</c:if>
        </display:column>  
        <display:column property="lastUpdate" sortable="true" titleKey="feeds.date" style="width: 8%" autolink="true"
                        media="all"/>

        <display:setProperty name="paging.banner.item_name"><fmt:message key="newsList.news"/></display:setProperty>
        <display:setProperty name="paging.banner.items_name"><fmt:message key="newsList.news"/></display:setProperty>

        <display:setProperty name="export.excel.filename" value="NewsList.xls"/>
        <display:setProperty name="export.csv.filename" value="NewsList.csv"/>
        <display:setProperty name="export.pdf.filename" value="NewsList.pdf"/>
                                              
	</display:table>    
</div>