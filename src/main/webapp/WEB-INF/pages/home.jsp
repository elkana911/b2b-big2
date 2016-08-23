<%@ include file="/common/taglibs.jsp"%>

<head>
<title><fmt:message key="home.title" /></title>
<link rel="stylesheet" href="<c:url value="/styles/jquery-ui.css"/>" />
<link rel="stylesheet" href="<c:url value="/styles/citiesPicker.css"/>" />
<link rel="stylesheet" href="<c:url value="/styles/style.css"/>" />
<link rel="stylesheet" href="<c:url value="/styles/home.css"/>" />
<meta name="menu" content="Home" />
</head>

<style>
@media(max-width:767px){
	.lehalogo{
		width:auto;height:140px;display:block; margin: 0 auto;
		padding-bottom: 3px;
	}
	
	#searchFromHome{
		width: 100%;
		margin-top: 1em;
	}
}

@media (min-width: 768px) {
	.lehalogo{
		width:100%;height:140px
	}
}

@media (min-width: 992px) {
	.lehalogo{
		width:100%;height:140px
	}
}
@media (min-width: 1200px) {
	.lehalogo{
		width:100%;height:140px
	}
}

#ui-datepicker-div{
	font-size: smaller;
}
</style>

<body class="home">

<div class="row">
<div class="col-sm-3 col-md-3 col-lg-2" style="padding:0">
	<img src="<c:url value='/images/giff-b2b-1.gif'/>" class="lehalogo"/>
</div>		

<div class="col-sm-9 col-md-9 col-lg-10" style="padding-right: 0px">
	<div class="titlebox">
	<h2>
		<fmt:message key="home.welcome"/>&nbsp;<strong><c:out value="${pageContext.request.remoteUser}"></c:out></strong> 
	</h2>
	<hr>
	<p>
		<fmt:message key="home.messageHeader" >
			<fmt:param value="${agent.balanceIdr}" />
			<fmt:param value="${agent.balanceUsd}" />
		</fmt:message>
	</p>
</div>
</div>
</div>


<div class="row" style="margin-top: 5px">	
	<div class="hidden-xs col-sm-3 col-md-3 col-lg-2 panel-kiri">
		<div class="panel-group" id="accordion">
		   <div class="panel panel-info">
		      <div class="panel-heading" style="background-color: rgb(45,63,77)">
		         <h4 class="panel-title" style="text-align: right; font-weight: bold; color: #FFF">
		            <a data-toggle="collapse" data-parent="#accordion" 
		               href="#collapseleftbar">
		               FLIGHT
		            </a>
		         </h4>
		      </div>
		      <div id="collapseleftbar" class="panel-collapse collapse in">
		         <div class="panel-body">
		         	<a href="<c:url value='/flight/b2b/search?from=menu'/>"><fmt:message
							key="menu.ticketSearch" /></a>
		         </div>
		         <div class="panel-body">
		         	<a href="<c:url value='/flight/b2b/retrievebooking'/>"><fmt:message
							key="menu.retrieveBooking" /></a>
		         </div>
		         <div class="panel-body">
		         </div>
		         <div class="panel-body">
		         </div>
		      </div>
		      
		      <div class="panel-heading" style="background-color: rgb(45,63,77)">
		         <h4 class="panel-title" style="text-align: right; font-weight: bold; color: #FFF">
		            <a data-toggle="collapse" data-parent="#accordion" 
		               href="#collapseleftbar2">
		               HOTEL
		            </a>
		         </h4>
		      </div>
		      <div id="collapseleftbar2" class="panel-collapse collapse">
		         <div class="panel-body">
		         	<a href="<c:url value='/hotel/b2b/search'/>"><fmt:message
							key="menu.ticketSearch" /></a>
		         </div>
		         <div class="panel-body">
		         	<a href="<c:url value='/hotel/b2b/retrievebooking'/>"><fmt:message
							key="menu.retrieveBooking" /></a>
		         </div>
		         <div class="panel-body">
		         </div>
		         <div class="panel-body">
		         </div>
		      </div>
		      
		      <div class="panel-heading" style="background-color: rgb(45,63,77)">
		         <h4 class="panel-title" style="text-align: right; font-weight: bold; color: #FFF">
		            <a data-toggle="collapse" data-parent="#accordion" 
		               href="#collapseleftbar3">
		               RENT CAR
		            </a>
		         </h4>
		      </div>
		      <div id="collapseleftbar3" class="panel-collapse collapse">
		         <div class="panel-body">
		         	<a href="<c:url value='/flight/b2b/search'/>"><fmt:message
							key="menu.ticketSearch" /></a>
		         </div>
		         <div class="panel-body">
		         	<a href="<c:url value='/flight/retrievebooking'/>"><fmt:message
							key="menu.retrieveBooking" /></a>
		         </div>
		         <div class="panel-body">
		         </div>
		         <div class="panel-body">
		         </div>
		      </div>
		   </div>
		</div>
	</div>
	
	<div class="col-sm-9 col-md-9 col-lg-10" style="padding-right:0">

	<form:form commandName="maBookingForm" method="post" autocomplete="off" cssClass="well"
		action="home"
		onsubmit="return validateFlightSearch(this)"
		style="padding: 9px"
		>
		<%@ include file="../pages/flight/inner/searchform-home.jsp"%>
	</form:form>
	</div>
