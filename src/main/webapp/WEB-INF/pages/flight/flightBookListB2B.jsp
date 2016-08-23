<%@ include file="/common/taglibs.jsp"%>

<%@ page import="com.big.web.b2b_big2.email.EmailSimple"%>

<head>
<title><fmt:message key="menu.ticketBook" /></title>
<link rel="stylesheet" href="<c:url value="/styles/jquery-ui.css"/>" />
<fmt:setLocale value="en_US"/>
<style>
	.panel{
		border: 1px solid rgb(214, 214, 214);
	}
	.panel-default > .panel-heading {
		background-color: #2D3F4D;
		color: white;
		padding: 6px;
	}
	.panel-default-blue > .panel-heading-blue {
		border-top-left-radius: 3px;
		border-top-right-radius: 3px;
		background: rgb(30,87,153);
		color: white;
		padding: 6px;
	}
	.panel-default-bluesky > .panel-heading-bluesky {
		border-top-left-radius: 3px;
		border-top-right-radius: 3px;
		background: rgb(0,149,224);
		color: white;
		padding: 6px;
	}
	.panel-default-green > .panel-heading-green {
		border-top-left-radius: 3px;
		border-top-right-radius: 3px;
		background: #22AE19;
		color: white;
		padding: 6px;
	}

	.panel-default-red > .panel-heading-red {
		border-top-left-radius: 3px;
		border-top-right-radius: 3px;
		background: #a90329;
		color: white;
		padding: 6px;
	}
	.panel-title{
		color: white;
		text-shadow: 1px 1px #000000;
	}
	
	#bookDtl tr > th{
		text-align: right;
	}

	#bookDtl tr > td{
		text-align: right;
	}

	.timeCell{
		color: red;
		font-size: smaller;
	}	
	
	.flightNoCell{
		font-weight: bolder;
		font-size: large;
	}
	
	.classNameCell{
		font-weight: bold;
		font-size: x-large;
	}
	.pnrNameCell{
		font-weight: bold;
	}
	
	.bookStatusCell{
		font-weight: bold;	
	}
	.bookCodeCell{
		font-weight: bold;	
	}
	.airlineLogo-med{
		width: auto;
		height: 40px;
	}
	
            .tabss li {
                list-style:none;
                display:inline;
            }

            .tabss a {
                padding:5px 10px;
                display:inline-block;
                background:#666;
                color:#fff;
                text-decoration:none;
            }

            .tabss li.active a{
                background:#f0f;
                color:#00;
            }
           
            .tab_content {
                display: none;
            }
	.currencyLabel { display:block; } 
	.fontTicket{
		font-family: monospace;
	}
@media(max-width:767px){
.airlineLogo-med{
		width: auto;
		height: 30px;
	}
	
	
}

</style>

<meta name="menu" content="FlightMenu" />
	<script type="text/javascript" src="<c:url value="/scripts/jquery-ui.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/scripts/flightbookb2b.js"/>"></script>
<!-- 	<script type="text/javascript">var msgIssueConfirm = "<fmt:message key="delete.confirm"><fmt:param value="${delObject}"/></fmt:message>"; -->
	</script>

	
</head>
<div class="container">

    <div class="row">
		<div class="col-sm-12">
			<div class="panel panel-default">
	 			<div class="panel-heading">
	 				<h3 class="panel-title"><strong>PNR List Status</strong></h3>
	 			</div>
	 			<div class="panel-body">
					<display:table id="ticket" name="listBooks" cellspacing="0" cellpadding="0" requestURI="" keepStatus="true"
						class="table table-condensed table-striped table-hover" export="false">
						<display:column titleKey="report.no" media="all" style="width: 2%">
							${ticket_rowNum}
						</display:column>
				       	<display:column titleKey="report.booking.bookingCode" style="width: 28%;font-family: monospace;font-size:large"
				                        media="all" class="bookCodeCell">
						    <a href="#tab${ticket_rowNum}" > 
							    <c:out value='${ticket.code}'/>
						    </a>
<%-- 						    <a href="<c:url value='/flight/b2b/debugbookpage'/>/<c:out value='${ticket.id}'/>" >  --%>
<%-- 							    <c:out value='${ticket.code}'/> --%>
<!-- 						    </a> -->
				        </display:column>
						
						<display:column property="status" titleKey="report.booking.status" media="all" class="bookStatusCell" decorator="com.big.web.b2b_big2.flight.decorator.BookStatusColumnDecorator"/>
						
