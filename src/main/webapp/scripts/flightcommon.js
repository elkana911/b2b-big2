<script type="text/javascript">
//jangan lupa jquery udah harus dideclare sebelumnya
//supaya dpt dipakai berkali2, gunakan id2 yg telah didefine di script ini

	var airports;
	
	document.body.onload=retrieveData();
	
	function retrieveData(){
		//http://api.jquery.com/query.get/
	    $.getJSON("${pageContext.request.contextPath}/getAirports")
	    	.done(function (data){
	        	window.airports = data;
	        	});
	}

	function validateFlightSearch(form){
		try {
			if (!checkDate(form.depart1, true)) return false;
	
			if (document.getElementById('travelRadio2').checked){
				if (!checkDate(form.return1, true)) return false;

			}
		
		}catch(err) {
			console.log(">>" + err.message);
		    //document.getElementById("demo").innerHTML = err.message;
		    return false;
		}
		
		return true;
	}
	
	function travelModeCheck() {
		if (document.getElementById('travelRadio2').checked) {
			document.getElementById('returnGroup').style.display = 'block';
		}
		else {
			document.getElementById('returnGroup').style.display = 'none';	
		}
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
						if ($("#travelRadio2").is(":checked")){
							$('#return1').focus();
						}
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

		function split(val){
			return val.split(/,\s*/);
		}

		function extractLast(term){
			return split(term).pop();
		}

		function checkEmpty(field, keyword){
			
			if (field.value.trim().length < 2 ){
				alert("Invalid " + keyword + " !");
				field.focus();
				return false;
			} 
			
			return true;
		}

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
		
		function convertMilliToDDMMYYYY(milliTime, separator){
			var date = new Date(milliTime);
			var mnth = ("0" + (date.getMonth() + 1)).slice(-2);
			var day = ("0" + date.getDate()).slice(-2); 
			var ddmmyyyy = [day, mnth,date.getFullYear()].join(separator);
			
			return ddmmyyyy;
			//var ddmmyyyy = t.getDate() + '/' + (t.getMonth()+1) + '/' + t.getFullYear();

		}

		function checkInfantDate(field, allowBlank) { 
			//var allowBlank = true; 
			var today = new Date();
			var maxYear = today.getFullYear(); 
			var minYear = maxYear - 2; 
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
					
					var currMonth = today.getMonth();
					if (regs[3] > maxYear && regs[2] > currMonth+1){
						errorMsg = "Invalid birth date !";
					}
					
					var d = new Date();
					d.setDate(parseInt(regs[1]));
					d.setMonth(parseInt(regs[2])-1);
					d.setYear(parseInt(regs[3]));
					var diffDays = parseInt((today - d) / (1000 * 60 * 60 * 24));
					if (diffDays < 14){
						errorMsg = "Infant's age must not below 14 days !";
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

        $("#departCity1").keyup(function(){
			if((this.value.length > 0) && this.value.match(/^[A-z]+$/)){
             var items = [];
             for (var i = 0; i < window.airports.length; i++) {
            	    var n = window.airports[i].search(new RegExp(this.value, "i"));
            	    if (n > -1){
                	    items[items.length] = window.airports[i];
                	}
            	}
          	 $("#departCity1").autocomplete({source: items});          	 
		 	}
       	});

        $("#destCity1").keyup(function(){
			if((this.value.length > 0) && this.value.match(/^[A-z]+$/)){
             var items = [];
             for (var i = 0; i < window.airports.length; i++) {
            	    var n = window.airports[i].search(new RegExp(this.value, "i"));
            	    if (n > -1){
                	    items[items.length] = window.airports[i];
                	}
            	}
          	 $("#destCity1").autocomplete({source: items});          	 
		 	}
       	});
</script>

