/**
 * 
 */
loadTable();

function callInfo(divTarget,buttonId, divProgressBar, pathInfo){
	$(buttonId).hide();
	$(divProgressBar).show();
  //	$(divProgressBar).progressbar({value:false});
  //	$(divProgressBar).height(20);
	$.ajax({
		type : "GET",
		url: myContextPath + pathInfo,
		success: function(data, status, req) {
	        var strHtml='';                
	        for(var i=0;i<data.length;i++){
	        	strHtml+='<tr><td>' + data[i].label + '</td><td>' + data[i].value + '</td></tr>';  
	        }
			$(divTarget).html('<thead><tr><th>Info</th><th>Value</th></tr></thead><tbody>' + strHtml + '</tbody>');
			//$( "#weather-temp" ).html( "<strong>" + data + "</strong> degrees" );
		},
		failure: function(data, status, req){
			console.log(data);
		},
		error: function(data, status, req){
			console.log(data);
		},
		complete: function (data, status){
			$(divProgressBar).hide();
			$(buttonId).show();
//	  		$('#progressbar').hide();
		}
		});	
}
function loadTable()
{
	callInfo("#airlineInfo", "#refreshAirline", "#progressbarAirlineInfo", "/flight/b2b/airlineInfo");
	callInfo("#agencyInfo", "#refreshAgency", "#progressbarAgencyInfo", "/agent/agencyInfo");
	callInfo("#flightInfo", "#refreshFlight", "#progressbarFlightInfo", "/flight/b2b/flightInfo");
	callInfo("#flightBookingInfo", "#refreshBookingFlight", "#progressbarFlightBookingInfo", "/flight/b2b/bookingInfo");
	callInfo("#financeInfo", "#refreshFinance", "#progressbarFinanceInfo", "/b2b/financeInfo");
	callInfo("#sysInfo", "#refreshSysInfo", "#progressbarSysInfo", "/b2b/sysInfo");

}

$('#refreshAirline').click(function(event){
	callInfo("#airlineInfo", "#refreshAirline", "#progressbarAirlineInfo", "/flight/b2b/airlineInfo");	
});
$('#refreshAgency').click(function(event){
	callInfo("#agencyInfo", "#refreshAgency", "#progressbarAgencyInfo", "/agent/agencyInfo");	
});
$('#refreshFlight').click(function(event){
	callInfo("#flightInfo", "#refreshFlight", "#progressbarFlightInfo", "/flight/b2b/flightInfo");	
});
$('#refreshBookingFlight').click(function(event){
	callInfo("#flightBookingInfo", "#refreshBookingFlight", "#progressbarFlightBookingInfo", "/flight/b2b/bookingInfo");	
});
$('#refreshFinance').click(function(event){
	callInfo("#financeInfo", "#refreshFinance", "#progressbarFinanceInfo", "/b2b/financeInfo");	
});
$('#refreshSysInfo').click(function(event){
	callInfo("#sysInfo", "#refreshSysInfo", "#progressbarSysInfo", "/b2b/sysInfo");	
});

	
	