<%-- 						<display:column property="timeToPay" titleKey="report.booking.timeToPay" media="all" format="{0,date,dd-MMM-yyyy HH.mm.ss}"/> --%>
						
						<display:column titleKey="report.booking.timeToPay">
						    <fmt:formatDate value="${ticket.timeToPay}" type="both" dateStyle="long" timeStyle="long"/>
						  </display:column>
										
					</display:table>
	 			</div>
			</div>

		</div>
	</div>
	<hr>
	<c:choose> 
	  <c:when test="${fn:length(listBooks) > 2}" > 
	 	<div id="flip-tabs" >  
		    <ul id='tabs' class="tabss">
		 	<c:forEach items="${listBooks}" varStatus="loop">
		 		<li id="eric${loop.index+1}"><a href="#tab${loop.index+1}">${listBooks[loop.index].code}</a></li>
	      	</c:forEach>
	       	</ul>
		 	<c:forEach items="${listBooks}" varStatus="loop">
				<div id="tab${loop.index+1}" class="tab_content" >
					<c:set var="bookDtl" value="${listBooks[loop.index]}" scope="request" />
					<c:set var="index" value="${loop.index}" scope="request" />
					<c:set var="layoutMode" value="compact" scope="request" />
					<%@ include file="../flight/book/flightBookB2B.jsp" %>
					<script>
						hitungPax('${index}');
					</script>
		        </div>
	      	</c:forEach>
	    </div>  
	  </c:when> 
	  <c:when test="${fn:length(listBooks) == 1}" > 
			<div class="row">
				<div class="col-sm-12">
					<c:set var="bookDtl" value="${listBooks[0]}" scope="request" />
					<c:set var="side" value="left" scope="request" />
					<c:set var="index" value="0" scope="request" />
					<c:set var="layoutMode" value="compact" scope="request" />
					<%@ include file="../flight/book/flightBookB2B.jsp" %>
					<script>
						hitungPax('${index}');
					</script>
				</div>
			</div>
	  </c:when> 
	  <c:otherwise> 
			<div class="row">
				<div class="col-sm-12 col-md-6 col-lg-6">
					<c:choose>
					  <c:when test="${empty listBooks[0].code}">
					   <div style="color: red;"><strong><fmt:message key="errors.booking.failed" /></strong></div>
					  </c:when>
					  <c:otherwise>
						<c:set var="bookDtl" value="${listBooks[0]}" scope="request" />
						<c:set var="side" value="left" scope="request" />
						<c:set var="index" value="0" scope="request" />
						<c:set var="layoutMode" value="full" scope="request" />
						<%@ include file="../flight/book/flightBookB2B.jsp" %>
						<script>
							hitungPax('${index}');
						</script>
					  </c:otherwise>
					</c:choose>
				
				
				</div>
				<div class="col-sm-12 col-md-6 col-lg-6">
					<c:choose>
					  <c:when test="${empty listBooks[1].code}">
					   <div style="color: red;"><strong><fmt:message key="errors.booking.failed" /></strong></div>
					  </c:when>
					  <c:otherwise>
						<c:set var="bookDtl" value="${listBooks[1]}" scope="request" />
						<c:set var="side" value="right" scope="request" />
						<c:set var="index" value="1" scope="request" />
						<c:set var="layoutMode" value="full" scope="request" />
						<jsp:include page="../flight/book/flightBookB2B.jsp"/>
						<script>
							hitungPax('${index}');
						</script>
					  </c:otherwise>
					</c:choose>
				
				</div>
			</div>
	  </c:otherwise> 
	</c:choose>  

	<hr>
	<div class="row">
		<div class="col-sm-12">
			<div>Currency: IDR</div>
<%-- 			<div>Last Book By :<strong>${agent.userName}</strong></div>			 --%>
		</div>
	</div>	
</div>


<c:set var="scripts" scope="request">
	<v:javascript formName="bookFlight" staticJavascript="false" />
	<script type="text/javascript" src="<c:url value="/scripts/validator.jsp"/>"></script>
</c:set>
