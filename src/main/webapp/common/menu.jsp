<%@ include file="/common/taglibs.jsp"%>

<menu:useMenuDisplayer name="Velocity" config="navbarMenu.vm" permissions="rolesAdapter">
<div class="collapse navbar-collapse" id="navbar">

<c:set value="style=float:left;" var="kanan"/>
<c:if test="${empty pageContext.request.remoteUser}">
	<c:set value="style=float:right;" var="kanan"/>
</c:if>

<ul class="nav navbar-nav" ${kanan}>
    <c:if test="${empty pageContext.request.remoteUser}">
        <li class="active">
            <a href="<c:url value="/login"/>"><fmt:message key="login.title"/></a>
        </li>
    </c:if>
    <menu:displayMenu name="Home"/>
    <menu:displayMenu name="TicketMenu"/>
    <menu:displayMenu name="TopUpMenu"/>
    <menu:displayMenu name="ReportMenu"/>
    <menu:displayMenu name="MandiraAdminReportMenu"/>
    <menu:displayMenu name="UserMenu"/>
    <menu:displayMenu name="MandiraAdminMenu"/>
    <menu:displayMenu name="AdminMenu"/>
    <menu:displayMenu name="SpvMenu"/>
    <menu:displayMenu name="HelpMenu"/>
    <menu:displayMenu name="Logout"/>
</ul>
<ul class="nav navbar-nav navbar-right hidden-xs hidden-sm hidden-md navbar-call-center">
<!--         <li><span class="glyphicon icon-callCenter" style="float:left;"></span></li> -->
        <li><span style="padding:15px 2px;display: block;">Call Center (+62)21 - 293 243 80</span></li>
</ul>
</div>
</menu:useMenuDisplayer>
