<%@ include file="/common/taglibs.jsp"%>

<head>
<title><fmt:message key="menu.search" />&nbsp;<fmt:message
		key="menu.flight" /></title>
<link rel="stylesheet" href="<c:url value="/styles/jquery-ui.css"/>" />
<link rel="stylesheet" href="<c:url value="/styles/citiesPicker.css"/>" />

<style>
.flightInfoCell{
 	font-size:12px;  	
}

.flightCell{
	border-width: 1px;border-style:solid;border-color: #D7D4D4;
	text-align: center;
}

.sortable{
	text-align:center;
}

.departureTitle{
	font-weight: bold;
}

.returnTitle{
	font-weight: bold;
}

.ui-menu .ui-menu-item a{
    background: rgb(252, 252, 242);
    font-size:11px;
    margin: 2px;
}

</style>

<meta name="menu" content="FlightMenu" />
</head>

<c:set var="hintsearchfrom">
	<fmt:message key="hint.search.flight.from" />
</c:set>
<c:set var="hintsearchto">
	<fmt:message key="hint.search.flight.to" />
</c:set>
<c:set var="hintsearchtoday">
	<fmt:message key="hint.search.flight.today" />
</c:set>
<c:set var="hintsearchtomm">
	<fmt:message key="hint.search.flight.tommorrow" />
</c:set>
<c:set var="errorsagreeterms">
	<fmt:message key="errors.agree.terms" />
</c:set>
<input id="errorsagreeterms" type="hidden" value="${errorsagreeterms}" />

<form:form commandName="maBookingForm" method="get"
	action="../flight/search"
	onsubmit="return validateFlightSearch(this)"
	style="padding-bottom:0px">

    <legend class="accordion-heading">
        <a data-toggle="collapse" href="#collapse-searchform"><fmt:message key="menu.ticketSearchPublic"/></a>
    </legend>
	<div id="collapse-searchform"  class="row well accordion-body collapse in" style="padding: 0px">
		<%@ include file="../flight/inner/searchform-top.jsp" %>
	</div>
	
	<%@ include file="../flight/inner/list-depart-return.jsp" %>

	<c:if test="${ (listDepart != null && fn:length(listDepart) > 0) || (listReturn != null && fn:length(listReturn) > 0)}">
	<%@ include file="../static/info-before-book.html" %>
		<div class="row">
				<div class="col-sm-8">
					<label class="checkbox-inline">
						<form:errors path="agreeInsurance" cssClass="help-block" />
		                <form:checkbox path="agreeInsurance" id="agreeInsurance1"/>
					    <fmt:message key="flight.book.agree.insurance">
					    	<fmt:param><c:url value="/travelinsurance"/></fmt:param>
					    </fmt:message>		                
					</label>
				</div>
				<div class="col-sm-4">
					<button type="submit" name="bookParam" class="btn btn-primary text-right"
						onclick="return onBookClick();" style="float: right;width:200px">
						<i class="icon-upload icon-white"></i>
						<fmt:message key="button.book" />
					</button>
				</div>
		</div>
	</c:if>

</form:form>

<c:set var="scripts" scope="request">
	<v:javascript formName="flightSearch" staticJavascript="false" />
	<script type="text/javascript" src="<c:url value="/scripts/validator.jsp"/>"></script>
	<script type="text/javascript" src="<c:url value="/scripts/jquery-ui.js"/>"></script>
 	<script type="text/javascript" src="<c:url value="/scripts/jquery.citiesPicker.js"/>"></script>

	<%@ include file="/scripts/flightcommon.js"%>
 	 
<script type="text/javascript">

       $("#xxx").autocomplete({
       	//http://viralpatel.net/blogs/spring-3-mvc-autocomplete-json-tutorial/
           source: function(request, response){
               $.getJSON("${pageContext.request.contextPath}/getCountries", {
					q: request.term
                   }, response);
           },
           search: function(){
               	//custom minLength
           		var q = this.value;
           		if (q.length < 1)
               		return false;
               },
       	focus: function(){
           		//prevent value inserted on focus
       			return false;
           },
           select: function(event, ui){
               console.log("select " + ui.item.value);
               this.value = ui.item.value;
               return false;
           }
       });

       function clickdepartrate() {
     	  var radioValue = $('input[name="departrate"]:checked').val();
		
     	  var id = radioValue.substr(0, radioValue.length -1); 
     	  var rate = radioValue.slice(-1);	//ambil last character 
     	  document.getElementById("depId1").setAttribute("value", radioValue);
   	  //alert(id + "," + rate);
     }
     function clickreturnrate() {
     	  var radioValue = $('input[name="returnrate"]:checked').val();
		
     	  var id = radioValue.substr(0, radioValue.length -1); 
     	  var rate = radioValue.slice(-1);	//ambil last character 

     	  document.getElementById("retId1").setAttribute("value", radioValue);
   	  //alert(id + "," + rate);

     	  //to display detailed information
     	  $.ajax({
         	  type: 'GET',
         	  url : '#',
         	  data :{},
         	  success : function(result){
             	  },
         	  error: function(result){
             	  }
         	});
     }

     function onBookClick(){
		if (!document.getElementById('agreeInsurance1').checked){
			alert($("#errorsagreeterms").val());
			return false;
		}
		return true;
     }

  	
</script>


</c:set>
