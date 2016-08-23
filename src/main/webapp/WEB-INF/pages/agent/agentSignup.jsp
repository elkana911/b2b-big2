<%@ include file="/common/taglibs.jsp" %>

<%@ page import="net.tanesha.recaptcha.ReCaptcha" %>
<%@ page import="net.tanesha.recaptcha.ReCaptchaFactory" %>

<head>
    <title><fmt:message key="agent.signup.title"/></title>
</head>

<c:set var="errorsagreeterms">
	<fmt:message key="errors.agree.terms" />
</c:set>

<body class="signup"/>

<input id="errorsagreeterms" type="hidden" value="${errorsagreeterms}" />

<div class="col-sm-2">
    <h2><fmt:message key="agent.signup.heading"/></h2>
    <p><fmt:message key="agent.signup.message"/></p>
</div>
<div class="col-sm-7">


<!-- onsubmit="return validateSignup(this)" -->
<!-- utk validasi dengan asterisk, pastikan commandName sama dengan yg di validation.xml -->
    <form:form commandName="agentuser" method="post" action="agentsignup" autocomplete="on" enctype="multipart/form-data"
               cssClass="well" >
		<spring:bind path="agentuser.agent.agent_id">
		<div class="form-group${(not empty status.errorMessage) ? ' has-error' : ''}">
		</spring:bind>
			<appfuse:label key="agent.agency" styleClass="control-label" />
			<form:select path="agent.agent_id" cssClass="form-control input-sm">
				<form:options items="${wholesaler}" itemLabel="agentName" itemValue="agent_id" />
			</form:select>
			<form:errors path="agent.agent_id" cssClass="help-block" />
		</div>               
               
        <spring:bind path="agentuser.username">
        <div class="form-group${(not empty status.errorMessage) ? ' has-error' : ''}">
        </spring:bind>
            <appfuse:label styleClass="control-label" key="agent.username"/>
            <form:input cssClass="form-control" path="username" id="username" autofocus="true" onkeyup="nospaces(this)" autocomplete="off" />
            <form:errors path="username" cssClass="help-block"/>
        </div>
        
		<spring:bind path="agentuser.agent.agent_Name">
        <div class="form-group${(not empty status.errorMessage) ? ' has-error' : ''}">
        </spring:bind>
            <appfuse:label key="agent.travel" styleClass="control-label" /><span class="required">&nbsp;*</span>
            <form:input path="agent.agent_Name" id="travel" cssClass="form-control" maxlength="50"/>
            <form:errors path="agent.agent_Name" cssClass="help-block"/>
        </div>  

        <div class="row">
            <spring:bind path="agentuser.password">
            <div class="col-sm-6 form-group${(not empty status.errorMessage) ? ' has-error' : ''}">
            </spring:bind>
                <appfuse:label styleClass="control-label" key="user.password"/><span class="required">&nbsp;*</span>
                <form:password cssClass="form-control" path="password" id="password" showPassword="true"/>
                <form:errors path="password" cssClass="help-block"/>
            </div>          
            
            <spring:bind path="agentuser.confirmPassword">
            <div class="col-sm-6 form-group${(not empty status.errorMessage) ? ' has-error' : ''}">
            </spring:bind>
                <appfuse:label styleClass="control-label" key="user.confirmPassword"/><span class="required">&nbsp;*</span>
                <form:password cssClass="form-control" path="confirmPassword" showPassword="true"/>
                <form:errors path="confirmPassword" cssClass="help-block"/>
            </div>
        </div>
        
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
                <form:input cssClass="form-control" path="phoneNumber" id="phoneNumber" onkeyup="nospaces(this)"/>
                <form:errors path="phoneNumber" cssClass="help-block"/>
            </div>

            <spring:bind path="agentuser.agent.phone2">
            <div class="col-sm-6 form-group${(not empty status.errorMessage) ? ' has-error' : ''}">
            </spring:bind>
                <appfuse:label key="agent.mobilePhone" styleClass="control-label" /><span class="required">&nbsp;*</span>
                <form:input cssClass="form-control" path="agent.phone2" onkeyup="nospaces(this)"/>
                <form:errors path="agent.phone2" cssClass="help-block"/>
            </div>
        </div>
        
        <div class="form-group">
            <appfuse:label styleClass="control-label" key="user.website"/>
            <form:input cssClass="form-control" path="website" id="website" onkeyup="nospaces(this)"/>
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

            <div class="row">
	          <spring:bind path="agentuser.agent.ym_id">
    	      <div class="col-sm-6 form-group${(not empty status.errorMessage) ? ' has-error' : ''}">
        	  </spring:bind>
              	<appfuse:label styleClass="control-label" key="agent.ym"/>
              	<form:input cssClass="form-control" path="agent.ym_id" onkeyup="nospaces(this)"/>
              	<form:errors path="agent.ym_id" cssClass="help-block"/>
              </div>

              <div class="col-sm-6 form-group">
              	<appfuse:label styleClass="control-label" key="agent.bbm"/>
              	<form:input path="agent.bbm_id" cssClass="form-control" onkeyup="nospaces(this)"/>
              </div>

            </div>
            
			<c:if test="${ccode ne 113}">
			<div class="row">
	          <spring:bind path="agentuser.agent.package_code">
    	      <div class="col-sm-12 form-group${(not empty status.errorMessage) ? ' has-error' : ''}">
        	  </spring:bind>
              	<appfuse:label styleClass="control-label" key="agent.packageChoose"/> &nbsp;&nbsp;&nbsp;&nbsp;
				<form:radiobutton path="agent.package_code" value="101" id="pkgRadio1" onclick="javascript:travelModeCheck();"/><fmt:message key="agent.package1" />&nbsp;&nbsp;&nbsp;&nbsp;
				<form:radiobutton path="agent.package_code" value="102" id="pkgRadio2" onclick="javascript:travelModeCheck();"/><fmt:message key="agent.package2" />&nbsp;&nbsp;&nbsp;&nbsp;
				<form:radiobutton path="agent.package_code" value="103" id="pkgRadio3" onclick="javascript:travelModeCheck();"/><fmt:message key="agent.package3" />
              	<form:errors path="agent.package_code" cssClass="help-block has-error"/>
			  </div>
			</div>
			</c:if>


        </div>
        
        <div class="form-group">
            <appfuse:label key="agent.uploadLogo" styleClass="control-label"/>
