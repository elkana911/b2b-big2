<%@ include file="/common/taglibs.jsp"%>
	<form:input path="depId" id="depId1" type="hidden"></form:input>
	<form:input path="depTrans2Id" id="depTrans2Id1" type="hidden"></form:input>
	<form:input path="depTrans3Id" id="depTrans3Id1" type="hidden"></form:input>
	<form:input path="retId" id="retId1" type="hidden"></form:input>
	<form:input path="retTrans2Id" id="retTrans2Id1" type="hidden"></form:input>
	<form:input path="retTrans3Id" id="retTrans3Id1" type="hidden"></form:input>
	<div class="col-sm-9 col-lg-7">
				<spring:bind path="maBookingForm.searchForm.tripMode">
				<div class="col-sm-2 form-group small-cell" style="font-size: small;">
				</spring:bind>
					<form:radiobutton path="searchForm.tripMode" value="0" id="travelRadio1" onclick="javascript:travelModeCheck();"/><fmt:message key="flight.oneWayTrip" />&nbsp;&nbsp;&nbsp;&nbsp;
					<br><form:radiobutton path="searchForm.tripMode" value="1" id="travelRadio2" onclick="javascript:travelModeCheck();"/><fmt:message key="flight.returnTrip" />
				</div>

				<div class="col-sm-4 form-group small-cell">				
					<div class="row">
						<spring:bind path="maBookingForm.searchForm.departCity">
						<div class="col-sm-12 form-group${(not empty status.errorMessage) ? ' has-error' : ''}">
						</spring:bind>
							<appfuse:label key="flight.cityFrom" styleClass="control-label" />
							<form:input path="searchForm.departCity" cssClass="form-control input-sm" 
								id="departCity1" placeholder="${hintsearchfrom}" value="${departCity}" />
							<!-- adanya value utk autofill memungkinkan aq quick tes,jd langsung pencet search  -->
							<form:errors path="searchForm.departCity" cssClass="help-block" />
						</div>
					</div>
						
					<div class="row">
						<spring:bind path="maBookingForm.searchForm.destCity">
						<div class="col-sm-12 form-group${(not empty status.errorMessage) ? ' has-error' : ''}">
						</spring:bind>
							<appfuse:label key="flight.destination" styleClass="control-label" />
							<form:input path="searchForm.destCity" cssClass="form-control input-sm" 
								id="destCity1" placeholder="${hintsearchto}" value="${toCity}" />
							<form:errors path="searchForm.destCity" cssClass="help-block" />
						</div>
					</div>
				</div>
				<div class="col-sm-2 form-group small-cell">
					<div class="row">
						<spring:bind path="maBookingForm.searchForm.departDate">
						<div class="col-sm-12 form-group${(not empty status.errorMessage) ? ' has-error' : ''}">
						</spring:bind>
							<appfuse:label key="flight.dateDepart" styleClass="control-label" />
							<form:input path="searchForm.departDate" name="dateDepart" id="depart1" placeholder="${hintsearchtoday}"
								value="${departDate}" cssClass="form-control input-sm" />
							<form:errors path="searchForm.departDate" cssClass="help-block" />
						</div>
					</div>
					<div class="row small-cell">
						<spring:bind path="maBookingForm.searchForm.returnDate">
						<div id="returnGroup" <c:if test="${maBookingForm.searchForm.tripMode != 1}">style="display:none"</c:if> class="col-sm-12 form-group${(not empty status.errorMessage) ? ' has-error' : ''}">
						</spring:bind>
							<appfuse:label key="flight.dateReturn" styleClass="control-label" />
							<form:input path="searchForm.returnDate" id="return1" placeholder="${hintsearchtoday}" value="${returnDate}" cssClass="form-control input-sm" />
							<form:errors path="searchForm.returnDate" cssClass="help-block" />
						</div>
					</div>
				</div>	
				<div class="col-sm-4 form-group">
					<div class="row">
						<spring:bind path="maBookingForm.searchForm.adult">
						<div class="col-xs-4 col-sm-4 small-cell form-group${(not empty status.errorMessage) ? ' has-error' : ''}">
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
						<div class="col-xs-4 col-sm-4 small-cell form-group${(not empty status.errorMessage) ? ' has-error' : ''}">
						</spring:bind>
							<appfuse:label key="flight.children" styleClass="control-label" />
							<form:select path="searchForm.children" cssClass="form-control input-sm" id="childrenList"
								>
								<c:forEach var="i" begin="0" end="6">
									<form:option value="${i}">${i}</form:option>
								</c:forEach>
							</form:select>
							<form:errors path="searchForm.children" cssClass="help-block" />
						</div>

						<spring:bind path="maBookingForm.searchForm.infants">
						<div class="col-xs-4 col-sm-4 small-cell form-group${(not empty status.errorMessage) ? ' has-error' : ''}">
						</spring:bind>
							<appfuse:label key="flight.infant" styleClass="control-label" />
							<form:select path="searchForm.infants" cssClass="form-control input-sm" id="infantList">
								<c:forEach var="i" begin="0" end="6">
									<form:option value="${i}">${i}</form:option>
								</c:forEach>
							</form:select>
							<form:errors path="searchForm.infants" cssClass="help-block" />
						</div>
					</div> <!-- row -->
					<div class="row">
					<button type="submit" name="searchParam" id="searchButton" class="btn btn-primary text-right"
						onclick="bCancel=false; splash();" style="width:100%; margin-top: 1.3em;font-weight: bolder;text-transform: uppercase;">
						<i class="icon-upload icon-white"></i>
						<fmt:message key="button.search" />
					</button>
					</div>
				</div>	<!-- col-sm-4 -->		
	</div>	<!-- div-col-8 -->

	<div class="col-sm-3 col-lg-3" style="border-left: thin solid #3A87AD;font-size: smaller;">
		<div style="font-size: medium;font-style: italic;font-weight: bold;">Airline</div>
		<div>
			<input type="checkbox" id="selectAll1" checked="checked"><fmt:message key="button.selectall" /></input>
		</div>
		<div class="row">
		<div class="col-lg-6">
		 	<div>
	<!-- 	 	cbairline kubuat sbg trik utk selectall -->
			<label class="checkbox-inline">
		        <form:checkbox path="searchForm.garuda" cssClass="cbairline"/>
		       	<fmt:message key="airline.garuda" />
		    </label>
		    </div>
		 	<div>
			<label class="checkbox-inline">
	        	<form:checkbox path="searchForm.citilink" cssClass="cbairline"/>
	        	<fmt:message key="airline.citilink" />
	    	</label>
	    	</div>
		 	<div>
			<label class="checkbox-inline">
	        	<form:checkbox path="searchForm.airasia" cssClass="cbairline"/>
	        	<fmt:message key="airline.airasia" />
	    	</label>
	    	</div>
		 	<div>
			<label class="checkbox-inline">
	        	<form:checkbox path="searchForm.lion" cssClass="cbairline"/>
	        	<fmt:message key="airline.lion" />
	    	</label>
	    	</div>
		 	<div>
			<label class="checkbox-inline">
	        	<form:checkbox path="searchForm.wings" cssClass="cbairline"/>
	        	<fmt:message key="airline.wings" />
	    	</label>
	    	</div>
		 	<div class="span2">
			<label class="checkbox-inline">
	        	<form:checkbox path="searchForm.batik" cssClass="cbairline"/>
	        	<fmt:message key="airline.batik" />
	    	</label>
	    	</div>
	    	<div>
			<label class="checkbox-inline">
	        	<form:checkbox path="searchForm.express" cssClass="cbairline"/>
	        	<fmt:message key="airline.express" />
	    	</label>
	    	</div>
		</div>
		
		<div class="col-lg-6">
	    	<div>
			<label class="checkbox-inline">
	        	<form:checkbox path="searchForm.sriwijaya" cssClass="cbairline"/>
	        	<fmt:message key="airline.sriwijaya" />
	    	</label>
	    	</div>
	    	<div>
			<label class="checkbox-inline">
	        	<form:checkbox path="searchForm.nam" cssClass="cbairline"/>
	        	<fmt:message key="airline.nam" />
	    	</label>
	    	</div>
	    	
	    	<div>
			<label class="checkbox-inline">
	        	<form:checkbox path="searchForm.kalstar" cssClass="cbairline"/>
	        	<fmt:message key="airline.kalstar" />
	    	</label>
	    	</div>
	    	
	    	<div>
			<label class="checkbox-inline">
	        	<form:checkbox path="searchForm.trigana" cssClass="cbairline"/>
	        	<fmt:message key="airline.trigana" />
	    	</label>
	    	</div>
<!-- 	    	<div>    	 -->
<!-- 			<label class="checkbox-inline"> -->
<%-- 	        	<form:checkbox path="searchForm.susi" cssClass="cbairline"/> --%>
<%-- 	        	<fmt:message key="airline.susi" /> --%>
<!-- 	    	</label> -->
<!-- 			</div> -->
	    	<div>    	
			<label class="checkbox-inline">
	        	<form:checkbox path="searchForm.malindo" cssClass="cbairline"/>
	        	<fmt:message key="airline.malindo" />
	    	</label>
			</div>
	    	<div>    	
			<label class="checkbox-inline">
	        	<form:checkbox path="searchForm.aviastar" cssClass="cbairline"/>
	        	<fmt:message key="airline.aviastar" />
	    	</label>
			</div>
		</div>
		</div>
	</div>
<!-- 	
    <label class="checkbox-inline">
        <form:checkbox path="searchForm.searchPast" id="searchPast1"/>
        Past Day
    </label> -->