</div>

<div class="row">
<div class="col-sm-12 networknews">
	<fmt:message key="home.network"/>&nbsp;<b><fmt:message key="home.news"/></b> 
</div>
</div>

<div class="hidden-xs row headlinebox" style="margin-top: 2px;">
	<div id="slider123" class="col-sm-12 carousel slide">
		<div id="carousels" class="carousel-inner"></div>
	</div>
</div>

<div class="row" id="feedsarea">
	<display:table name="feeds" id="feed" cellspacing="0" cellpadding="0" requestURI="" keepStatus="true"
		class="table table-condensed table-striped table-hover" export="false">

       	<display:column property="lastUpdate" sortable="true" titleKey="feeds.date" style="width: 5%;"
                        media="all" format="{0,date,dd/MMM/yyyy}"/>

		<display:column sortable="true" titleKey="feeds.title" media="all" >
			<a href="${feed.externalUrl}">
				<c:out value='${feed.title}'/>
			</a>
		</display:column>
		
		<display:caption><fmt:message key="feeds.others" />:</display:caption>
		
		<display:setProperty name="basic.show.header" value="false" />
	</display:table>

</div>	

	<c:if test="${pageContext.request.remoteUser == 'admin'}">
		<div class="row">
			<%@ include file="../pages/table-request.jsp"%>
		</div>
	</c:if>


