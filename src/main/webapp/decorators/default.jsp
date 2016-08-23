<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>


<%@page import="com.big.web.b2b_big2.agent.pojo.Agent"%>

<html lang="en">
<head>
    <meta http-equiv="Cache-Control" content="no-store"/>
    <meta http-equiv="Pragma" content="no-cache"/>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="icon" href="<c:url value="/images/favicon.ico"/>"/>
    <title><decorator:title/> | <fmt:message key="webapp.name"/></title>
    <t:assets/>
    <decorator:head/>
</head>

<style>


body{
	padding-top: 60px;
}

.well{
	background-color: transparent;
	box-shadow: 0;
	border: 1px solid rgba(189, 190, 191, 1);
}

.navbar-call-center{
	float: right;
	color: white;
	padding-right: 1em;
	text-shadow: 0 1px 2px rgba(0,0,0,1);
}

.navbar{
	background-image: none;
	background-color: rgb(45,63,77);
	border: none;
	text-shadow: none;
}

.navbar-default .navbar-text{
	color:#FFF
}

.navbar-default .navbar-nav>li>a{
	color:#FFF
}

.navbar-default .navbar-nav>li>a:hover,.navbar-default .navbar-nav>li>a:focus{
	color:#3399f3;
	background-color:transparent
}

.navbar-default .navbar-nav>.active>a,.navbar-default .navbar-nav>.active>a:hover,.navbar-default .navbar-nav>.active>a:focus{
	color:#3399f3;
	background-color:transparent
}

.navbar-default .navbar-nav>.disabled>a,.navbar-default .navbar-nav>.disabled>a:hover,.navbar-default .navbar-nav>.disabled>a:focus{
	color:#444;
	background-color:transparent
}

/* Elkana */
.btn-primary, .btn-primary:hover, .btn-primary:focus{
	color: #FFF;
	background-color: rgb(245,124,103); 
	border-color: rgb(245,124,103);
	background-image: none;
}

a{
	font-weight: bold;
}

#footer{
	padding-bottom:  0px;
	background-color: rgb(247,148,29);
	color:#FFF;
}

@media(max-width:767px){
	.navbar-default .navbar-nav .open .dropdown-menu>li>a{
		color: #F8F8F8;
	}
	#footer{
		text-align: left;
		font-size: smaller;
	}
	#content{
		padding-bottom: 0px;
	}
}

</style>
<div id="loader"></div> 
<body<decorator:getProperty property="body.id" writeEntireProperty="true"/><decorator:getProperty property="body.class" writeEntireProperty="true"/> onload="load()">
    <c:set var="currentMenu" scope="request"><decorator:getProperty property="meta.menu"/></c:set>

    <div class="navbar navbar-default navbar-fixed-top" role="navigation">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#navbar">
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <c:choose> 
			  <c:when test="${agent.logoFileName == null}">
			   <a class="navbar-brand" style="color:white;font-style: italic;font-weight: bolder;" href="<c:url value='/'/>">${agent.travelName}</a>
			  </c:when>
			  <c:otherwise>
			   <div>
	            <img src="<c:url value='/agent/getLogo?imagePath=${agent.logoFileName}' />" style="height:50px;padding-right: 2px;"/>
	            </div>
			  </c:otherwise>
			</c:choose>
        </div>

        <%@ include file="/common/menu.jsp" %>

        <c:if test="${pageContext.request.locale.language != 'en'}">
            <div id="switchLocale"><a href="<c:url value='/?locale=en'/>">
                <fmt:message key="webapp.name"/> in English</a>
            </div>
        </c:if>
        
    </div>

    <div class="container" id="content">
        <%@ include file="/common/messages.jsp" %>
        <div class="row">
            <decorator:body/>

            <c:if test="${currentMenu == 'AdminMenu'}">
                <div class="col-sm-2">
                <menu:useMenuDisplayer name="Velocity" config="navlistMenu.vm" permissions="rolesAdapter">
                    <menu:displayMenu name="AdminMenu"/>
                    <menu:displayMenu name="MandiraAdminMenu"/>
                </menu:useMenuDisplayer>
                </div>
            </c:if>
        </div>
    </div>

<% 
	Agent agent = (Agent) session.getAttribute("agent"); 
%>
    <div id="footer" class="container navbar-fixed-bottom">
        <span class="col-sm-6 text-left">
<%--             <c:if test="${pageContext.request.remoteUser != null}"> --%>
            <c:if test="${agent != null && agent.id != null}">
	            Agent:&nbsp; <span class="text-capitalize"><strong>${pageContext.request.remoteUser}</strong> </span>&nbsp;( ${agent.packageType})
	            | Balance:&nbsp;${agent.balanceIdr}&nbsp;IDR
<%-- 	            | &nbsp;&nbsp;${agent.balanceUsd}&nbsp;USD --%>
            </c:if>
<%--             | ${now} --%>
        </span>
        <span class="col-sm-6 text-right" style="font-size: xx-small;">
            &copy; <fmt:message key="copyright.year"/> <a href="<fmt:message key="company.url"/>"><fmt:message key="company.name"/></a>&nbsp;(<fmt:message key="webapp.version"/>)
        </span>
    </div>
<%= (request.getAttribute("scripts") != null) ?  request.getAttribute("scripts") : "" %>
</body>
<script type="text/javascript">
	$(window).load(function () {
		  // run code
		$('#loader').hide();
		});
	
	function splash(){
		$('#loader').show();
	}
</script>
</html>
