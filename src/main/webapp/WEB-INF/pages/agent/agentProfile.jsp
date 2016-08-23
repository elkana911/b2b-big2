<%@ include file="/common/taglibs.jsp"%>

<head>
    <title><fmt:message key="agentProfile.title"/></title>
    <meta name="menu" content="UserMenu"/>
</head>

<c:set var="delObject" scope="request"><fmt:message key="agentList.agent"/></c:set>
<script type="text/javascript">var msgDelConfirm =
   "<fmt:message key="delete.confirm"><fmt:param value="${delObject}"/></fmt:message>";
</script>

<div class="col-sm-2">
    <h2><fmt:message key="agentProfile.heading"/></h2>
    <c:choose>
        <c:when test="${param.from == 'list'}">
            <p><fmt:message key="agentProfile.admin.message"/></p>
        </c:when>
        <c:otherwise>
            <p><fmt:message key="agentProfile.message"/></p>
        </c:otherwise>
    </c:choose>
</div>

<!-- onsubmit="return validateUser(this)" -->
<div class="col-sm-7">
    <form:form commandName="agentuser" method="post" action="agentprofile" id="userForm" autocomplete="off" enctype="multipart/form-data"
               cssClass="well" >
        <form:hidden path="id"/>
        <form:hidden path="version"/>
        <form:hidden path="agent.aff_id"/>
        <input type="hidden" name="from" value="<c:out value="${param.from}"/>"/>

	    <c:if test="${param.from == 'list'}">
        <spring:bind path="agentuser.username">
        <div class="form-group${(not empty status.errorMessage) ? ' has-error' : ''}">
        </spring:bind>
            <appfuse:label styleClass="control-label" key="agent.username"/>
            <form:input cssClass="form-control" path="username" id="username"/>
            <form:errors path="username" cssClass="help-block"/>
        </div>
        </c:if>

        <spring:bind path="agentuser.agent.agent_Name">
        <div class="form-group${(not empty status.errorMessage) ? ' has-error' : ''}">
        </spring:bind>
            <appfuse:label key="agent.travel" styleClass="control-label"/>
            <form:input path="agent.agent_Name" id="passwordHint" cssClass="form-control" />
            <form:errors path="agent.agent_Name" cssClass="help-block"/>
        </div>

        <c:if test="${pageContext.request.remoteUser == agentuser.username}">
		    <form:hidden path="username"/>
            <span class="help-block">
                <a href="<c:url value="/updatePassword" />"><fmt:message key='updatePassword.changePasswordLink'/></a>
            </span>
        </c:if>

