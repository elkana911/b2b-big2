<%@ include file="/common/taglibs.jsp"%>


<%
	//unused, for learn only
	String adultCount = (String) request.getParameter("searchForm.adult"); 		//dipakai oleh contacts-b2b utk diassign ke infant
	pageContext.setAttribute("adultCount", adultCount == null ? "1" : adultCount);	//akan dibaca oleh adult list dibagian contact

%> 

<c:set var="gelar">Mr,Mrs,Ms</c:set>
<c:set var="specialReq">WheelChair</c:set>
<spring:message code="ticket.passengerContact" var="dayScheduleExample"/>

<c:if test="${ (listDepart != null && fn:length(listDepart) > 0) || (listReturn != null && fn:length(listReturn) > 0)}">
<div class="row">
  <div class="col-sm-7" id="bookArea" style="min-width: 640px;">	
	<div class="row well" id="adultForm"  >
		<div class="col-sm-1">
			<appfuse:label key="flight.adult"/>
		</div>
		<div class="col-sm-10">
			<table id="tableContact" width="420" border="0" style="font-size:11px">
				<c:forEach items="${maBookingForm.adult}" varStatus="loop">
					<tr>
						<td>${loop.index+1}</td>
					    <td>
						  <spring:bind path="maBookingForm.adult[${loop.count-1}].title">
 							  <div class="${(not empty status.errorMessage) ? ' has-error' : ''}">
						  <form:errors path="adult[${loop.count-1}].title" cssClass="help-block" />
						  <form:select path="adult[${loop.count-1}].title">
						        <form:option value="">--</form:option>
						        <form:option value="MR">Mr.</form:option>
						        <form:option value="MRS">Mrs.</form:option>
						        <form:option value="MS">Ms.</form:option>
					      </form:select>
					      </div>
					      </spring:bind>
					    </td>
						<td>
							<spring:bind path="maBookingForm.adult[${loop.count-1}].fullName">
							<div class="${(not empty status.errorMessage) ? ' has-error' : ''}">
								<form:errors path="adult[${loop.count-1}].fullName" cssClass="help-block" />
								<c:choose>
								    <c:when test="${loop.index == 0}">
										<form:input path="adult[${loop.count-1}].fullName" placeholder="* <Full Name>" onchange="toggleAdultFullName(${loop.index},this);"/>
									</c:when>
								    <c:otherwise>
										<form:input path="adult[${loop.count-1}].fullName" placeholder="* <Full Name>" />
								    </c:otherwise>
								</c:choose>
								
								<c:if test="${status.error}">
								        <script type="text/javascript">
								           document.getElementById("adult'${loop.count-1}'.fullName").focus();
		        						</script>
		        				</c:if>
							</div>
							</spring:bind>
						</td>
						<td>
							<spring:bind path="maBookingForm.adult[${loop.count-1}].idCard">
							<div class="${(not empty status.errorMessage) ? ' has-error' : ''}">
							</spring:bind>
								<form:errors path="adult[${loop.count-1}].idCard" cssClass="help-block" />
								<form:input path="adult[${loop.count-1}].idCard" placeholder="<ID No>" />
							</div>
						</td>
					    <td style="width:12%;display:none;" id="showBirthday${loop.count-1}">
							<spring:bind path="maBookingForm.adult[${loop.count-1}].birthday">
							<div class="${(not empty status.errorMessage) ? ' has-error' : ''}">
	                    	</spring:bind>
								<form:errors path="adult[${loop.count-1}].birthday" cssClass="help-block" />
						    	<form:input path="adult[${loop.count-1}].birthday" cssStyle="width:100%" placeholder="* ${fn:toLowerCase(datePattern)}" />
	                    		<appfuse:country name="adult[${loop.count-1}].countryCode" prompt="- Nationality -" cssStyle="margin-bottom:0.4em" default="ID" />
							</div>	                    		
					    </td>
					    <td>
						  <form:select path="adult[${loop.count-1}].specialRequest">
						        <form:option value="">--</form:option>
						        <form:option value="WHEELCHAIR">WheelChair</form:option>
					      </form:select>
						</td>					
						<td>
							<button id="speedAdultButton${loop.count-1}" onclick="return false;" class="speedButton">&gt;&gt;</button>
						</td>					
					</tr>
				</c:forEach>
			</table>
		</div>
	</div>

	<c:if test="${maBookingForm.searchForm.children > 0}">
	<div class="row well" id="childForm" >
		<div class="col-sm-1">
			<appfuse:label key="flight.children"/>
		</div>
		<div class="col-sm-10">
			<table width="400" border="0" style="font-size:11px">
				<c:forEach items="${maBookingForm.child}" varStatus="loop">
					<tr>
						<td>${loop.index+1}</td>
	
						    <td>
							  <form:select path="child[${loop.count-1}].title">
							        <form:option value="">--</form:option>
							        <form:option value="MR">Mr.</form:option>
							        <form:option value="MRS">Mrs.</form:option>
							        <form:option value="MS">Ms.</form:option>
						      </form:select>
						    </td>
						    
							<td><form:input path="child[${loop.count-1}].fullName"  placeholder="* <Full Name>" /></td>
							<td><form:input path="child[${loop.count-1}].idCard" placeholder="<ID No>" /></td>
						    <td style="width:10%">
								<spring:bind path="maBookingForm.child[${loop.count-1}].birthday">
								<div class="${(not empty status.errorMessage) ? ' has-error' : ''}">
		                    	</spring:bind>
									<form:errors path="child[${loop.count-1}].birthday" cssClass="help-block" />
							    	<form:input path="child[${loop.count-1}].birthday" placeholder="* ${fn:toLowerCase(datePattern)}"  />
							    </div>
						    </td>
	
						    <td>
							  <form:select path="child[${loop.count-1}].specialRequest">
							        <form:option value="">--</form:option>
							        <form:option value="WHEELCHAIR">WheelChair</form:option>
						      </form:select>
							</td>
							<td>
								<button id="speedChildButton${loop.count-1}" onclick="return false;" class="speedButton">&gt;&gt;</button>
							</td>					
					</tr>
				</c:forEach>
			</table>			
		</div>			
	
	</div>
	</c:if>

	<c:if test="${maBookingForm.searchForm.infants > 0}">
	<div class="row well" id="infantForm">
		<div class="col-sm-1">
			<appfuse:label key="flight.infant"/>
		</div>
		<div class="col-sm-10">
			<table width="420" border="0" style="font-size:11px">
				<c:forEach items="${maBookingForm.infant}" varStatus="loop">
					<tr>
						<td>${loop.index+1}</td>	
						    <td>
							  <form:select path="infant[${loop.count-1}].title">
							        <form:option value="">--</form:option>
							        <form:option value="MSTR">Mstr.</form:option>
							        <form:option value="MISS">Miss.</form:option>
						      </form:select>
						    </td>
						    
							<td><form:input path="infant[${loop.count-1}].fullName"  placeholder="* <Full Name>" /></td>
							<td><form:input path="infant[${loop.count-1}].idCard" placeholder="<ID No>" /></td>
						    <td style="width:10%">
								<spring:bind path="maBookingForm.infant[${loop.count-1}].birthday">
								<div class="${(not empty status.errorMessage) ? ' has-error' : ''}">
		                    	</spring:bind>
									<form:errors path="infant[${loop.count-1}].birthday" cssClass="help-block" />
							    	<form:input path="infant[${loop.count-1}].birthday" placeholder="* ${fn:toLowerCase(datePattern)}"  />
							    </div>
						    </td>
						    <td>
