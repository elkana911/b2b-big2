<%@ include file="/common/taglibs.jsp"%>

<div class="row">
	<div class="col-sm-12">
	<display:table name="listHotel" cellspacing="0" cellpadding="0" requestURI="" keepStatus="true"
		id="flights" pagesize="25"
		class="table table-condensed table-striped table-hover" export="false">

       	<display:column property="hotel_Name" titleKey="hotel.hotelName" style="width: 10%;"
                        url="#" paramId="id" paramProperty="hotel_id" media="all" class="flightCell"/>

		<display:setProperty name="paging.banner.item_name">
			<fmt:message key="hotelList.hotel" />
		</display:setProperty>
		<display:setProperty name="paging.banner.items_name">
			<fmt:message key="hotelList.hotels" />
		</display:setProperty>

	</display:table>
	</div>
</div>	<!-- row -->


<ul class="glassList" style="font-size: 9px">
    <li>
        <fmt:message key="flightList.remark.localtime"/>
    </li>
    <li>
        <fmt:message key="flightList.remark.priceperadult"/>
    </li>
</ul>

