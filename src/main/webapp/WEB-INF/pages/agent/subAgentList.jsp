<%@ include file="/common/taglibs.jsp" %>

<head>
    <title><fmt:message key="subAgentList.title"/></title>
    <meta name="menu" content="AdminMenu"/>
</head>

<c:if test="${not empty searchError}">
    <div class="alert alert-danger alert-dismissable">
        <a href="#" data-dismiss="alert" class="close">&times;</a>
        <c:out value="${searchError}"/>
    </div>
</c:if>

<div class="col-sm-10">
    <h2><fmt:message key="subAgentList.heading"/></h2>

    <form method="get" action="${ctx}/admin/subagents" id="searchForm" class="form-inline">
    <div id="search" class="text-right hidden">
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
		<div class="col-xs-12 col-sm-3 form-group">
		    <div id="actions" class="btn-group">
		        <a class="btn btn-primary" href="<c:url value='/agent/subagentform?method=Add&from=list'/>">
		            <i class="icon-plus icon-white"></i> <fmt:message key="button.add"/></a>
		
		        <a class="btn btn-default" href="<c:url value='/home'/>">
		            <i class="icon-ok"></i> <fmt:message key="button.done"/></a>
		    </div>
	    </div>
		<div class="col-xs-12 col-sm-9">
			<form method="get" action="${ctx}/admin/subagents" id="searchForm" class="form-inline">
				<div class="row">
				<div class="col-xs-12 col-sm-5">
						<label for="selectAgent" class="control-label"><fmt:message key="agent.agency"/></label>
						<select id="selectAgent" name="agency" class="form-control">
							<option value=""
								<c:if test="${empty agency}"> 'selected' </c:if>>ALL</option>
						     <c:forEach items="${agencies}" var="a">
						          <option value="${a.agent_id}" ${fn:contains(agency, a.agent_id) ? 'selected' : ''}>${a.agentName}</option>
						     </c:forEach>
						</select>
				</div>
				<div class="col-xs-12 col-sm-5 form-horizontal">
						<label><fmt:message key="label.status"/></label>
						<select id="selectStatus" name="status" class="form-control">
						 	<option value="" <c:if test="${empty param.status})"> 'selected' </c:if> >ALL</option>
			                <option value="A" ${fn:contains(param.status, 'A') ? 'selected' : ''}>ACTIVE</option>
			                <option value="E" ${fn:contains(param.status, 'E') ? 'selected' : ''}>INACTIVE</option>
			                <option value="P" ${fn:contains(param.status, 'P') ? 'selected' : ''}>PENDING</option>
			            </select>
				</div>
				<div class="col-xs-12 col-sm-2 form-horizontal">
				        <button id="button.search" class="btn btn-primary" type="submit" onclick="toggleExportCsv();" style="margin-top: 1.6em">
				            <i class="icon-search"></i> <fmt:message key="button.search"/>
				        </button>
				</div>
				</div>
			</form>
		</div>
	</div>

    <display:table name="subAgentList" cellspacing="0" cellpadding="0" requestURI="" 
                   defaultsort="1" id="agents" pagesize="25" class="table table-condensed table-striped table-hover" export="true">
		<display:column titleKey="report.no" media="all" style="width: 2%">
			${agents_rowNum}.
		</display:column>
        <display:column property="agent_Name" escapeXml="true" sortable="true" titleKey="agent.name" style="width: 25%"
                        url="/agent/subagentform?from=list" paramId="idParam" paramProperty="id" media="all"/>
        <display:column property="city" sortable="true" titleKey="agent.city" style="width: 15%" autolink="true"
                        media="all"/>
<%--         <display:column property="account.bank_code" sortable="true" titleKey="account.bank" autolink="true" --%>
<%-- 	        decorator="com.big.web.b2b_big2.flight.decorator.BankCodeColumnDecorator" --%>
<%--                         media="all"/>   --%>
        <display:column property="accountNo" sortable="true" titleKey="account.no" autolink="true"
	        decorator="com.big.web.b2b_big2.flight.decorator.VANColumnDecorator"
                        media="all"/>  
<%--         <display:column property="account.status" sortable="true" title="account.status" autolink="true" --%>
<%-- 			decorator="com.big.web.b2b_big2.flight.decorator.AccountStatusColumnDecorator" --%>
<%-- 			media="all"/>    --%>
        <display:column property="userLogin.username" sortable="true" titleKey="agent.username" autolink="true"
                        media="all"/>
        <display:column property="userLogin.created_date" sortable="true" titleKey="user.regDate" style="width: 15%" autolink="true"
    	    decorator="com.big.web.b2b_big2.flight.decorator.DateTimeColumnDecorator"
                        media="all"/>

        <display:setProperty name="paging.banner.item_name"><fmt:message key="agentList.agent"/></display:setProperty>
        <display:setProperty name="paging.banner.items_name"><fmt:message key="agentList.agents"/></display:setProperty>

        <display:setProperty name="export.excel.filename" value="SubAgentList.xls"/>
        <display:setProperty name="export.csv.filename" value="SubAgentList.csv"/>
        <display:setProperty name="export.pdf.filename" value="SubAgentList.pdf"/>
                                              
	</display:table>
	
	<div style="margin-top: 1.5em">	
		<ul class="glassList" style="font-size: 9px">
		    <li>
		        <fmt:message key="subAgentList.remark.pendingStatus"/>
		    </li>
		</ul>
	</div>

    <div class="text-right" ${empty hideSelectAgent ? '' : ' hidden'} id="exportToCSV" style="margin-top: 2em;">
	        <a class="btn btn-primary" href="<c:url value='/admin/exportsubagents'/>" onclick="return onExportClick();">
	            <i class="icon-ok"></i> <c:out value="Export To CSV" /></a>
    </div>
	    
</div>

<script type="text/javascript">

	function onExportClick(){
		var r = confirm("Export Pending Accounts to CSV ?");
		if (r == false) {
		    return false;
		} 	
	
		window.open("https://bizchannel.cimbniaga.co.id/corp/common2/login.do?action=loginRequest");
		return true;
	}
	
	function toggleExportCsv()
	{
	    var val = $("#selectStatus").prop("value");
	    if (val === 'P')
	    	$("#exportToCSV").show();
	    else
	    	$("#exportToCSV").hide();
	}

</script>