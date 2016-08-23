<%@ include file="/common/taglibs.jsp"%>

<div class="row">
<c:if test="${listDepart != null}">
	<div>
		<img style="" src="../images/depart_icon.gif" />
		<span class="departureTitle">${depart_Title}</span>
	</div>
</c:if>
<div class="col-sm-12">
	<display:table name="listDepart" cellspacing="0" cellpadding="0" requestURI="" keepStatus="true"
		decorator="com.big.web.b2b_big2.flight.decorator.FlightDepartTableDecorator"
		id="flights" pagesize="25" 
		class="table table-condensed table-striped table-hover" export="false">

       	<display:column property="airline" sortable="true" titleKey="flight.airline" style="width: 5%;"
                        url="/flight/search?from=list" paramId="id" paramProperty="flightdetid" media="all" class="flightCell"/>

		<display:column property="origin" sortable="true" title="${depart_depTitle}"
			media="all" class="flightCell" style="width: 3%">
		</display:column>
		<display:column property="destination" sortable="true"
			title="${depart_retTitle}" media="all" class="flightCell" style="width: 3%">
		</display:column>
		<display:column property="duration" sortable="true"
			titleKey="flightList.duration" media="all" class="flightCell" style="width: 3%">
		</display:column>
		<display:column property="promoRate" sortable="true"
			titleKey="flightList.promo" media="all" class="flightCell" style="width: 7%">
		</display:column>
		<display:column property="economyRate" sortable="true"
			titleKey="flightList.economy" media="all" class="flightCell" style="width: 7%">
		</display:column>
		<display:column property="businessRate" sortable="true"
			titleKey="flightList.business" media="all" class="flightCell" style="width: 7%">
		</display:column>

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
			<span class="returnTitle">${return_Title}</span>
			<img style="" src="../images/return_icon.gif" />
		</div>
	</c:if>
	<div class="col-sm-12">
		<display:table name="listReturn" cellspacing="0" cellpadding="0"
			requestURI="" keepStatus="true"
			decorator="com.big.web.b2b_big2.flight.decorator.FlightReturnTableDecorator"
			defaultsort="1" id="flights" pagesize="25"
			class="table table-condensed table-striped table-hover" export="false">
	
	       	<display:column property="airline" sortable="true" titleKey="flight.airline" style="width: 5%;"
	                        url="/flight/search?from=list" paramId="id" paramProperty="flightdetid" media="all" class="flightCell"/>

			<display:column property="origin" sortable="true" title="${depart_depTitle}"
				media="all" class="flightCell" style="width: 3%">
			</display:column>
			<display:column property="destination" sortable="true"
				title="${depart_retTitle}" media="all" class="flightCell" style="width: 3%">
			</display:column>
			<display:column property="duration" sortable="true"
				titleKey="flightList.duration" media="all" class="flightCell" style="width: 3%">
			</display:column>
			<display:column property="promoRate" sortable="true"
				titleKey="flightList.promo" media="all" class="flightCell" style="width: 7%">
			</display:column>
			<display:column property="economyRate" sortable="true"
				titleKey="flightList.economy" media="all" class="flightCell" style="width: 7%">
			</display:column>
			<display:column property="businessRate" sortable="true"
				titleKey="flightList.business" media="all" class="flightCell" style="width: 7%">
			</display:column>
	
			<display:setProperty name="paging.banner.item_name">
				<fmt:message key="flightList.flight" />
			</display:setProperty>
			<display:setProperty name="paging.banner.items_name">
				<fmt:message key="flightList.flights" />
			</display:setProperty>
	
		</display:table>
	</div>
</div>	<!-- row -->
</c:if>

<ul class="glassList" style="font-size: 9px">
    <li>
        <fmt:message key="flightList.remark.localtime"/>
    </li>
    <li>
        <fmt:message key="flightList.remark.priceperadult"/>
    </li>
</ul>