<%--         <spring:bind path="agentuser.passwordHint"> --%>
<%--         <div class="form-group${(not empty status.errorMessage) ? ' has-error' : ''}"> --%>
<%--         </spring:bind> --%>
<%--             <appfuse:label styleClass="control-label" key="user.passwordHint"/> --%>
<%--             <form:input path="passwordHint" id="passwordHint" cssClass="form-control" /> --%>
<%--             <form:errors path="passwordHint" cssClass="help-block"/> --%>
<!--         </div> -->

        <div class="row">
            <spring:bind path="agentuser.email">
            <div class="col-sm-6 form-group${(not empty status.errorMessage) ? ' has-error' : ''}">
            </spring:bind>
                <appfuse:label styleClass="control-label" key="user.email"/>
                <form:input cssClass="form-control" path="email" id="email"/>
                <form:errors path="email" cssClass="help-block"/>
            </div>
            
            <spring:bind path="agentuser.agent.fax1">
            <div class="col-sm-6 form-group${(not empty status.errorMessage) ? ' has-error' : ''}">
            </spring:bind>
                <appfuse:label key="agent.fax1" styleClass="control-label" />
                <form:input path="agent.fax1" cssClass="form-control" />
                <form:errors path="agent.fax1" cssClass="help-block"/>
            </div>
            
        </div>
        
        <div class="row">
            <spring:bind path="agentuser.phoneNumber">
            <div class="col-sm-6 form-group${(not empty status.errorMessage) ? ' has-error' : ''}">
            </spring:bind>
                <appfuse:label styleClass="control-label" key="user.phoneNumber"/><span class="required">&nbsp;*</span>
                <form:input cssClass="form-control" path="phoneNumber" id="phoneNumber"/>
                <form:errors path="phoneNumber" cssClass="help-block"/>
            </div>

            <spring:bind path="agentuser.agent.phone2">
            <div class="col-sm-6 form-group${(not empty status.errorMessage) ? ' has-error' : ''}">
            </spring:bind>
                <appfuse:label key="agent.mobilePhone" styleClass="control-label" /><span class="required">&nbsp;*</span>
                <form:input cssClass="form-control" path="agent.phone2"/>
                <form:errors path="agent.phone2" cssClass="help-block"/>
            </div>      
        </div>
        
        <div class="form-group">
            <appfuse:label styleClass="control-label" key="user.website"/>
            <form:input cssClass="form-control" path="website" id="website"/>
        </div>
        <div>
            <legend class="accordion-heading">
                <a data-toggle="collapse" href="#collapse-address"><fmt:message key="user.address.address"/></a>
            </legend>
            <div id="collapse-address" class="accordion-body collapse in">
	            <spring:bind path="agentuser.address.address">
            	<div class="form-group${(not empty status.errorMessage) ? ' has-error' : ''}">
            	</spring:bind>
                    <appfuse:label styleClass="control-label" key="user.address.address"/><span class="required">&nbsp;*</span>
                    <form:input cssClass="form-control" path="address.address" id="address.address"/>
	                <form:errors path="address.address" cssClass="help-block"/>
                </div>
                <div class="row">
		            <spring:bind path="agentuser.address.city">
		            <div class="col-sm-8 form-group${(not empty status.errorMessage) ? ' has-error' : ''}">
	            	</spring:bind>
                        <appfuse:label styleClass="control-label" key="user.address.city"/><span class="required">&nbsp;*</span>
                        <form:input cssClass="form-control" path="address.city" id="address.city"/>
		                <form:errors path="address.city" cssClass="help-block"/>
                    </div>

                    <div class="col-sm-4 form-group">
                        <appfuse:label styleClass="control-label" key="user.address.postalCode"/>
                        <form:input cssClass="form-control" path="address.postalCode" id="address.postalCode"/>
                    </div>
                </div>
	            <spring:bind path="agentuser.address.country">
            	<div class="form-group${(not empty status.errorMessage) ? ' has-error' : ''}">
            	</spring:bind>
                    <appfuse:label styleClass="control-label" key="user.address.country"/>&nbsp;<span class="required">*</span>
                    <appfuse:country name="address.country" prompt="" default="${agentuser.address.country}"/>
	                <form:errors path="address.country" cssClass="help-block"/>
                </div>
            </div>
        </div>

        <div class="row">
	    	<spring:bind path="agentuser.agent.ym_id">
	      	<div class="col-sm-6 ${(not empty status.errorMessage) ? ' has-error' : ''}">
    	  	</spring:bind>
          	<appfuse:label styleClass="control-label" key="agent.ym"/><span class="required">&nbsp;*</span>
          	<form:input cssClass="form-control" path="agent.ym_id"/>
          	<form:errors path="agent.ym_id" cssClass="help-block"/>
          	</div>

          <div class="col-sm-6">
          	<appfuse:label styleClass="control-label" key="agent.bbm"/>
          	<form:input path="agent.bbm_id" cssClass="form-control" />
          </div>

        </div>

		<div class="row">
