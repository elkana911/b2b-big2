var dialog0, dialog1;

function send_Email(index) {
	 var valid = true;
	 var emailReg = /^([\w-\.]+@([\w-]+\.)+[\w-]{2,4})?$/;
	 var msg = $("#messageBody" + index).val();
	 
	 var emailTo = $("#emailTo" + index).val();
	 valid = valid && (emailTo.length >= 5);
	 valid = valid && (emailTo.match(emailReg));

	 if ( valid ) {
		 $.getJSON("../booking/email",
		    		{
			 			idx: index,
			 			to: emailTo,
		    			msg : msg
		    		})      	    	
	    	.done(function (data){
		    		console.log("success" + data);
	    		})
		  	.fail(function (msg){
		  		var errMsg = "Sorry, fail to send email.";
		  		console.log("(Http " + msg.status + ") " + msg.statusText);
		  		alert(errMsg);
		  	})
		  	.always(function(){
		  		console.log("complete banget");
				 if (index == 0){ 
					 dialog0.dialog( "close" );
				 }
				 else if (index == 1){
					 dialog1.dialog( "close" );
			     }
		  	})
		  	;
		 
	 }else
		 alert("Invalid Email");
	 
	 return valid;
}

function confirmEmailPNR(index, obj){
	
	$("#messageBody" + index).focus();
	if (index == 0){
		dialog0.dialog( "open" );
	}
	else if (index == 1){
		
		dialog1.dialog( "open" );
	}
	
	return false;
}

function confirmIssuedPNR(obj) {
    var msg = "Issued ticket for " + obj + " ?";
    ans = confirm(msg);
    return ans;
}

function confirmCancelPNR(obj) {
	var msg = "Cancel ticket for " + obj + " ?";
	ans = confirm(msg);
	return ans;
}
		
