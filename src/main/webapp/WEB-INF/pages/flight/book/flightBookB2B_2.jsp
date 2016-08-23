<%@ include file="/common/taglibs.jsp"%>

<c:set var="rowColor1" value="panel panel-default" scope="page"/>
<c:set var="rowColor2" value="panel-heading" scope="page"/>

<c:forEach items="${fn:split(bookDtl.airlines_iata,',')}" varStatus="i" var="iata" >
	<c:choose>
	    <c:when test="${iata == 'JT' || iata == 'IW' || iata == 'ID'}">
	      <c:set var="rowColor1" value="panel panel-default-red" scope="page"/>
	      <c:set var="rowColor2" value="panel-heading-red" scope="page"/>
	      <c:if test="${iata == 'JT'}">
		      <c:if test="${i.index == 0 }">
			      	<c:set var="airlineImage0" value="/images/airline/lionair-med.png" scope="page"/>
		      </c:if>
		      <c:if test="${i.index == 1 }">
			      	<c:set var="airlineImage1" value="/images/airline/lionair-med.png" scope="page"/>
		      </c:if>
	      </c:if>
	      <c:if test="${iata == 'IW'}">
		      <c:if test="${i.index == 0 }">
			      	<c:set var="airlineImage0" value="/images/airline/wings-med.png" scope="page"/>
		      </c:if>
		      <c:if test="${i.index == 1 }">
			      	<c:set var="airlineImage1" value="/images/airline/wings-med.png" scope="page"/>
		      </c:if>
		      <c:if test="${i.index == 2 }">
			      	<c:set var="airlineImage2" value="/images/airline/wings-med.png" scope="page"/>
		      </c:if>
	      </c:if>
	      <c:if test="${iata == 'ID'}">
		      <c:if test="${i.index == 0 }">
			      	<c:set var="airlineImage0" value="/images/airline/batik-med.png" scope="page"/>
		      </c:if>
		      <c:if test="${i.index == 1 }">
			      	<c:set var="airlineImage1" value="/images/airline/batik-med.png" scope="page"/>
		      </c:if>
		      <c:if test="${i.index == 2 }">
			      	<c:set var="airlineImage2" value="/images/airline/batik-med.png" scope="page"/>
		      </c:if>
	      </c:if>
	    </c:when>
	    <c:when test="${iata == 'SJ'}">
	      <c:set var="rowColor1" value="panel panel-default-blue" scope="page"/>
	      <c:set var="rowColor2" value="panel-heading-blue" scope="page"/>
	      <c:set var="airlineImage0" value="/images/airline/sriwijaya-med.png" scope="page"/>
	    </c:when>
	    <c:when test="${iata == 'QG'}">
	      <c:set var="rowColor1" value="panel panel-default-green" scope="page"/>
	      <c:set var="rowColor2" value="panel-heading-green" scope="page"/>
	      <c:set var="airlineImage0" value="/images/airline/citilink-med.png" scope="page"/>
	    </c:when>
	    <c:when test="${iata == 'GA'}">
	      <c:set var="rowColor1" value="panel panel-default-blue" scope="page"/>
	      <c:set var="rowColor2" value="panel-heading-blue" scope="page"/>
	      <c:set var="airlineImage0" value="/images/airline/garuda-med.png" scope="page"/>
	    </c:when>
	    <c:when test="${iata == 'KD'}">
	      <c:set var="rowColor1" value="panel panel-default-blue" scope="page"/>
	      <c:set var="rowColor2" value="panel-heading-blue" scope="page"/>
	      <c:set var="airlineImage0" value="/images/airline/kalstar-med.png" scope="page"/>
	    </c:when>
	    <c:when test="${iata == 'QZ'}">
	      <c:set var="rowColor1" value="panel panel-default-red" scope="page"/>
	      <c:set var="rowColor2" value="panel-heading-red" scope="page"/>
	      <c:set var="airlineImage0" value="/images/airline/airasia-med.png" scope="page"/>
	    </c:when>
	    <c:otherwise>
	      	<c:set var="airlineImage0" value="/images/airline/batik-med.png" scope="page"/>
	    </c:otherwise>
 	</c:choose>
</c:forEach>
<c:set var="id" value="${bookDtl.id}" scope="page"/>
<div class="row">
	<div class="col-xs-6 col-sm-3">
		<c:choose>
		    <c:when test="${bookDtl.status == 'XX'}">
				<span style="font-size:xx-large;font-weight: bolder;padding: 0 6px;text-decoration:line-through;">${bookDtl.code}</span>
			</c:when>
		    <c:otherwise>
				<span style="font-size:xx-large;font-weight: bolder;padding: 0 6px;">${bookDtl.code}</span>
		    </c:otherwise>
		</c:choose>
		
	</div>
	<div class="col-xs-6 col-sm-9" style="text-align: right;float: right;">
		<c:choose>
		    <c:when test="${fn:length(fn:split(bookDtl.airlines_iata,',')) == 1}">
				<img src="<c:url value='${airlineImage0}'/>" class="airlineLogo-med"/>
			</c:when>
		    <c:when test="${fn:length(fn:split(bookDtl.airlines_iata,',')) == 2}">
				<img src="<c:url value='${airlineImage1}'/>" class="airlineLogo-med"/>
				<img src="<c:url value='${airlineImage0}'/>" class="airlineLogo-med"/>
			</c:when>
		    <c:when test="${fn:length(fn:split(bookDtl.airlines_iata,',')) == 3}">
				<img src="<c:url value='${airlineImage2}'/>" class="airlineLogo-med"/>
				<img src="<c:url value='${airlineImage1}'/>" class="airlineLogo-med"/>
				<img src="<c:url value='${airlineImage0}'/>" class="airlineLogo-med"/>
			</c:when>
		</c:choose>
	</div>		