<%-- 	         <spring:bind path="agentuser.agent.package_code"> --%>
<%-- 	  	      <div class="col-sm-12 form-group${(not empty status.errorMessage) ? ' has-error' : ''}"> --%>
<%-- 	      	  </spring:bind> --%>
<%-- 	            	<appfuse:label styleClass="control-label" key="agent.packageChoose"/> &nbsp;&nbsp;&nbsp;&nbsp; --%>
<%-- 			<form:radiobutton path="agent.package_code" value="101" id="pkgRadio1" onclick="javascript:travelModeCheck();"/><fmt:message key="agent.package1" />&nbsp;&nbsp;&nbsp;&nbsp; --%>
<%-- 			<form:radiobutton path="agent.package_code" value="102" id="pkgRadio2" onclick="javascript:travelModeCheck();"/><fmt:message key="agent.package2" />&nbsp;&nbsp;&nbsp;&nbsp; --%>
<%-- 			<form:radiobutton path="agent.package_code" value="103" id="pkgRadio3" onclick="javascript:travelModeCheck();"/><fmt:message key="agent.package3" /> --%>
<%-- 	            	<form:errors path="agent.package_code" cssClass="help-block has-error"/> --%>
<!-- 			</div> -->
		</div>

		<div class="form-group">        
			<c:choose> 
			  <c:when test="${agent.logoFileName == null}">
			            <appfuse:label key="agent.uploadLogo" styleClass="control-label"/>
			            <input type="file" name="file" id="file"/>
			            <form:errors path="agent.logoId" cssClass="help-block has-error"/>
			  </c:when>
			  <c:otherwise>
			            <appfuse:label key="agent.currentLogo" styleClass="control-label"/>
			            <div>
			            	<img src="<c:url value='/agent/getLogo?imagePath=${agent.logoFileName}' />" style="height:50px;padding-right: 2px;"/>
			            </div>
			            <div>		
				            <input type="file" name="file"/> &nbsp;<span style="color: red;font-size: small;"><fmt:message key="agent.edit.logo.remarks" /></span>
			            </div>
			  </c:otherwise>
			</c:choose>
        </div>

<c:choose>
    <c:when test="${param.from == 'list' or param.method == 'Add'}">
        <div class="form-group">
            <label class="control-label"><fmt:message key="userProfile.accountSettings"/></label>
            <label class="checkbox-inline">
                <form:checkbox path="enabled" id="enabled"/>
                <fmt:message key="user.enabled"/>
            </label>

            <label class="checkbox-inline">
                <form:checkbox path="accountExpired" id="accountExpired"/>
                <fmt:message key="user.accountExpired"/>
            </label>

            <label class="checkbox-inline">
                <form:checkbox path="accountLocked" id="accountLocked"/>
                <fmt:message key="user.accountLocked"/>
            </label>

            <label class="checkbox-inline">
                <form:checkbox path="credentialsExpired" id="credentialsExpired"/>
                <fmt:message key="user.credentialsExpired"/>
            </label>
        </div>
        <div class="form-group">
            <label for="userRoles" class="control-label"><fmt:message key="userProfile.assignRoles"/></label>
            <select id="userRoles" name="userRoles" multiple="true" class="form-control">
                <c:forEach items="${availableRoles}" var="role">
                <option value="${role.value}" ${fn:contains(user.roles, role.label) ? 'selected' : ''}>${role.label}</option>
                </c:forEach>
            </select>
        </div>
    </c:when>
    <c:when test="${not empty agentuser.username and param.from == 'list'}">
        <div class="form-group">
            <label class="control-label"><fmt:message key="agent.roles"/>:</label>
            <div class="readonly">
                <c:forEach var="role" items="${user.roleList}" varStatus="status">
                    <c:out value="${role.label}"/><c:if test="${!status.last}">,</c:if>
                    <input type="hidden" name="userRoles" value="<c:out value="${role.label}"/>"/>
                </c:forEach>
            </div>
            <form:hidden path="enabled"/>
            <form:hidden path="accountExpired"/>
            <form:hidden path="accountLocked"/>
            <form:hidden path="credentialsExpired"/>
        </div>
    </c:when>
</c:choose>
        <div class="form-group">
            <button type="submit" class="btn btn-primary" name="save" onclick="bCancel=false">
                <i class="icon-ok icon-white"></i> <fmt:message key="button.save"/>
            </button>

            <c:if test="${param.from == 'list' and param.method != 'Add'}">
              <button type="submit" class="btn btn-default" name="delete" onclick="bCancel=true;return confirmMessage(msgDelConfirm)">
                  <i class="icon-trash"></i> <fmt:message key="button.delete"/>
              </button>
            </c:if>

            <button type="submit" class="btn btn-default" name="cancel" onclick="bCancel=true">
                <i class="icon-remove"></i> <fmt:message key="button.cancel"/>
            </button>
        </div>
    </form:form>
</div>

<c:set var="scripts" scope="request">
<script type="text/javascript">
// This is here so we can exclude the selectAll call when roles is hidden
function onFormSubmit(theForm) {
    return validateUser(theForm);
}
</script>
</c:set>

<v:javascript formName="agentuser" staticJavascript="false"/>
<script type="text/javascript" src="<c:url value="/scripts/validator.jsp"/>"></script>

