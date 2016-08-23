<%@ include file="/common/taglibs.jsp"%>

<%-- <c:set var="seatclasses" value="${fn:split('o,u,x,e,g,v,t,q,n,m,l,k,h,b,w,s,y,i,d,c,p,a,f,j,r,z', ',')}" scope="application" /> --%>
<div class="row">
	<c:if test="${listDepart != null}">
	<div>
		<img style="" src="../../images/depart_icon.gif" />
		<span class="departureTitle"><fmt:message key="flightList.depart"/>&nbsp;(${maBookingForm.departTitle})</span>
	</div>
	</c:if>
	<div class="col-sm-12">
	<display:table name="listDepart" cellspacing="0" cellpadding="0" requestURI="" keepStatus="true"
		decorator="com.big.web.b2b_big2.flight.decorator.FlightDepartB2BTableDecorator"
		id="departFlights" pagesize="25"
		class="table table-condensed table-striped table-hover" export="false">

       	<display:column property="airline" sortable="true" titleKey="flight.airline" paramId="id" paramProperty="flightAvailId" media="all" class="flightCellAirline"/>

		<display:column property="origin" title="${fn:toUpperCase(maBookingForm.searchForm.departIata)}"
			media="all" class="flightCell flightCellDepart">
		</display:column>
		<display:column property="destination" 
			title="${fn:toUpperCase(maBookingForm.searchForm.destinationIata)}" media="all" class="flightCell flightCellArrival">
		</display:column>
		
		<c:forEach var="i" items="${seatclasses_dep}">
			<display:column property="${i}" 
				title="${fn:toUpperCase(i)}" media="all" class="flightCell" style="width: 1%">
			</display:column>
		</c:forEach>

		<display:setProperty name="paging.banner.item_name">
			<fmt:message key="flightList.flight" />
		</display:setProperty>
		<display:setProperty name="paging.banner.items_name">
			<fmt:message key="flightList.flights" />
		</display:setProperty>

	</display:table>
	</div>
</div>	<!-- row -->
<c:if test="${maBookingForm.searchForm.tripMode == 1}">
<div class="row">

		<c:if test="${listReturn != null}">
			<div>
				<span class="returnTitle"><fmt:message key="flightList.arrival"/>&nbsp;(${maBookingForm.returnTitle})</span>
				<img style="" src="../../images/return_icon.gif" />
			</div>
		</c:if>
		
		<div class="col-sm-12">
			<display:table name="listReturn" cellspacing="0" cellpadding="0" 
				requestURI="" keepStatus="true"
				decorator="com.big.web.b2b_big2.flight.decorator.FlightReturnB2BTableDecorator"
				defaultsort="1" id="returnFlights" pagesize="25"
				class="table table-condensed table-striped table-hover fixed" export="false">
		       	<display:column property="airline" titleKey="flight.airline" url="#" paramId="id" paramProperty="flightAvailId" media="all" class="flightCellAirline"/>
		
				<display:column property="origin" title="${maBookingForm.searchForm.destinationIata}" 
					media="all" class="flightCell flightCellDepart">
				</display:column>
				<display:column property="destination" 
					title="${maBookingForm.searchForm.departIata}" media="all" class="flightCell flightCellArrival">
				</display:column>
				
				<c:forEach var="i" items="${seatclasses_ret}">
					<display:column property="${i}"
						title="${fn:toUpperCase(i)}" media="all" class="flightCell" style="width: 1%">
					</display:column>
				</c:forEach>
		
				<display:setProperty name="paging.banner.item_name">
					<fmt:message key="flightList.flight" />
				</display:setProperty>
				<display:setProperty name="paging.banner.items_name">
					<fmt:message key="flightList.flights" />
				</display:setProperty>
		
			</display:table>			
				
		</div>
</div>
</c:if>

<ul class="glassList" style="font-size: 9px">
    <li>
        <fmt:message key="flightList.remark.localtime"/>
    </li>
    <li>
        <fmt:message key="flightList.remark.priceperadult"/>
    </li>
</ul>

