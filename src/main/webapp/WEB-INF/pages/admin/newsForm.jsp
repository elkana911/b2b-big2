<%@ include file="/common/taglibs.jsp"%>

<head>
    <title><fmt:message key="newsProfile.heading"/></title>
    <meta name="menu" content="FeedsMenu"/>
</head>

<!-- untuk popup delete message -->
<c:set var="delObject" scope="request"><fmt:message key="airlineList.airline"/></c:set>
<script type="text/javascript">var msgDelConfirm =
   "<fmt:message key="delete.confirm"><fmt:param value="${delObject}"/></fmt:message>";
</script>

<div class="col-sm-2" style="margin-bottom: 1em">
    <h2><fmt:message key="newsProfile.heading"/></h2>
    <c:choose>
        <c:when test="${param.from == 'list'}">
            <p><fmt:message key="newsProfile.admin.message"/></p>
        </c:when>
        <c:otherwise>
            <p><fmt:message key="newsProfile.message"/></p>
        </c:otherwise>
    </c:choose>
    <button type="button" class="btn btn-info" style="width: 8em" onclick="history.go(-1);return true;">
        <i class="icon-remove"></i> <fmt:message key="button.back"/>
    </button>
</div>
<div class="col-sm-7">
    <form:errors path="*" cssClass="alert alert-danger alert-dismissable" element="div"/>
    <form:form commandName="newsBean" method="post" action="../admin/newsform" id="newsForm"
               cssClass="well" onsubmit="return validateNews(this)" enctype="multipart/form-data">
<!--         airline_id berikut dipakai utk proses update data -->
        <form:hidden path="id"/>
        <form:hidden path="thumbId"/>

<!-- 		butuh hidden utk menampung parameter saat dipanggil pertama kali, soalnya isi parameter bisa kosong saat mau post event. jd variable dpt ditampung di input  -->
        <input type="hidden" name="method" value="<c:out value="${param.method}"/>"/>
        <input type="hidden" name="from" value="<c:out value="${param.from}"/>"/>

        <div class="checkbox-inline">
            <appfuse:label styleClass="control-label" key="feeds.headline"/>
            <form:checkbox path="headline" value="1" />
            <form:errors path="headline" cssClass="help-block"/>
        </div>

        <spring:bind path="newsBean.title">
        <div class="form-group${(not empty status.errorMessage) ? ' has-error' : ''}">
        </spring:bind>
            <appfuse:label styleClass="control-label" key="feeds.title"/><span class="required"> *</span>
            <form:input cssClass="form-control" path="title" required="required" maxlength="100"/>
            <form:errors path="title" cssClass="help-block"/>
        </div>

        <spring:bind path="newsBean.remarks">
        <div class="form-group${(not empty status.errorMessage) ? ' has-error' : ''}">
        </spring:bind>
            <appfuse:label styleClass="control-label" key="feeds.remarks"/><span class="required"> *</span>
            <form:textarea path="remarks" rows="8" cols="60" cssClass="form-control" />
            <form:errors path="remarks" cssClass="help-block"/>
        </div>

		<div class="form-group well">
			<c:choose>
				<c:when test="${newsBean.thumbId == null}">
		            <appfuse:label key="feeds.uploadThumb" styleClass="control-label"/>
		            <input type="file" name="thumbfile" id="thumbfile" />&nbsp;<span style="color: red;font-size: small;"><fmt:message key="feeds.thumb.remarks" /></span> 
		            <form:errors path="thumbId" cssClass="help-block has-error"/>
				</c:when>
				<c:otherwise>
		            <appfuse:label key="feeds.currentThumb" styleClass="control-label"/>
			            <div>
			            	<img class="img-thumbnail" src="<c:url value='/feeds/getThumb?imagePath=${newsBean.thumbId}' />" style="height:100px;"/>
			            </div>
		            <input type="file" name="thumbfile" id="thumbfile" />&nbsp;<span style="color: red;font-size: small;"><fmt:message key="feeds.thumb.remarks" /></span> 
		            <form:errors path="thumbId" cssClass="help-block has-error"/>
				</c:otherwise>
			</c:choose>
		</div> 

        <spring:bind path="newsBean.externalUrl">
        <div class="form-group${(not empty status.errorMessage) ? ' has-error' : ''}">
        </spring:bind>
            <appfuse:label styleClass="control-label" key="feeds.externalUrl"/>
            <form:input cssClass="form-control" path="externalUrl" maxlength="255" />
            <form:errors path="externalUrl" cssClass="help-block"/>
        </div>


        <div class="form-group">
            <button type="submit" class="btn btn-primary" name="save" onclick="bCancel=false">
                <i class="icon-ok icon-white"></i> <fmt:message key="button.save"/>
            </button>

            <c:if test="${param.from == 'list' and param.method != 'Add'}">
              <button type="submit" class="btn btn-danger" name="delete" onclick="bCancel=true;return confirmMessage(msgDelConfirm)">
                  <i class="icon-trash"></i> <fmt:message key="button.delete"/>
              </button>
            </c:if>

            <button type="submit" class="btn btn-info" name="cancel" onclick="bCancel=true">
                <i class="icon-remove"></i> <fmt:message key="button.cancel"/>
            </button>
        </div>
    </form:form>
</div>

<c:set var="scripts" scope="request">
<script type="text/javascript">
// This is here so we can exclude the selectAll call when roles is hidden
function onFormSubmit(theForm) {
    return validateNews(theForm);
}
</script>
</c:set>

<v:javascript formName="newsBean" cdata="false" dynamicJavascript="true" staticJavascript="false"/>
<script type="text/javascript" src="<c:url value="/scripts/validator.jsp"/>"></script>

