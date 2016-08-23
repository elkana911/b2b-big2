<%@ include file="/common/taglibs.jsp"%>

<head>
    <title><fmt:message key="airportProfile.heading"/></title>
    <meta name="menu" content="AirportMenu"/>
</head>

<!-- untuk popup delete message -->
<c:set var="delObject" scope="request"><fmt:message key="airportList.airport"/></c:set>
<script type="text/javascript">var msgDelConfirm =
   "<fmt:message key="delete.confirm"><fmt:param value="${delObject}"/></fmt:message>";
</script>

<div class="col-sm-2">
    <h2><fmt:message key="airportProfile.heading"/></h2>
    <c:choose>
        <c:when test="${param.from == 'list'}">
            <p><fmt:message key="airportProfile.admin.message"/></p>
        </c:when>
        <c:otherwise>
            <p><fmt:message key="airportProfile.message"/></p>
        </c:otherwise>
    </c:choose>
</div>
<div class="col-sm-7">
    <form:errors path="*" cssClass="alert alert-danger alert-dismissable" element="div"/>
    <form:form commandName="airportBean" method="post" action="../admin/airportform" id="airportForm"
               cssClass="well" onsubmit="return validateAirport(this)">
<!--         airline_id berikut dipakai utk proses update data -->
        <form:hidden path="airport_id"/>

<!-- 		butuh hidden utk menampung parameter saat dipanggil pertama kali, soalnya isi parameter bisa kosong saat mau post event. jd variable dpt ditampung di input  -->
        <input type="hidden" name="method" value="<c:out value="${param.method}"/>"/>
        <input type="hidden" name="from" value="<c:out value="${param.from}"/>"/>

        <spring:bind path="airportBean.airport_name">
        <div class="form-group${(not empty status.errorMessage) ? ' has-error' : ''}">
        </spring:bind>
            <appfuse:label key="airport.name" styleClass="control-label" /><span class="required"> *</span>
            <form:input path="airport_name" id="airportname" cssClass="form-control" />
            <form:errors path="airport_name" cssClass="help-block"/>
        </div>
        <spring:bind path="airportBean.iata">
        <div class="form-group${(not empty status.errorMessage) ? ' has-error' : ''}">
        </spring:bind>
            <appfuse:label key="airport.iata" styleClass="control-label"/>
            <form:input path="iata" id="airportiata" cssClass="form-control" />
            <form:errors path="iata" cssClass="help-block"/>
        </div>
        <spring:bind path="airportBean.city">
        <div class="form-group${(not empty status.errorMessage) ? ' has-error' : ''}">
        </spring:bind>
            <appfuse:label key="airport.city" styleClass="control-label" />
            <form:input path="city" id="airlinecity" cssClass="form-control" />
            <form:errors path="city" cssClass="help-block"/>
        </div>
        <spring:bind path="airportBean.country">
        <div class="form-group${(not empty status.errorMessage) ? ' has-error' : ''}">
        </spring:bind>
            <appfuse:label key="airport.country" styleClass="control-label" />
			<form:select path="country" id="airlinecountry" cssClass="form-control" items='${countries}' itemValue="country_id" itemLabel="country_name" />
            <form:errors path="country" cssClass="help-block"/>
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
    return validateAirport(theForm);
}
</script>
</c:set>

<v:javascript formName="airportBean" cdata="false" dynamicJavascript="true" staticJavascript="false"/>
<script type="text/javascript" src="<c:url value="/scripts/validator.jsp"/>"></script>

