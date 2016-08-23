<%@ include file="/common/taglibs.jsp" %>
<!-- ga kepake -->
<head>
    <title><fmt:message key="menu.ticketBook"/></title>
</head>

<body class="signup"/>

<div class="col-sm-3">
    <h2><fmt:message key="ticket.passengerContact"/></h2><fmt:message key="ticket.timerMsg"/>
<%--     <p><fmt:message key="signup.message"/></p> --%>
</div>
<div class="col-sm-7">
	<c:out value="Jakarta - Semarang" />
    <form:form commandName="contactForm" method="post" action="../flight/book" autocomplete="on"
               cssClass="well" onsubmit="return validateSignup(this)">
         <div class="form-group">
            <label class="control-label"><fmt:message key="ticket.personName"/></label>
            <input type="text" name="city" class="form-control" id="personName1" value="<c:out value="${personName}" escapeXml="true"/>" required>
        </div>
	    
	    <div class="form-group">
        	<label class="control-label"><fmt:message key="ticket.personPhone"/></label>
            <input type="text" class="form-control" name="personPhone" id="personPhone1" required>
        </div>  
               
        <div class="form-group">
        	<label class="control-label"><fmt:message key="ticket.personEmail"/></label>
            <input type="text" class="form-control" name="personEmail" id="personEmail1" required>
        </div>
	    
	    <div class="form-group">
		    <button type="submit" class="btn btn-large btn-primary">
		        <i class="icon-ok icon-white"></i> <fmt:message key='button.bookNow'/>
		    </button>
	    </div>
    </form:form>  
</div>

<c:set var="scripts" scope="request">
<v:javascript formName="signup" staticJavascript="false"/>
<script type="text/javascript" src="<c:url value="/scripts/validator.jsp"/>"></script>
</c:set>