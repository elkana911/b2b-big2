<%@ include file="/common/taglibs.jsp"%>

<head>
    <title><fmt:message key="airlineProfile.heading"/></title>
    <meta name="menu" content="AirlineMenu"/>
</head>

<!-- untuk popup delete message -->
<c:set var="delObject" scope="request"><fmt:message key="airlineList.airline"/></c:set>
<script type="text/javascript">var msgDelConfirm =
   "<fmt:message key="delete.confirm"><fmt:param value="${delObject}"/></fmt:message>";
</script>

<div class="col-sm-2">
    <h2><fmt:message key="airlineProfile.heading"/></h2>
    <c:choose>
        <c:when test="${param.from == 'list'}">
            <p><fmt:message key="airlineProfile.admin.message"/></p>
        </c:when>
        <c:otherwise>
            <p><fmt:message key="airlineProfile.message"/></p>
        </c:otherwise>
    </c:choose>
</div>
<div class="col-sm-7">
    <form:errors path="*" cssClass="alert alert-danger alert-dismissable" element="div"/>
    <form:form commandName="airlineBean" method="post" action="../admin/airlineform" id="airlineForm"
               cssClass="well" onsubmit="return validateAirline(this)">
<!--         airline_id berikut dipakai utk proses update data -->
        <form:hidden path="airline_id"/>

<!-- 		butuh hidden utk menampung parameter saat dipanggil pertama kali, soalnya isi parameter bisa kosong saat mau post event. jd variable dpt ditampung di input  -->
        <input type="hidden" name="method" value="<c:out value="${param.method}"/>"/>
        <input type="hidden" name="from" value="<c:out value="${param.from}"/>"/>

        <spring:bind path="airlineBean.name">
        <div class="form-group${(not empty status.errorMessage) ? ' has-error' : ''}">
        </spring:bind>
            <appfuse:label styleClass="control-label" key="airline.name"/><span class="required"> *</span>
            <form:input cssClass="form-control" path="name" id="airlinename"/>
            <form:errors path="name" cssClass="help-block"/>
        </div>
        <spring:bind path="airlineBean.code">
        <div class="form-group${(not empty status.errorMessage) ? ' has-error' : ''}">
        </spring:bind>
            <appfuse:label styleClass="control-label" key="airline.code"/>
            <form:input cssClass="form-control" path="code" id="airlinecode"/>
            <form:errors path="code" cssClass="help-block"/>
        </div>
        <spring:bind path="airlineBean.hq">
        <div class="form-group${(not empty status.errorMessage) ? ' has-error' : ''}">
        </spring:bind>
            <appfuse:label styleClass="control-label" key="airline.hq"/>
            <form:input cssClass="form-control" path="hq" id="airlinehq"/>
            <form:errors path="hq" cssClass="help-block"/>
        </div>
        <spring:bind path="airlineBean.website1">
        <div class="form-group${(not empty status.errorMessage) ? ' has-error' : ''}">
        </spring:bind>
            <appfuse:label styleClass="control-label" key="airline.website1"/>
            <form:input cssClass="form-control" path="website1" id="airlinewebsite1"/>
            <form:errors path="website1" cssClass="help-block"/>
        </div>
        <spring:bind path="airlineBean.call_center">
        <div class="form-group${(not empty status.errorMessage) ? ' has-error' : ''}">
        </spring:bind>
            <appfuse:label styleClass="control-label" key="airline.call_center"/>
            <form:input cssClass="form-control" path="call_center" id="airlinecallcenter"/>
            <form:errors path="call_center" cssClass="help-block"/>
        </div>
        <spring:bind path="airlineBean.phone1">
        <div class="form-group${(not empty status.errorMessage) ? ' has-error' : ''}">
        </spring:bind>
            <appfuse:label styleClass="control-label" key="airline.phone1"/>
            <form:input cssClass="form-control" path="phone1" id="airlinephone1"/>
            <form:errors path="phone1" cssClass="help-block"/>
        </div>
        <spring:bind path="airlineBean.email1">
        <div class="form-group${(not empty status.errorMessage) ? ' has-error' : ''}">
        </spring:bind>
            <appfuse:label styleClass="control-label" key="airline.email1"/>
            <form:input cssClass="form-control" path="email1" id="airlineemail1"/>
            <form:errors path="email1" cssClass="help-block"/>
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
    return validateAirline(theForm);
}
</script>
</c:set>

<v:javascript formName="airlineBean" cdata="false" dynamicJavascript="true" staticJavascript="false"/>
<script type="text/javascript" src="<c:url value="/scripts/validator.jsp"/>"></script>