</div>

<div class="row">
	<div class="col-sm-12" >
		<div class="${rowColor1}">
			<div class="${rowColor2}">
				 <h3 class="panel-title"><strong>Itinerary</strong></h3>
			</div>
			<display:table name="bookDtl.itineraries" id="itineraries" cellspacing="0" cellpadding="0" requestURI="" keepStatus="true"
							decorator="com.big.web.b2b_big2.flight.decorator.FlightB2BItinerariesTableDecorator"											   
							class="table table-condensed table-striped table-hover" export="false">
				<display:column titleKey="report.no" media="all" style="width: 2%">
					${itineraries_rowNum}
				</display:column>
				<display:column property="flightNo" titleKey="flightList.flightNum" media="all"/>
		       	<display:column property="className" titleKey="flightList.class" media="all"/>
		       	<display:column property="depart" titleKey="flightList.depart" media="all"/>
		       	<display:column property="arrival" titleKey="flightList.arrival" media="all"/>
			</display:table>
		</div>
	</div>	
</div>

<div class="row">
	<div class="col-sm-12" >
		<div class="${rowColor1}">
			<div class="${rowColor2}">
				<h3 class="panel-title"><strong>Passengers</strong></h3>
			</div>
			<display:table name="bookDtl.pnr" id="passengers" cellspacing="0" cellpadding="0" requestURI="" keepStatus="true"
				class="table table-condensed table-striped table-hover" export="false">
				<display:column titleKey="report.no" media="all" style="width: 2%">
					${passengers_rowNum}
				</display:column>
		       	<display:column property="title" titleKey="pnr.title" media="all"/>
				<display:column property="fullName" titleKey="pnr.nameFull" media="all" class="pnrNameCell"/>
				<c:if test="${bookDtl.showBirthday()}">				
			       	<display:column property="birthday" titleKey="pnr.birthday" class="pnrNameCell"
			                        media="all" format="{0,date,dd-MMM-yyyy}"/>
					<display:column property="countryCode" titleKey="pnr.country" media="all" class="pnrNameCell"/>
				</c:if>
				<display:column property="idCard" titleKey="pnr.idCard" media="all"/>
		       	<display:column property="specialRequest" titleKey="pnr.ssr" media="all"/>
		       	<display:column property="ticketNo" titleKey="pnr.ticketNo" media="all"/>
			</display:table>
		</div>
	</div>
</div>

<div class="row">
	<div class="col-sm-12" >
		<div class="${rowColor1}">
 			<div class="${rowColor2}">
 				<h3 class="panel-title"><strong>Customer Contact</strong></h3>
 			</div>
			<table class="table table-condensed">
				<thead>
                          <tr>
  							<td class="text-left"><strong>Name</strong></td>
  							<td class="text-left"><strong>Phone</strong></td>
                          </tr>
				</thead>
				<tbody>
					<tr>
						<td class="text-left" style="text-transform: uppercase;">${bookDtl.cust_name}</td>
						<td class="text-left">${bookDtl.cust_phone1}</td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>
</div> 

<div class="row">
	<div class="col-sm-12">
		<div class="${rowColor1}">
  			<div class="${rowColor2}">
 				<h3 class="panel-title"><strong>Payment Details</strong></h3>
  			</div>
   			<div class="panel-body">
				<form:form commandName="bookDtl" method="post" autocomplete="off"
					action="${ctx}/flight/b2b/booking/${id}">
					<form:hidden path="code"/>
					<form:hidden path="status"/>
					<form:hidden path="insurance"/>
					<form:hidden path="insuranceCommission"/>
					<form:hidden path="user"/>
					<form:hidden path="user.email"/>
					<table class="table table-condensed">
					   	<thead>
							<tr>
								<th></th>
								<th>NTSA</th>
								<th>COMMISSION</th>
								<th>PAX</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td><strong>TIKET</strong></td>
								<td><a href='#'><span id="nta${index}"><fmt:formatNumber value="${bookDtl.nta}" minFractionDigits="0"/></span></a></td>
								<td><span id="ntaCommission${index }"><fmt:formatNumber value="${bookDtl.ntaCommission}" minFractionDigits="0"/></span></td>
								<td><span id="ntaPax${index }"><fmt:formatNumber value="${bookDtl.amountTicket}" minFractionDigits="0"/></span></td>
							</tr>
							<tr style="display: none">
								<td>
									<label class="checkbox-inline">
								        <form:checkbox id="insurance_flag${index}" path="insurance_flag" value="Y" onchange="toggleInsurance(${index},this);"/>
								       	<strong><fmt:message key="report.booking.insuranceFee" /></strong>
								    </label>
									
								</td>
								<td><span id="insurance${index}"><fmt:formatNumber value="${bookDtl.insurance}" minFractionDigits="0"/></span></td>
								<td><span id="insuranceCommission${index }"><fmt:formatNumber value="${bookDtl.insuranceCommission}" minFractionDigits="0"/></span></td>
								<td><span id="insurancePax${index }"><fmt:formatNumber value="${bookDtl.insurancePax}" minFractionDigits="0"/></span></td>

