<%@ include file="/common/taglibs.jsp"%>

<head>
<title><fmt:message key="admin.topup.title" /></title>
<meta name="menu" content="AdminMenu" />
	<link rel="stylesheet" href="<c:url value="/styles/jquery-ui.css"/>" />
	<link rel="stylesheet" href="<c:url value="/styles/style.css"/>" />
</head>

<c:if test="${not empty searchError}">
	<div class="alert alert-danger alert-dismissable">
		<a href="#" data-dismiss="alert" class="close">&times;</a>
		<c:out value="${searchError}" />
	</div>
</c:if>

<div class="col-sm-10">
	<h2>
		<fmt:message key="admin.topup.heading" />
	</h2>

	<form method="get" action="${ctx}/admin/topups" id="searchForm"
		class="form-inline">
		<div id="search" class="text-right">
			<span class="col-sm-9"> <input type="text" size="20" name="q"
				id="query" value="${param.q}"
				placeholder="<fmt:message key="search.enterTerms"/>"
				class="form-control input-sm">
			</span>
			<button id="button.search" class="btn btn-default btn-sm"
				type="submit">
				<i class="icon-search"></i>
				<fmt:message key="button.search" />
			</button>
		</div>
	</form>

	<div class="row">
		<form method="get" action="${ctx}/admin/topups" id="searchForm"
			class="form-inline">
			<div class="col-xs-12 col-sm-3 col-md-3">
					<label for="selectStatus" class="control-label"><fmt:message key="report.topup.status"/></label>	
					<select id="selectStatus" name="status" class="form-control" style="display: inline;">
						<option value=""
							<c:if test="${empty param.status})"> 'selected' </c:if>>ALL</option>
						<option value="A"
							${fn:contains(param.status, 'A') ? 'selected' : ''}>ACTIVE</option>
						<option value="E"
							${fn:contains(param.status, 'E') ? 'selected' : ''}>INACTIVE</option>
						<option value="P"
							${fn:contains(param.status, 'P') ? 'selected' : ''}>PENDING</option>
					</select>
			</div>
	        <div class="col-xs-12 col-sm-3 col-md-3">
				 <label for="selectBank" class="control-label"><fmt:message key="report.topup.bank"/></label>
					<select id="selectBank" name="bank" class="form-control" style="display: inline;">
						<option value=""
							<c:if test="${empty param.bank})"> 'selected' </c:if>>ALL</option>
					     <c:forEach items="${banks}" var="b">
					          <option value="${b.code}" ${fn:contains(param.bank, b.code) ? 'selected' : ''}>${b.name}</option>
					     </c:forEach>
					</select>
			</div>
			<div class="col-xs-12 col-sm-3 col-md-3">
					<label for="selectAgent" class="control-label"><fmt:message key="agent.agency"/></label>
					<select id="selectAgent" name="agency" class="form-control">
						<option value=""
							<c:if test="${empty param.agency})"> 'selected' </c:if>>ALL</option>
					     <c:forEach items="${agencies}" var="a">
					          <option value="${a.agent_id}" ${fn:contains(param.agency, a.agent_id) ? 'selected' : ''}>${a.agentName}</option>
					     </c:forEach>
					</select>
			</div>
			
			<div class="col-xs-12 col-sm-3 col-md-3">
				<label class="control-label">&nbsp;</label>
				<button class="form-control btn btn-primary" type="submit" style="font-weight:bolder; text-transform: uppercase;">Search</button>
			</div>
			
		</form>
	</div>

	<display:table name="topuplist" cellspacing="0" cellpadding="0"
		requestURI=""
		decorator="com.big.web.b2b_big2.flight.decorator.ReportTopUpDecorator"
		defaultsort="1" id="agents" pagesize="25"
		class="table table-condensed table-striped table-hover" export="true">
		<display:column property="rowNum" sortable="true" titleKey="report.no"
			media="all" />
		<display:column property="bankCode" sortable="true"
			titleKey="report.topup.bankCode" style="width: 15%" autolink="true"
			media="all" />
		<display:column property="bankName" escapeXml="true" sortable="true"
			titleKey="report.topup.bank" style="width: 25%" media="all" />
		<display:column property="lastUpdate" escapeXml="true" sortable="true"
			titleKey="report.topup.createdDate" style="width: 17%" media="all" />
		<display:column property="timeToPay" sortable="true"
			titleKey="report.topup.timeToPay" style="width: 17%" autolink="true"
			media="all" />
		<display:column property="amountToPaid" sortable="true"
			titleKey="report.topup.amount" style="width: 15%" autolink="true"
			media="all"
			decorator="com.big.web.b2b_big2.flight.decorator.CurrencyColumnDecorator" />
		<%--         <display:column property="feeAdmin" sortable="true" titleKey="report.topup.adminFee" style="width: 17%" autolink="true" --%>
		<%--                         media="all"/>   --%>
		<%--         <display:column property="total" sortable="true" titleKey="report.topup.total" style="width: 15%" autolink="true" --%>
		<%--                         media="all"/>   --%>
		<display:column property="status" sortable="true"
			titleKey="report.topup.status" style="width: 13%" autolink="true"
			media="all"
			decorator="com.big.web.b2b_big2.flight.decorator.TopUpStatusColumnDecorator" />

		<display:setProperty name="paging.banner.item_name">
			<fmt:message key="report.topup" />
		</display:setProperty>
		<display:setProperty name="paging.banner.items_name">
			<fmt:message key="report.topups" />
		</display:setProperty>

		<%--         <display:setProperty name="export.excel.filename" value="TopUpList.xls"/> --%>
		<%--         <display:setProperty name="export.csv.filename" value="TopUpList.csv"/> --%>
		<%--         <display:setProperty name="export.pdf.filename" value="TopUpList.pdf"/> --%>

	</display:table>


</div>

<script type="text/javascript">
	
</script>