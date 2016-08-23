//jangan lupa jquery udah harus dideclare sebelumnya
//supaya dpt dipakai berkali2, gunakan id2 yg telah didefine di script ini

//	var airports;
	
	document.body.onload=retrieveData();
	
	function retrieveData(){
		//http://api.jquery.com/query.get/
//	    $.getJSON("${pageContext.request.contextPath}/getAirports")
//	    	.done(function (data){
//	        	window.airports = data;
//	        	});
	}

	function validateHotelSearch(form){
		try {
			if (!checkDate(form.depart1, true)) return false;
	
			if (!checkDate(form.return1, true)) return false;
		
		}catch(err) {
			console.log(">>" + err.message);
		    return false;
		}
		
		return true;
	}
	
		var checkin = $('#depart1').datepicker(
				{

					minDate : new Date(),
					numberOfMonths : 2,
					showButtonPanel : true,
					dateFormat : "dd/mm/yy",
					//showAnim: "drop",
					//showOptions: {direction: "up"},
					currentText : "Bulan ini",
					closeText : "Tutup",
					monthNames : [ "Januari", "Februari", "Maret", "April",
							"Mei", "Juni", "July", "Agustus", "September",
							"Oktober", "November", "Desember" ],
					dayNamesMin : [ "M", "S", "S", "R", "K", "J", "S" ],
					showOtherMonths : true,
					
					onClose: function(){
							$('#return1').focus();
					}
				});

		var checkout = $('#return1').datepicker(
				{

					minDate : new Date(),
					numberOfMonths : 2,
					showButtonPanel : true,
					dateFormat : "dd/mm/yy",
					//showAnim: "drop",
					//showOptions: {direction: "up"},
					currentText : "Bulan ini",
					closeText : "Tutup",
					monthNames : [ "Januari", "Februari", "Maret", "April",
							"Mei", "Juni", "July", "Agustus", "September",
							"Oktober", "November", "Desember" ],
					dayNamesMin : [ "M", "S", "S", "R", "K", "J", "S" ],
					showOtherMonths : true
				}).on('changeDate', function(ev) {
			//$('#checkout1')[0].focus();
		});



		// Original JavaScript code by Chirp Internet: www.chirp.com.au 
		// Please acknowledge use of this code by including this header. 
		function checkDate(field, allowBlank) { 
			//var allowBlank = true; 
			var minYear = 1902; 
			var maxYear = (new Date()).getFullYear() + 1; 
			var errorMsg = ""; 
			// regular expression to match required date format 
			re = /^(\d{1,2})\/(\d{1,2})\/(\d{4})$/; 
			if(field.value != '') { 
				if(regs = field.value.match(re)) { 
					if((regs[1] < 1) || regs[1]>31) { 
						errorMsg = "Invalid value for day: " + regs[1]; 
					} else if((regs[2] < 1) || (regs[2] > 12)) { 
						errorMsg = "Invalid value for month: " + regs[2]; 
					} else if((regs[3] < minYear) || (regs[3] > maxYear)) { 
						errorMsg = "Invalid value for year: " + regs[3] + " - must be between " + minYear + " and " + maxYear; 
					} 
				} else { 
					errorMsg = "Invalid date format: " + field.value; 
				} 
			} else if(!allowBlank) { 
					errorMsg = "Empty date not allowed!"; 
				} 
				if(errorMsg != "") { 
					alert(errorMsg); 
					field.focus(); 
					return false; 
				} 

			return true; 
		}