<%-- 								<td><form:input id="insurance${index }" readonly="true" path="insurance" cssStyle="text-align: right; border: none; width: 100%"/></td> --%>
<%-- 								<td><form:input id="insuranceCommission${index}" readonly="true" path="insuranceCommission" cssStyle="text-align: right;border:none; width: 100%"/></td> --%>
<%-- 								<td><strong><form:input id="insurancePax${index }" readonly="true" path="insurancePax" cssStyle="text-align: right;border:none; width: 100%"/></strong></td> --%>
							</tr>
							<tr style="display: none">
								<td>
									<label class="checkbox-inline">
								        <form:checkbox id="service_flag${index}" path="service_flag" value="Y" onchange="toggleService(${index}, this);"/>
								       	<strong><fmt:message key="report.booking.serviceFee" /></strong>
								    </label>
								</td>
								<td>&nbsp;</td>
								<td><form:input id="serviceFeeCommission${index }" path="serviceFeeCommission" cssStyle="text-align: right;width: 100%; display:none;" placeholder="type here" onkeypress="return /\d/.test(String.fromCharCode(((event||window.event).which||(event||window.event).which)));" onchange="updateTotalCommission(${index})"/></td>
								<td><span id="servicePax${index }"><fmt:formatNumber value="${bookDtl.servicePax}" minFractionDigits="0"/></span></td>
<%-- 								<td><strong><form:input id="servicePax${index }" readonly="true" path="servicePax" cssStyle="text-align: right;border:none; width: 100%; display:none;"/></strong></td> --%>
							</tr>
							<tr>
								<td style="vertical-align: bottom;"><strong>TOTAL</strong></td>
<%-- 								<td style="text-align: right;">${bookDtl.ntaAmount}</td> --%>
								<td style="vertical-align: bottom;"><span id="ntaAmount${index}"><fmt:formatNumber value="${bookDtl.ntaAmount}" minFractionDigits="0"/></span></td>
								<td style="vertical-align: bottom;"><span id="amountCommission${index }"></span></td>
								<td style="vertical-align: bottom;font-size: large;"><strong><span id="total${index }">${bookDtl.amount}</span></strong></td>
							</tr>
							
						</tbody>

					</table>
				    <c:if test="${bookDtl.status != 'XX' && bookDtl.status != 'EXPIRED'}">
					<c:choose>
					   <c:when test="${side == 'right'}">
							<div style="text-align: right;">
								<button type="submit" name="emailpnr" class="btn btn-info" onclick="return confirmEmailPNR('${index}','${bookDtl.code}');">EMAIL</button>
								<button type="submit" name="printpnr" class="btn btn-info" onclick="return confirmPrintPNR('${bookDtl.code}');">PRINT</button>

							    <c:if test="${bookDtl.status != 'OK'}">
									<button type="submit" name="issuedpnr" class="btn btn-success"  onclick="bCancel=true;return confirmIssuedPNR('${bookDtl.code}');">PAY &amp; ISSUED</button>
									<button type="submit" name="cancelpnr" class="btn btn-danger" onclick="return confirmCancelPNR('${bookDtl.code}');">CANCEL TICKET</button>
								</c:if>
							</div>
					   </c:when>
					   <c:otherwise>
							<div>
							    <c:if test="${bookDtl.status != 'OK'}">
									<button type="submit" name="issuedpnr" class="btn btn-success" onclick="return confirmIssuedPNR('${bookDtl.code}');">PAY &amp; ISSUED</button>
									<button type="submit" name="cancelpnr" class="btn btn-danger" onclick="return confirmCancelPNR('${bookDtl.code}');">CANCEL TICKET</button>
								</c:if>
								<button type="submit" name="printpnr" class="btn btn-info" onclick="return confirmPrintPNR('${bookDtl.code}');">PRINT</button>
								<button type="submit" name="emailpnr" class="btn btn-info" onclick="return confirmEmailPNR('${index}','${bookDtl.code}');">EMAIL</button>
							</div>
					   </c:otherwise>
					</c:choose>
					</c:if>

					<div id="dialog-form${index}" title="Email Booking">
						<label>Email To Customer</label>
						<input id="emailTo${index}" value="${bookDtl.user.email}"/>
						<p><label>Message</label>
						<textarea id="messageBody${index}" rows="2" cols="40"></textarea>
						<p><hr>
						<input id="submitSendEmail${index}" type="submit" value="Send Email">
					</div>
				</form:form>
			</div>
		</div>
	</div>
</div>

	