<c:set var="scripts" scope="request">
	<script type="text/javascript" src="<c:url value="/scripts/jquery-ui.js"/>"></script>
	<%@ include file="/scripts/flightcommon.js"%>
 	<script type="text/javascript" src="<c:url value="/scripts/jquery.citiesPicker.js"/>"></script>
	<script type="text/javascript">
	$('#departCity1').citiesPicker();
	$('#destCity1').citiesPicker();
	retrieveHeadlines();
	retrieveInfo();
	
	$('#selectAll1').click(function(event){
		if (this.checked){
			$(".cbairline").attr('checked', true);
		}else{
			$(".cbairline").attr('checked', false);
		}
	});

	$('input[id=selectAll1]').attr('checked', true).triggerHandler('click');
	
		var headlines;
		function retrieveHeadlines(){
			//http://api.jquery.com/query.get/
		    $.getJSON("${pageContext.request.contextPath}/feeds/getHeadlines")
		    	.done(function (data){
		    		var first = true; 
		        	$.each(data, function(key,item){
		        		var carousleContaierDivId = $('#carousels');
		        		var thumb = "${pageContext.request.contextPath}/feeds/getThumb?imagePath=" + item.thumbId; 
		        		var pointToDetail = item.externalUrl != null ? ("<a href='" + item.externalUrl + "' target='_blank'><span style='text-decoration: underline;''>Read More ...</span></a>") : "";
	        			var imageCarousel = '<div class="item carousel-item ' + (first ? 'active' :'') + '"><a href="' + item.externalUrl + '"><img class="carousel-image" alt="Image Caption" src="'+ thumb +'"width="380" height="250"></a><div class="carousel-caption">'+item.title+'</div><div class="headlineremarks">' + item.remarks + '<p>' + pointToDetail + '</div></div>';
	        			
	        			first = false;

						carousleContaierDivId.append(imageCarousel);
		 				
		        	}); 
		        	$('#slider123').carousel({
		                interval: 6200, cycle:true
		              });
		    	});
		}
		function formatNumber (num) {
		    return num.toString().replace(/(\d)(?=(\d{3})+(?!\d))/g, "$1,")
		}
		function retrieveInfo(){
		    $.getJSON("${pageContext.request.contextPath}/flight/b2b/gettotal")
	    	.done(function (data){
	        	$('#airlineList').text("Airline ( " + formatNumber(data) + " flights )");
	    	});			
		}
		function processSuccess(data, status, req) {
			//alert('success');
			alert(data);
			alert('status:' + status); //status success
			alert('req:' + req); //object
			alert('req.:' + req.responseXML); //object XMLDocument
			alert('req.:' + req.responseText); //object XMLDocument
			if (status == "success")
				$("#response").text($(req.responseXML).find("return").text());

			var $books = $(req.responseText).find("return");
			alert($books.length);
			var i = 0;
			$.each($books, function(i, book) {
				i = i + 1;
				var author = $(book).children("address1").text();
				// var bookName = $(book).children("title").text();
				// var li = $("<li>").text(bookName + " - " + author);
				// $("#books").append(li);
				alert(author);
				if (i > 5)
					return false; //cant use break
			});

		}
		function processError(data, status, req) {
			alert('err' + data.state);
			//alert(req.responseText + " " + status);
		}
		function onButtonClick() {

			alert("jquery version:" + jQuery.fn.jquery);
			console.log("hello");
			$
					.ajax({
						type : "POST",
						username : 'admin',
						password : 'admin',
						url : 'http://203.128.87.234:9090/travel6/services/FlightService?wsdl',
						contentType : "text/xml; charset=\"utf-8\"",
						method : 'searchDepartFlight',
						dataType : "xml",
						data : '<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:ser="http://service.flight.travel6.web1.big.com/"><soapenv:Header/><soapenv:Body><ser:searchDepartFlight><DepartDate></DepartDate><DepartIATA>cgk</DepartIATA><destinationIATA>bth</destinationIATA><airlines></airlines><searchPast>true</searchPast></ser:searchDepartFlight></soapenv:Body></soapenv:Envelope>',
						//data: soapRequest,
						success : processSuccess,
						error : processError
					});

/*
			var request = createCORSRequest( "POST", 'http://203.128.87.234:9090/travel6/services/HotelService?wsdl');
			if ( request ){
			    // Define a callback function
			    request.onload = function(){};
			    // Send request
			    request.send();
			}*/	/*
			$.ajax({
			    type: "POST",
			    username: 'admin',
			    password: 'admin',
			    xhrFields: {
			    	  withCredentials: true
			    	},
			    url: 'http://203.128.87.234:9090/travel6/services/HotelService?wsdl',
			    contentType: "text/xml; charset=\"utf-8\"",
			    method: 'getHotels',
			    dataType: "xml",
			    data: '<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:ser="http://service.hotel.travel6.web1.big.com/"><soapenv:Header/><soapenv:Body><ser:getHotels/></soapenv:Body></soapenv:Envelope>',
			    //data: soapRequest,
			    success: processSuccess,
			    error: processError
			});*/
			
			/*
			//http://203.128.87.234:9090/travel6/services/HotelService?wsdl		
			$.ajax({
			dataType: "xml",
			method: "GET",
			//url: 'http://www.corsproxy.com/103.11.133.138/~dummyapi/ma_xml_response_getallotment.php',
			url: "http://www.corsproxy.com/203.128.87.234:9090/travel6/services/HotelService?wsdl",
			data: {
			    username: 'admin',
			    password: 'admin'
			    },
			// data:$('#form').serialize(),
			success: function (data) {
			    console.log('hahaha');
			},
			failure: function(data){
			    console.log('payah');
			}
			});		*/
		}
		function createCORSRequest(method, url){
		    var xhr = new XMLHttpRequest();
		    if ("withCredentials" in xhr){
		        // XHR has 'withCredentials' property only if it supports CORS
		        xhr.open(method, url, true);
		    } else if (typeof XDomainRequest != "undefined"){ // if IE use XDR
		        xhr = new XDomainRequest();
		        xhr.open(method, url);
		    } else {
		        xhr = null;
		    }
		    return xhr;
		}
	</script>
</c:set>
</body>
