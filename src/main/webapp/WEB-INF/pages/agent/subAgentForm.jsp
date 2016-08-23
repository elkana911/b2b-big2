<%@ include file="/common/taglibs.jsp"%>

<head>
    <title><fmt:message key="subAgentProfile.title"/></title>
    <meta name="menu" content="AdminMenu"/>
</head>

<!-- untuk popup delete message -->
<c:set var="delObject" scope="request"><fmt:message key="agentList.agent"/></c:set>
<script type="text/javascript">var msgDelConfirm =
   "<fmt:message key="delete.confirm"><fmt:param value="${delObject}"/></fmt:message>";
</script>

<div class="col-sm-2">
    <h2><fmt:message key="subAgentProfile.heading"/></h2>
    <c:choose>
        <c:when test="${param.from == 'list'}">
            <p><fmt:message key="agentProfile.admin.message"/></p>
        </c:when>
        <c:otherwise>
            <p><fmt:message key="agentProfile.message"/></p>
        </c:otherwise>
    </c:choose>
</div>
<div class="col-sm-7">
    <form:errors path="*" cssClass="alert alert-danger alert-dismissable" element="div"/>
    <form:form commandName="agentBean" method="post" action="../agent/subagentform" id="agentForm"
               cssClass="well" onsubmit="return validateAgent(this)">
<!--         airline_id berikut dipakai utk proses update data -->
        <form:hidden path="agent_id"/>

<!-- 		butuh hidden utk menampung parameter saat dipanggil pertama kali, soalnya isi parameter bisa kosong saat mau post event. jd variable dpt ditampung di input  -->
        <input type="hidden" name="method" value="<c:out value="${param.method}"/>"/>
        <input type="hidden" name="from" value="<c:out value="${param.from}"/>"/>

		<spring:bind path="agentBean.agent_id">
		<div class="form-group${(not empty status.errorMessage) ? ' has-error' : ''}">
		</spring:bind>
			<appfuse:label key="agent.agency" styleClass="control-label" />
			<form:select path="agent_id" cssClass="form-control input-sm">
				<form:options items="${wholesaler}" itemLabel="agentName" itemValue="agent_id" />
			</form:select>
			<form:errors path="agent_id" cssClass="help-block" />
		</div> 
		
        <spring:bind path="agentBean.agent_Name">
        <div class="form-group${(not empty status.errorMessage) ? ' has-error' : ''}">
        </spring:bind>
            <appfuse:label key="agent.name" styleClass="control-label" /><span class="required"> *</span>
            <form:input path="agent_Name" id="agentname" cssClass="form-control" />
            <form:errors path="agent_Name" cssClass="help-block"/>
        </div>
        <spring:bind path="agentBean.address1">
        <div class="form-group${(not empty status.errorMessage) ? ' has-error' : ''}">
        </spring:bind>
            <appfuse:label key="agent.address1" styleClass="control-label" />
            <form:input path="address1" id="agentaddress1" cssClass="form-control" />
            <form:errors path="address1" cssClass="help-block"/>
        </div>
        <spring:bind path="agentBean.city">
        <div class="form-group${(not empty status.errorMessage) ? ' has-error' : ''}">
        </spring:bind>
            <appfuse:label key="agent.city" styleClass="control-label" />
            <form:input path="city" id="agentcity" cssClass="form-control" />
            <form:errors path="city" cssClass="help-block"/>
        </div>
        <spring:bind path="agentBean.website1">
        <div class="form-group${(not empty status.errorMessage) ? ' has-error' : ''}">
        </spring:bind>
            <appfuse:label key="agent.website1" styleClass="control-label" />
            <form:input path="website1" id="agentwebsite1" cssClass="form-control" />
            <form:errors path="website1" cssClass="help-block"/>
        </div>




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
    return validateAgent(theForm);
}
</script>
</c:set>

<v:javascript formName="agentBean" cdata="false" dynamicJavascript="true" staticJavascript="false"/>
<script type="text/javascript" src="<c:url value="/scripts/validator.jsp"/>"></script>