<!-- 							    	anaknya siapa, mengacu pada adult item -->
							  <form:select path="infant[${loop.count-1}].num">
								    <form:option value="0">--</form:option>
									<c:forEach var="i" begin="1" end="${maBookingForm.searchForm.adult}">
								        <form:option value="${i}">${i}</form:option>
									</c:forEach>
						      </form:select>

							</td>					
							<td>
								<button id="speedInfantButton${loop.count-1}" onclick="return false;" class="speedButton">&gt;&gt;</button>
							</td>					
					</tr>
				</c:forEach>
			</table>					
		</div>			
	</div>
	</c:if>

	<div class="row well" id="contactForm">
		<div class="col-sm-1">
			<appfuse:label key="flight.contact"/>
		</div>
		<div class="col-sm-10">
			<table width="420" border="0" style="font-size:11px">
				<tr>
				    <td>Customer</td>			    
					<td>
						<spring:bind path="maBookingForm.customer.fullName">
						<div class="${(not empty status.errorMessage) ? ' has-error' : ''}">
							<form:errors path="customer.fullName" cssClass="help-block" />
							<form:input path="customer.fullName"  placeholder="* <Full Name>" />
							<c:if test="${status.error}">
							        <script type="text/javascript">
							            document.getElementById('customer.fullName').focus();
	        						</script>
	        				</c:if>
						</div>				
						</spring:bind>									
					</td>
					<td>
						<spring:bind path="maBookingForm.customer.phone1">
						<div class="${(not empty status.errorMessage) ? ' has-error' : ''}">
							<form:errors path="customer.phone1" cssClass="help-block" />
							<form:input path="customer.phone1"  placeholder="* <Mobile Phone>" />
							<c:if test="${status.error}">
							        <script type="text/javascript">
							            document.getElementById('customer.phone1').focus();
	        						</script>
	        				</c:if>
						</div>				
						</spring:bind>									
					</td>
					<td><form:input path="customer.phone2"  placeholder="<Other Phone>" /></td>
					<td>
						<button id="speedCCustButton" onclick="return false;" class="speedButton">&gt;&gt;</button>
					</td>					
					
				</tr>
				<tr>
				    <td>Agent</td>			    
					<td><form:input path="customer.agentName"  placeholder="* <Full Name>" /></td>
					<td><form:input path="customer.agentEmail"  placeholder="* <Agent Email>" /></td>
					<td><form:input path="customer.agentPhone1"  placeholder="<Agent Phone>" /></td>
				</tr>
			</table>					
		</div>			
	</div>
  </div> <!-- bookArea -->
  <div class="col-sm-5">
	<!--<span class="h4"><fmt:message key="menu.ticketDetails" /></span>   -->
  	<div id="ticketDtlSide">
  	</div>
  </div>	