function hitungPax(index){
		var includeInsurance = $('#insurance_flag' + index).prop("checked");
		var includeService = $('#service_flag' + index).prop("checked");

		var paxTicket = $('#ntaPax' + index).text();
		var paxInsurance = includeInsurance ? $('#insurancePax' + index).text() : '0';
		var paxServiceFee = includeService ? $('#servicePax' + index).text() : '0';
		
		var total = 0;
		total += parseInt(paxTicket.replace(/[,.]/g, ''));
		total += parseInt(paxInsurance.replace(/[,.]/g, ''));
		total += parseInt(paxServiceFee.replace(/[,.]/g, ''));
		
		var ntaTicket = $('#nta' + index).text();
		var insurance = $('#insurance' + index).text();
		var ntaAmount = 0;
		ntaAmount += parseInt(ntaTicket.replace(/[,.]/g, ''));
		if (includeInsurance)
			ntaAmount += parseInt(insurance.replace(/[,.]/g, ''));

		var ntaComm = $('#ntaCommission' + index).text();
		if (ntaComm == '') ntaComm = '0';
		var insComm = includeInsurance ? $('#insuranceCommission' + index).text() : '0';
		var commAmount = includeService ? $('#serviceFeeCommission' + index).val() : '0';
		if (commAmount == '') commAmount = '0';
		var commissionAmount = 0;
		commissionAmount += parseInt(ntaComm.replace(/[,.]/g, ''));
		commissionAmount += parseInt(insComm.replace(/[,.]/g, ''));
		commissionAmount += parseInt(commAmount.replace(/[,.]/g, ''));
		
		$('#ntaAmount' + index).html(formatNumber(ntaAmount));
		$('#amountCommission' + index).html(formatNumber(commissionAmount));
		$('#total' + index).html(formatNumber(total));		
	}
	
	function formatNumber (num) {
	    return num.toString().replace(/(\d)(?=(\d{3})+(?!\d))/g, "$1,")
	}
	
	function updateTotalCommission(index, element){
		
		var total = 0;
		var includeInsurance = $('#insurance_flag' + index).prop("checked");
		var includeService = $('#service_flag' + index).prop("checked");

		var ntaComm = $('#ntaCommission' + index).text();
		var insComm = includeInsurance ? $('#insuranceCommission' + index).text() : '0';
		var serviceFeeComm = includeService ? $('#serviceFeeCommission' + index).val() : '0';

		//var servicePax = element !== undefined ?  element.value : '0';
		
		total += parseInt(ntaComm.replace(/[,.]/g, ''));
		total += parseInt(insComm.replace(/[,.]/g, ''));
		total += parseInt(serviceFeeComm.replace(/[,.]/g, ''));
		
		//var v = element.value;
		$('#amountCommission' + index).html(formatNumber(total));
		$('#servicePax' + index).html(formatNumber(serviceFeeComm));
		
		hitungPax(index);
	}
	
	function toggleInsurance(index, element)
	{
	    var val = $(element).prop("checked");
	    if (val){
			$('#insurance' + index).fadeIn();
			$('#insuranceCommission' + index).fadeIn();
			$('#insurancePax' + index).fadeIn();
	    }else{
			$('#insurance' + index).fadeOut();
			$('#insuranceCommission' + index).fadeOut();
			$('#insurancePax' + index).fadeOut();
	    }
	    
	    hitungPax(index);
	}
	function toggleService(index, element)
	{
	    var val = $(element).prop("checked");
	    if (val){
			$('#serviceFeeCommission' + index).fadeIn();
			$('#servicePax' + index).fadeIn();
	    }else{
			$('#serviceFeeCommission' + index).fadeOut();
			$('#servicePax' + index).fadeOut();
	    	
	    }
	    hitungPax(index);
	}
	
	/*
	$('#insurance_flag0').change(function () {
	    alert('insurance changed');
	 });
	$('#insurance_flag1').change(function () {
	    var val = $("#insurance_flag1").prop("checked"); 
		alert(val);
	    //update price
	    //$("#tot").text('eric');
	    
	 });*/

    function doAjaxPost() {
alert('hi');
        $.ajax({
            type: "GET",
            url: "subView",
            success: function(response) {
                $("#subViewDiv").html( response );
            }
        });
    }
    
    // Wait until the DOM has loaded before querying the document
    $(document).ready(function(){

       //Active tab selection after page load       
       $('#tabs').each(function(){
      
       // For each set of tabs, we want to keep track of
       // which tab is active and it's associated content
       var $active, $content, $links = $(this).find('a');
      
       // If the location.hash matches one of the links, use that as the active tab.
       // If no match is found, use the first link as the initial active tab.
       $active = $($links.filter('[href="'+location.hash+'"]')[0]
                 || $links[0]);
       
       $active.parent().addClass('active');
      
       $content = $($active.attr('href'));
       $content.show();
   });
  
       $("#ticket a").click(function(){
    	   var addressValue = $(this).attr("href");
    	   var idx = addressValue.slice(-1);
    	   $("#tabs li.active").removeClass('active');
    	   
    	   $("#eric" +idx).addClass("active");
           
           // Hide all tab content
           $(".tab_content").hide();

           // Here we get the href value of the selected tab
           var selected_tab = addressValue;// $("#tabs li").find("a").attr("href");      

           var starting = selected_tab.indexOf("#");
           var sub = selected_tab.substring(starting);
             
           //write active tab into cookie
              
           //$(sub).show();
               $(sub).fadeIn();
           // At the end, we add return false so that the click on the
          // link is not executed
           return false;
       });
       
    $("#tabs li").click(function() {
           
        // First remove class "active" from currently active tab
        $("#tabs li").removeClass('active');
   
        // Now add class "active" to the selected/clicked tab
        $(this).addClass("active");
           
        // Hide all tab content
        $(".tab_content").hide();

        // Here we get the href value of the selected tab
        var selected_tab = $(this).find("a").attr("href");      

        var starting = selected_tab.indexOf("#");
        var sub = selected_tab.substring(starting);
          
        //write active tab into cookie
        $(sub).fadeIn();
        // At the end, we add return false so that the click on the
       // link is not executed
        return false;
     });
    
    $('#submitSendEmail0').click(function(){
    	send_Email(0);
    });
    $('#submitSendEmail1').click(function(){
    	send_Email(1);
    });
    
	$( "#create-user" ).button().on( "click", function() {
//		dialog.dialog( "open" );
	});
	
	 function checkLength( o, n, min, max ) {
		 if ( o.val().length > max || o.val().length < min ) {
		 o.addClass( "ui-state-error" );
		 return false;
		 } else {
		 return true;
		 }
		 }
	 function checkRegexp( o, regexp, n ) {
		 if ( !( regexp.test( o.val() ) ) ) {
		 o.addClass( "ui-state-error" );
		 return false;
		 } else {
		 return true;
		 }
	}	 
	 
	dialog0 = $( "#dialog-form0" ).dialog({
		autoOpen: false,
		height: 260,
		width: 350,
		modal: true,
		close: function() {
			 $(this).dialog("close");
		}
	});
	dialog1 = $( "#dialog-form1" ).dialog({
		autoOpen: false,
		height: 260,
		width: 350,
		modal: true,
		close: function() {
			$(this).dialog("close");
		}
	});

});
