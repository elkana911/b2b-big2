	<div class="col-sm-12">
				<div class="col-lg-4 form-group small-cell">				
					<div class="row">
						<spring:bind path="maBookingForm.searchForm.query">
						<div class="col-sm-12 form-group${(not empty status.errorMessage) ? ' has-error' : ''}">
						</spring:bind>
							<appfuse:label key="hotel.query" styleClass="control-label" />
							<form:input path="searchForm.query" cssClass="form-control input-sm" 
								id="query1" placeholder="${hintsearchfrom}" value="${query}" />
							<!-- adanya value utk autofill memungkinkan aq quick tes,jd langsung pencet search  -->
							<form:errors path="searchForm.query" cssClass="help-block" />
						</div>
					</div>
				</div>
				<div class="col-lg-2 form-group small-cell">
					<div class="row">
						<spring:bind path="maBookingForm.searchForm.checkInDate">
						<div class="col-sm-12 form-group${(not empty status.errorMessage) ? ' has-error' : ''}">
						</spring:bind>
							<appfuse:label key="hotel.checkIn" styleClass="control-label" />
							<form:input path="searchForm.checkInDate" id="depart1" placeholder="${hintsearchtoday}"
								value="${checkInDate}" cssClass="form-control input-sm" />
							<form:errors path="searchForm.checkInDate" cssClass="help-block" />
						</div>
					</div>
				</div>
				<div class="col-lg-2 form-group small-cell">
					<div class="row small-cell">
						<spring:bind path="maBookingForm.searchForm.checkOutDate">
						<div class="col-sm-12 form-group${(not empty status.errorMessage) ? ' has-error' : ''}">
						</spring:bind>
							<appfuse:label key="hotel.checkOut" styleClass="control-label" />
							<form:input path="searchForm.checkOutDate" id="return1" placeholder="${hintsearchtoday}" value="${checkOutDate}" cssClass="form-control input-sm" />
							<form:errors path="searchForm.checkOutDate" cssClass="help-block" />
						</div>
					</div>
				</div>	

				<div class="col-lg-2 form-group">
					<div class="row">
						<spring:bind path="maBookingForm.searchForm.adult">
						<div class="col-xs-6 col-sm-6 small-cell form-group${(not empty status.errorMessage) ? ' has-error' : ''}">
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
						<div class="col-xs-6 col-sm-6 small-cell form-group${(not empty status.errorMessage) ? ' has-error' : ''}">
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

					</div> <!-- row -->
				</div>
				<div class="col-lg-2 form-group">
					<div class="row" style="margin-top: 1.3em">
						<button type="submit" name="searchParam" class="btn btn-primary text-right"
							onclick="bCancel=false" style="width:100%">
							<i class="icon-upload icon-white"></i>
							<fmt:message key="button.search" />
						</button>
					</div>
				</div>	<!-- col-sm-4 -->		

	</div>
	