<%--             <form:input path="agent.logoId" type="file" /> cant use this due string & byte[] difference--%>
            <input type="file" name="file" id="file" />&nbsp;<span style="color: red;font-size: small;"><fmt:message key="agent.edit.logo.remarks" /></span> 
            <form:errors path="agent.logoId" cssClass="help-block has-error"/>
        </div>
        
        <%
        /*
        // worked for localhost
        ReCaptcha c = ReCaptchaFactory.newReCaptcha(
                "6LfHEf0SAAAAACtwmEmjI9T6R775lWn01kc60Vpf" ,
                "6LfHEf0SAAAAAAVxAwOa9dcdXclAlCRKJ033ataQ" ,
                false);
	*/
		 
		//	working for server kuningan
			ReCaptcha c = null;
			if (request.isSecure()){
	        	c = ReCaptchaFactory.newSecureReCaptcha(
	    				"6Lel2v0SAAAAACfV0IlTZ37PyFw1z9uenqnAL32P", 
	    				"6Lel2v0SAAAAAHnayUEriPyoW4BuXtchoUgo_454", 
	    				false);
			}else{
	        	c = ReCaptchaFactory.newReCaptcha(
	    				"6Lel2v0SAAAAACfV0IlTZ37PyFw1z9uenqnAL32P", 
	    				"6Lel2v0SAAAAAHnayUEriPyoW4BuXtchoUgo_454", 
	    				false);
			}
	    	out.print(c.createRecaptchaHtml(null, null));
		%>
		
		<span class="help-block has-error">${errorcaptcha}</span>
		
		<div class="row">
			<div class="col-sm-12">
		        <label class="checkbox-inline">
		        	<input type="checkbox" id="agreeTerm1"/>
				    <fmt:message key="signup.agree.term">
				        <fmt:param><c:url value="/termsignup"/></fmt:param>
				    </fmt:message>
 	            </label>
			</div>
		</div>
		        		
        <div class="form-group text-right">
            <button type="submit" class="btn btn-primary" name="save" 
            onclick="bCancel=false; if (onSubmitClick()) splash(); else return false;">
                <i class="icon-ok icon-white"></i> <fmt:message key="button.register"/>
            </button>
            <button type="submit" class="btn btn-default" name="cancel" onclick="bCancel=true">
                <i class="icon-remove"></i> <fmt:message key="button.cancel"/>
            </button>
        </div>
                       
    </form:form>
</div>

<!-- kuputuskan kubuang utk sementara karena bikin confused. utk learn validation http://jroller.com/raible/entry/using_commons_validator_with_spring -->
<!-- penggunaan asterisk dilakukan manual saja, ga usah otomatis2an krn took hours of my life. i prefer use hibernate validator for future version -->
<c:set var="scripts" scope="request">
	<script type="text/javascript">
		function nospaces(t){
		    if(t.value.match(/\s|\./g)){
		        alert('Username cannot have spaces or dots');
		        t.value=t.value.replace(/\s/g,'');
		    }
		}
		
		function onSubmitClick(){
    		if (!document.getElementById('agreeTerm1').checked){
    			alert($("#errorsagreeterms").val());
    			return false;
    		}

    		return true;
		}
		
	</script>

</c:set>