</div>		
	<div class="row hidden">
		<div class="col-sm-12">
	        <label class="checkbox-inline">
				<form:errors path="agreeInsurance" cssClass="help-block" />
                <form:checkbox path="agreeInsurance" id="agreeInsurance1"/>
			    <fmt:message key="flight.b2b.agree.insurance">
			        <fmt:param><c:url value="/travelinsurance"/></fmt:param>
			    </fmt:message>
				
	           </label>
		</div>
	</div>

<div class="row" style="padding:1em">
	<div class="col-sm-12" style="font-size: smaller;padding: 1em;background-color: rgb(187, 105, 136);color: white;">
		<strong><fmt:message key="message.important"/></strong>
		<br><fmt:message key="message.beforeBook"/>
	<%-- 	<fmt:message key="message.beforeBook"/> --%>
	</div>
</div>

	<div class="row">
<!-- 		<div class="col-sm-4"> -->
<!-- 	        <label class="checkbox-inline"> -->
<!-- 	        	<input type="checkbox" id="agreeTerm1" checked="checked"/> -->
<%-- 			    <fmt:message key="flight.b2b.agree.term"> --%>
<%-- 			        <fmt:param><c:url value="/travelinsurance"/></fmt:param> --%>
<%-- 			    </fmt:message> --%>
<!-- 	        </label> -->
<!-- 		</div> -->
<!-- 		<div class="col-sm-4"> -->
<!-- 			<button class="btn btn-primary text-right"  -->
<!-- 				onclick="onTestClick();return false;" style="float: right;width:200px;"> -->
<!-- 				<i class="icon-upload icon-white"></i> -->
<%-- 				<fmt:message key="button.test" /> --%>
<!-- 			</button> -->
<!-- 		</div> -->
		<div class="col-sm-12">
			<button name="book_Param" class="btn btn-primary text-right"
				onclick="bCancel=false; if (onBookClick()) splash(); else return false;" style="float: right;width:200px">
				<i class="icon-upload icon-white"></i>
				<fmt:message key="button.book" />
			</button>
		</div>
	</div>

</c:if>


<script type="text/javascript">
	//Notes: utk insurance, please confirm that all passengers are below 75 years old

	function onTestClick(){
		//$("#adult0.fullName").val("eric");	ga ngefek
		$('input[name="adult[0].fullName"]').val("lia");
		$('input[name="adult[0].idCard"]').val("123456789012345");
		$('select[name="adult[0].title"]').val("MRS");
		$('input[name="customer.fullName"]').val("eric elkana");
		$('input[name="customer.phone1"]').val("087886283377");
		$('input[name="customer.phone2"]').val("021234567");

		$('input[name="adult[1].fullName"]').val("lia perkasa");
		$('input[name="adult[1].idCard"]').val("444422233");
		$('select[name="adult[1].title"]').val("MRS");

		$('input[name="infant[0].fullName"]').val("jack");
		$('input[name="infant[0].birthday"]').val("01/11/2014");
		$('select[name="infant[0].title"]').val("MSTR");
		$('input[name="infant[1].fullName"]').val("jackie");
		$('input[name="infant[1].birthday"]').val("02/11/2014");
		$('select[name="infant[1].title"]').val("MISS");
		
		//alert('haha');
	}

</script>