	<form:input path="depId" id="depId1" type="hidden"></form:input>
	<form:input path="depTrans2Id" id="depTrans2Id1" type="hidden"></form:input>
	<form:input path="depTrans3Id" id="depTrans3Id1" type="hidden"></form:input>
	<form:input path="retId" id="retId1" type="hidden"></form:input>
	<form:input path="retTrans2Id" id="retTrans2Id1" type="hidden"></form:input>
	<form:input path="retTrans3Id" id="retTrans3Id1" type="hidden"></form:input>

	<div class="col-sm-5 well">
			
			<div class="row">
				<spring:bind path="maBookingForm.searchForm.tripMode">
				<div class="col-sm-12 form-group">
				</spring:bind>
					<form:radiobutton path="searchForm.tripMode" value="0" id="travelRadio1" onclick="javascript:travelModeCheck();"/><fmt:message key="flight.oneWayTrip" />&nbsp;&nbsp;&nbsp;&nbsp;
					<form:radiobutton path="searchForm.tripMode" value="1" id="travelRadio2" onclick="javascript:travelModeCheck();"/><fmt:message key="flight.returnTrip" />
			</div>
			</div>

			<div class="row">
				<spring:bind path="maBookingForm.searchForm.departCity">
				<div class="col-sm-6 form-group${(not empty status.errorMessage) ? ' has-error' : ''}">
				</spring:bind>
					<appfuse:label key="flight.cityFrom" styleClass="control-label" />
					<form:input path="searchForm.departCity" cssClass="form-control input-sm" 
						id="departCity1" placeholder="${hintsearchfrom}" value="${departCity}" />
					<!-- adanya value utk autofill memungkinkan aq quick tes,jd langsung pencet search  -->
					<form:errors path="searchForm.departCity" cssClass="help-block" />
				</div>
				<spring:bind path="maBookingForm.searchForm.destCity">
				<div class="col-sm-6 form-group${(not empty status.errorMessage) ? ' has-error' : ''}">
				</spring:bind>
					<appfuse:label key="flight.destination" styleClass="control-label" />
					<form:input path="searchForm.destCity" id="destCity1" placeholder="${hintsearchto}" value="${toCity}" cssClass="form-control input-sm" />
					<form:errors path="searchForm.destCity" cssClass="help-block" />
				</div>
			</div>
			
			<div class="row">
				<spring:bind path="maBookingForm.searchForm.departDate">
				<div class="col-sm-6 form-group${(not empty status.errorMessage) ? ' has-error' : ''}">
				</spring:bind>
					<appfuse:label key="flight.dateDepart" styleClass="control-label" />
					<form:input path="searchForm.departDate" name="dateDepart" id="depart1" placeholder="${hintsearchtoday}"
						value="${departDate}" cssClass="form-control input-sm" />
					<form:errors path="searchForm.departDate" cssClass="help-block" />
				</div>
	
				<spring:bind path="maBookingForm.searchForm.returnDate">
				<div id="returnGroup" <c:if test="${maBookingForm.searchForm.tripMode != 1}">style="display:none"</c:if> class="col-sm-6 form-group${(not empty status.errorMessage) ? ' has-error' : ''}">
				</spring:bind>
					<appfuse:label key="flight.dateReturn" styleClass="control-label" />
					<form:input path="searchForm.returnDate" id="return1" placeholder="${hintsearchtoday}" value="${returnDate}" cssClass="form-control input-sm" />
					<form:errors path="searchForm.returnDate" cssClass="help-block" />
				</div>
			</div>

			<div class="row">
				<spring:bind path="maBookingForm.searchForm.adult">
				<div class="col-sm-4 form-group${(not empty status.errorMessage) ? ' has-error' : ''}">
				</spring:bind>
					<appfuse:label key="flight.adult" styleClass="control-label" />
					<form:select path="searchForm.adult" cssClass="form-control input-sm">
						<c:forEach var="i" begin="1" end="6">
							<form:option value="${i}">${i}</form:option>
						</c:forEach>
					</form:select>
					<form:errors path="searchForm.adult" cssClass="help-block" />
				</div>
				<spring:bind path="maBookingForm.searchForm.children">
				<div class="col-sm-4 form-group${(not empty status.errorMessage) ? ' has-error' : ''}">
				</spring:bind>
					<appfuse:label key="flight.children" styleClass="control-label" />
					<form:select path="searchForm.children" cssClass="form-control input-sm" >
						<c:forEach var="i" begin="0" end="6">
							<form:option value="${i}">${i}</form:option>
						</c:forEach>
					</form:select>
					<form:errors path="searchForm.children" cssClass="help-block" />
				</div>
				<spring:bind path="maBookingForm.searchForm.infants">
				<div class="col-sm-4 form-group${(not empty status.errorMessage) ? ' has-error' : ''}">
				</spring:bind>
					<appfuse:label key="flight.infant" styleClass="control-label" />
					<form:select path="searchForm.infants" cssClass="form-control input-sm" >
						<c:forEach var="i" begin="0" end="6">
							<form:option value="${i}">${i}</form:option>
						</c:forEach>
					</form:select>
					<form:errors path="searchForm.infants" cssClass="help-block" />
				</div>
			</div>
			
			<button type="submit" name="searchParam" class="btn btn-primary"
				onclick="bCancel=false">
				<i class="icon-upload icon-white"></i>
				<fmt:message key="button.search" />
			</button>
	</div>
	<!-- 
    <label class="checkbox-inline">
        <form:checkbox path="searchForm.searchPast" id="searchPast1"/>
        Past Day
    </label> -->
	
	