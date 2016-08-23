<script type="text/javascript">

var cTemplates = {
				div: $('<div class="summary-ticket"></div>'),
		        table  : $('<table class="ticketdtl-table" width="100%"></table>'),
		        colHeader : $('<td colspan="6" class="summary-header"></td>'),
		        row : $('<tr></tr>'),
		        data : $('<td></td>'),
		        dataBlank : $('<td>&nbsp;</td>'),
		        emptyLine : $('<tr><td colspan="6">&nbsp;</td></tr>'),
		       // labelTax : $('<td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>Tax</td>'), bikin pusing
		       // labelTotal : $('<td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>Tax</td>')
			}
		;

//link 920CBAEF564C496F9A89D6E21E0ACD4C
//8bb6013c-ea65-471d-9898-972183b54369G0000001 yg lama
//8bb6013c-ea65-471d-9898-972183b54369SJG0000001 yg baru
var IDX_PLANECOUNTER = -1;
var IDX_CLASS = -8;

var IDX_LIST_INDEX = -7
var LENGTH_LIST_INDEX = 6;

var IDX_AIRLINE_CODE = -10;
var LENGTH_AIRLINE_CODE = 2;

		/**
		 * Number.prototype.format(n, x, s, c)
		 * 
		 * @param integer n: length of decimal
		 * @param integer x: length of whole part
		 * @param mixed   s: sections delimiter
		 * @param mixed   c: decimal delimiter
		 */
		Number.prototype.format = function(n, x, s, c) {
		    var re = '\\d(?=(\\d{' + (x || 3) + '})+' + (n > 0 ? '\\D' : '$') + ')',
		        num = this.toFixed(Math.max(0, ~~n));
		    
		    return (c ? num.replace('.', c) : num).replace(new RegExp(re, 'g'), '$&' + (s || ','));
		};
		
/*
		function collectCheckedRadio(){
			
			var radioValue = $('input[name="departrate"]:checked').val();

			if (radioValue == undefined) return null;
			
			var buffer = [];
			
			var checkedAll = $("#listflights :radio:checked");			
 			var planeCounter = radioValue.slice(IDX_PLANECOUNTER)[0];			//0 or 1
 			
 			if (checkedAll.length > 1){
 				
				$("#listflights :radio:checked").each(function() {
					var _radioValue = $('input[name="' + this.name + '"]:checked').val();
					buffer.push(_radioValue);
			    });
			}else{
				if (planeCounter < 1)	//only accept non transit
					buffer.push(radioValue);
			}

			var _tmp = buffer[0].slice(-4);
			var _lastIdx = _tmp.substr(0,3); 
			var _valid = true;
			for (var i = 1, len = buffer.length; i < len; i++) {
				if (_lastIdx == buffer[i].slice(-4).substr(0, 3)){
					
				}
				else{
					_valid = false;
				}
			}

			if (!_valid) return null;
			
			return buffer;
		}
*/		

		//utk mengecek apakah airline2 terkoneksi dalam 1 kali rute harusnya menampilkan birthday
		function shouldDisplayBDay(groups){
			if (groups.length < 1) return false;

			var shouldShow = false;
			for (var i = 0; i < groups.length; i++){
	        	var airlineCode = groups[i].slice(IDX_AIRLINE_CODE).substr(0, LENGTH_AIRLINE_CODE);
				if (airlineCode == 'QG' || airlineCode == 'GA' || airlineCode == 'QZ'){
					shouldShow = true;
					break;
				}
	        }
			
			return shouldShow;

		}
		function shouldIdCardExist(groups){
			if (groups.length < 1) return false;

			var shouldExist = false;
			for (var i = 0; i < groups.length; i++){
	        	var airlineCode = groups[i].slice(IDX_AIRLINE_CODE).substr(0, LENGTH_AIRLINE_CODE);
				if (airlineCode == 'SJ'){
					shouldExist = true;
					break;
				}
	        }
			
			return shouldExist;			
		}
		//utk mengecek apakah pesawat2 terkoneksi dalam 1 kali rute
		function validGroups(groups){
			if (groups.length < 1) return true;
			
			var _tmp = groups[0].slice(IDX_LIST_INDEX);
			var _planeCounter = parseInt(_tmp.slice(IDX_PLANECOUNTER)[0]);
			if (_planeCounter > 0 && groups.length < 2) return false;
			
			var _valid = true;
			var _lastIdx = _tmp.substr(0, LENGTH_LIST_INDEX); 
			for (var i = 1, len = groups.length; i < len; i++) {
				if (_lastIdx == groups[i].slice(IDX_LIST_INDEX).substr(0, LENGTH_LIST_INDEX)){
					
				}
				else{
					_valid = false;
				}
			}
			
			return _valid;
		}
		function collectSelection(){
			//var radioValue = $('input[name="departrate"]:checked').val();
			
			//if (radioValue == undefined) return null;
			
			var depBuffer = [], retBuffer = [];
			
			var checkedAll = $("#listflights :radio:checked");			
			if (checkedAll.length > 0){
				
				$("#listflights :radio:checked").each(function() {
					var _radioValue = $('input[name="' + this.name + '"]:checked').val();
					if (this.name.lastIndexOf("departrate", 0) === 0){
						depBuffer.push(_radioValue);						
					}else if (this.name.lastIndexOf("returnrate", 0) === 0){
						retBuffer.push(_radioValue);						
					}
				});
			}
			
			if (!validGroups(depBuffer)) return null;
			
			if (!validGroups(retBuffer)) return null;
		
			return {value1: depBuffer, value2: retBuffer};
		}

		function toggleAdultFullName(index, element)
		{
			var _fullName = document.getElementById("adult" + index + ".fullName").value;
			var _cCustValue = $('input[name="customer.fullName"]');
			
			if (_cCustValue.val().trim().length < 1){
				//write here
				_cCustValue.val(_fullName);
			}
		}

		function isVisible(elem) {
			return elem.is(":visible");
//		    var style = window.getComputedStyle(elem);
//		    return (style.display === 'none');
//			return (elem.offsetParent === null);
//			  return elem.offsetWidth > 0 || elem.offsetHeight > 0;
		}

		//jangan lupa jquery udah harus dideclare sebelumnya
		function clickRadio(radioName){
			var checkedAll = $("#listflights :radio:checked");//http://stackoverflow.com/questions/6920606/jquery-how-to-check-if-all-radio-buttons-in-a-div-are-selected
			
			var radioValue = $('input[name="' + radioName + '"]:checked').val();
			var planeCounter = radioValue.slice(IDX_PLANECOUNTER)[0];			//0 or 1. posisi biasa paling belakang tujuannya menghitung jumlah pesawat. non transit 0, > 0 itu transit: pesawat pertama 1, pesawat kedua 2 dst
			
			var airlineCode = radioValue.slice(IDX_AIRLINE_CODE).substr(0, LENGTH_AIRLINE_CODE); 
			
			var depGroups = [], retGroups = [], valid;
			
			var div = cTemplates.div.clone(),
				tableFlight = cTemplates.table.clone(),
				rowHeader = cTemplates.row.clone(),
				flightHeader = cTemplates.colHeader.clone(),
				dataBlank = cTemplates.dataBlank.clone(),
				rowTax = cTemplates.row.clone(),
				rowTotal = cTemplates.row.clone()
			;

			//kalo non transit, uncheck transits
			if (planeCounter < 1){
				//uncheck other. gampang, tinggal diwalik2
				$('input[name="' + radioName + 'transit2"]').attr('checked',false);
				$('input[name="' + radioName + 'transit3"]').attr('checked',false);
				/*
				if (radioName == "departrate"){
					$('input[name="departratetransit2"]').attr('checked',false);
					$('input[name="departratetransit3"]').attr('checked',false);
				}else if (radioName == "returnrate"){
					$('input[name="returnratetransit2"]').attr('checked',false);
					$('input[name="returnratetransit3"]').attr('checked',false);						
				}
				*/
			}else{
				//auto select utk multi transit yg css radio nya hidden
				if (radioName == "departrate" || radioName == "returnrate"){

					var _radioId = $('input[name="' + radioName + '"]:checked').attr('id');	//departrate000000
					var _groupId = _radioId.slice(IDX_LIST_INDEX - IDX_PLANECOUNTER - 5); //-7 - (-1) -5
					//cek hidden ?
					var _hiddenDepartTransit2 = $('#' + radioName + "transit2" + _groupId).css('display') == 'none';
					if (_hiddenDepartTransit2){
						$('#' + radioName + _groupId).attr('checked', true);
						$('#' + radioName + 'transit2' + _groupId).attr('checked', true);
						$('#' + radioName + 'transit3' + _groupId).attr('checked', true);
					}else{
						//jika malah pesawat ketiga yg hidden
						var _hiddenDepartTransit3 = $('#' + radioName + "transit3" + _groupId).css('display') == 'none';
						if (_hiddenDepartTransit3){
							$('#' + radioName + _groupId).attr('checked', true);
							$('#' + radioName + 'transit3' + _groupId).attr('checked', true);
						}
					}
				}

				/*
				var transit3 = $(element).is(":visible");
				
				 */
				var pancing = true;
			}
			
			$("#listflights :radio:checked").each(function() {
		    	//link 920CBAEF564C496F9A89D6E21E0ACD4C
				var _radioValue = $('input[name="' + this.name + '"]:checked').val();
				var _clsSeat = _radioValue.slice(IDX_CLASS)[0];	//ambil character dr arah kanan. might want to play with http://www.w3schools.com/jsref/tryit.asp?filename=tryjsref_slice6

				if (this.name.lastIndexOf("departrate", 0) === 0){
					depGroups.push(_radioValue);						
				}else if (this.name.lastIndexOf("returnrate", 0) === 0){
					retGroups.push(_radioValue);						
				}
		    });

			//need to sort depart flights 
			var _depValid = validGroups (depGroups);
			var _retValid = validGroups(retGroups);
			valid = _depValid && _retValid;
			if (!valid){
				//close or clear booklist
		    	$('.summary-ticket').hide();
		    	
//		    	$("#popupFare").dialog("close");
				div.text('Please select a valid schedule.');
		    	$('#popupFare').html(div);
		    	
//		    	$('#ticketDtlSide').hide();
		    	$('#ticketDtlSide').text('Please select a valid schedule.');
		    	
		    	return;
			}
			
			var departBDay = shouldDisplayBDay(depGroups);
			var returnBDay = shouldDisplayBDay(retGroups);
			var shouldShow = departBDay || returnBDay;

	    	if (shouldShow){
	    		for (var i = 0, len = ${maBookingForm.searchForm.adult}; i < len; i++) {
		    		$('#showBirthday' + i).show();
    			}
	    		
	    	}else{
	    		for (var i = 0, len = ${maBookingForm.searchForm.adult}; i < len; i++) {
		    		$('#showBirthday' + i).hide();
    			}
	    	}
			
			var newDepGroups = [], newRetGroups = [];
			var __counter = depGroups.length;
			while (__counter > 0){
				var _planeCounter = parseInt(depGroups[__counter-1].slice(IDX_PLANECOUNTER)[0]);	//0
				if (_planeCounter == 0) _planeCounter = 1;
				newDepGroups[_planeCounter-1] = depGroups[__counter-1];
				
				__counter--;
			}

			__counter = retGroups.length;
			while (__counter > 0){
				var _planeCounter = parseInt(retGroups[__counter-1].slice(IDX_PLANECOUNTER)[0]);	//0
				if (_planeCounter == 0) _planeCounter = 1;
				newRetGroups[_planeCounter-1] = retGroups[__counter-1];
				
				__counter--;
			}
			
	      	$("#popupFare").html(div).prepend("<div id='progressbar'></div>");
	      	$('#progressbar').progressbar({value:false});
	    	$("#popupFare").dialog("open");
	    	
	    	//show birthday nationality for some airline
//	    	var lastVisibleBDay = isVisible($('#showBirthday'));

	    	$.getJSON("${pageContext.request.contextPath}/flight/b2b/getFareDtl",
			    		{
			    			_idDep : newDepGroups,
			    			_idRet : newRetGroups,
			    			dDate : '${maBookingForm.searchForm.departDate}',
			    			dCity: '${maBookingForm.searchForm.destCity}',
			    			adults : '${maBookingForm.searchForm.adult}',
			    			childs : '${maBookingForm.searchForm.children}',
			    			infants : '${maBookingForm.searchForm.infants}'
			    		})      	    	
		    	.done(function (data){
			    		console.log("success" + data);

						var flightTitle = "FLIGHT (ADT " + ${maBookingForm.searchForm.adult} + ", CHD " + ${maBookingForm.searchForm.children} + ", INF " + ${maBookingForm.searchForm.infants} + ")";
						flightHeader.text(flightTitle);
						flightHeader.appendTo(rowHeader);
						rowHeader.appendTo(tableFlight);
						tableFlight.appendTo(div);


			    		$.each(data.fareInfo, function (i, value){
			    			var row = cTemplates.row.clone(),
			    				dataFlightNo = cTemplates.data.clone(),
			    				dataClassNo = cTemplates.data.clone(),
			    				dataTimeTravel = cTemplates.data.clone(),
			    				dataRoute = cTemplates.data.clone(),
			    				dataCurr = cTemplates.data.clone(),
			    				dataRate = cTemplates.data.clone()
					        ;
			    			dataFlightNo.attr("width", "65");
			    			dataFlightNo.text(value.flightNo); dataFlightNo.appendTo(row);
			    			dataClassNo.attr("width", "11");
			    			dataClassNo.text(value.className); dataClassNo.appendTo(row);
			    			dataTimeTravel.attr("width", "81");
			    			dataTimeTravel.text(value.timeTravel); dataTimeTravel.appendTo(row);
			    			dataRoute.attr("width", "73");
			    			dataRoute.text(value.route.from.iataCode + "-" + value.route.to.iataCode); dataRoute.appendTo(row);
			    			dataCurr.attr("width", "25");
			    			dataCurr.text(value.currency); dataCurr.appendTo(row);
			    			dataRate.attr("width", "87");
			    			dataRate.attr("style", "text-align: right");
			    			var r = value.rate;//http://stackoverflow.com/questions/14467433/currency-formatting-in-javascript
			    			var num = r.toString().replace(/(\d)(?=(\d{3})+$)/g, "$1,");
			    			
			    			dataRate.text(num ); dataRate.appendTo(row);
			    			
			    			row.appendTo(tableFlight);

			    		});//exit loop
			    		
			    		//summary
			    		if (data.psc != undefined && data.psc != 0)
			    		$('<tr><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>PSC</td>'
			    				+ '<td>' + "IDR" + '</td>'
			    				+ '<td style="text-align: right">' + data.psc + '</td>'
			    				+ '</tr>')
			    			.appendTo(tableFlight);
			    		if (data.surcharge != undefined && data.surcharge!= 0)
			    			$('<tr><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>FuelSurcharge</td>'
			    					+ '<td>' + "IDR" + '</td>'
			    					+ '<td style="text-align: right">' + data.surcharge + '</td>'
			    					+ '</tr>')
			    					.appendTo(tableFlight);
			    		if (data.iwjr != undefined && data.iwjr != 0)
			    			$('<tr><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>IWJR</td>'
			    					+ '<td>' + "IDR" + '</td>'
			    					+ '<td style="text-align: right">' + data.iwjr + '</td>'
			    					+ '</tr>')
			    					.appendTo(tableFlight);
			    		if (data.pph != undefined && data.pph != 0)
			    			$('<tr><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>Pph</td>'
			    					+ '<td>' + "IDR" + '</td>'
			    					+ '<td style="text-align: right">' + data.pph + '</td>'
			    					+ '</tr>')
			    					.appendTo(tableFlight);
			    		if (data.tax != 0)
			    			$('<tr><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>Tax</td>'
			    					+ '<td>' + "IDR" + '</td>'
			    					+ '<td style="text-align: right">' + data.tax + '</td>'
			    					+ '</tr>')
			    					.appendTo(tableFlight);
			    		if (data.fuelSurcharge != undefined && data.fuelSurcharge != 0)
			    			$('<tr><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>Fuel Surcharge</td>'
			    					+ '<td>' + "IDR" + '</td>'
			    					+ '<td style="text-align: right">' + data.fuelSurcharge + '</td>'
			    					+ '</tr>')
			    					.appendTo(tableFlight);
			    		if (data.insurance != undefined && data.insurance != 0)
			    			$('<tr><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>Insurance</td>'
			    					+ '<td>' + "IDR" + '</td>'
			    					+ '<td style="text-align: right">' + data.insurance + '</td>'
			    					+ '</tr>')
			    					.appendTo(tableFlight);
			    		$('<tr><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>Total</td>'
			    				+ '<td>' + "IDR" + '</td>'
			    				+ '<td style="text-align: right"><strong>' + data.total + '</strong></td>'
			    				+ '</tr>')
			    				.appendTo(tableFlight);

			    		tableFlight.appendTo(div);

			    		//add return flight
			    		
			    		
			    		//add hotel
						var hotelTitle = "HOTEL at " + data.destinationCity,
							hotelHeader = cTemplates.colHeader.clone(),
							rowHotelHeader = cTemplates.row.clone(),
							tableHotel = cTemplates.table.clone()
							;
						hotelHeader.text(hotelTitle);
						hotelHeader.appendTo(rowHotelHeader);
						rowHotelHeader.appendTo(tableHotel);						
						$('<tr><td colspan="6"><a href="#">Add Hotel ?</a></td></tr>').appendTo(tableHotel);
						
						$('<p>').appendTo(div);
			    		tableHotel.appendTo(div);
			    		
			    		//add rentcar
						var rentCarTitle = "RENT CAR at " + data.destinationCity,
							rentCarHeader = cTemplates.colHeader.clone(),
							rowRentCarHeader = cTemplates.row.clone(),
							tableRentCar = cTemplates.table.clone()
							;
						rentCarHeader.text(rentCarTitle);
						rentCarHeader.appendTo(rowRentCarHeader);
						rowRentCarHeader.appendTo(tableRentCar);						
						$('<tr><td colspan="6"><a href="#">Rent a Car ?</a></td></tr>').appendTo(tableRentCar);
						
						$('<p>').appendTo(div);
//			    		tableRentCar.appendTo(div);disable for now until got partner :)

			    		$('#popupFare').html(div);
			    		$('#ticketDtlSide').html(div.clone());
			    		$('#ticketDtlSide').show();
				    	$('.summary-ticket').show();
				    	
				    	
			    		
			  	})
			  	.fail(function (msg){
			  		var errMsg = "Sorry, Fare not found or has changed";
			  		console.log("(Http " + msg.status + ") " + msg.statusText);
//		    		$('#popupFare').html(div).prepend("<div id='has-error'><button>" + errMsg + "</button></div>");
		    		$('#popupFare').html(div).prepend("<div id='has-error'><button type='button' class='btn btn-danger' style='width:100%' onclick='location.href=\"javascript:location.reload(true)\"'>" + errMsg + "</button></div>");
			  	})
			  	.always(function(){
			  		console.log("complete banget");
			  		$('#progressbar').hide();
			  	})
			  	;
		}
		
        function clickdepartrate() {
      	  var radioValue = $('input[name="departrate"]:checked').val();

    	  //document.getElementsByName("maBookingForm.searchForm.departCity")[0].setAttribute("value", "eric"); ga bisa pake gini
    	  document.getElementById("depId1").setAttribute("value", radioValue);
    	  //console.log("AA" + document.getElementById('departCity1').value);

    	  clickRadio("departrate");

        }
        function clickreturnrate() {
      	  var radioValue = $('input[name="returnrate"]:checked').val();

    	  document.getElementById("retId1").setAttribute("value", radioValue);
    	  
    	  clickRadio("returnrate");

        }
		function clickdepartratetransit2(){
      	  var radioValue = $('input[name="departratetransit2"]:checked').val();

    	  //document.getElementsByName("maBookingForm.searchForm.departCity")[0].setAttribute("value", "eric"); ga bisa pake gini
    	  document.getElementById("depTrans2Id1").setAttribute("value", radioValue);
    	  //console.log("AA" + document.getElementById('departCity1').value);
    	  clickRadio("departratetransit2")

		}
		function clickdepartratetransit3(){
			var radioValue = $('input[name="departratetransit3"]:checked').val();
			
			//document.getElementsByName("maBookingForm.searchForm.departCity")[0].setAttribute("value", "eric"); ga bisa pake gini
			document.getElementById("depTrans3Id1").setAttribute("value", radioValue);
			//console.log("AA" + document.getElementById('departCity1').value);
			clickRadio("departratetransit3")

		}
		function clickreturnratetransit2(){
			var radioValue = $('input[name="returnratetransit2"]:checked').val();
			
			//document.getElementsByName("maBookingForm.searchForm.departCity")[0].setAttribute("value", "eric"); ga bisa pake gini
			document.getElementById("retTrans2Id1").setAttribute("value", radioValue);
			//console.log("AA" + document.getElementById('departCity1').value);
			clickRadio("returnratetransit2")
			
		}
		function clickreturnratetransit3(){
			var radioValue = $('input[name="returnratetransit3"]:checked').val();
			
			//document.getElementsByName("maBookingForm.searchForm.departCity")[0].setAttribute("value", "eric"); ga bisa pake gini
			document.getElementById("retTrans3Id1").setAttribute("value", radioValue);
			//console.log("AA" + document.getElementById('departCity1').value);
			clickRadio("returnratetransit3")
		}
		function arrHasDupes( A ) {                          // finds any duplicate array elements using the fewest possible comparison
			var i, j, n;
			n=A.length;
		                                                     // to ensure the fewest possible comparisons
			for (i=0; i<n; i++) {                        // outer loop uses each item i at 0 through n
				for (j=i+1; j<n; j++) {              // inner loop only compares items j at i+1 to n
					if (A[i]==A[j]) return true;
			}	}
			return false;
		}
		
		function onBookClick(){
    		/*
    		//no term show
    		if (!document.getElementById('agreeTerm1').checked){
    			alrt($("#errorsagreeterms").val());
    			return false;
    		}*/
    		//var selectedDeparted = document.getElementById("depId1").value;
/*
			if (document.getElementById("depId1").value == ''){
    			alert($("#errordepartflight").val());
    			return false;
    		}		
			//check radio
    		if (document.getElementById('travelRadio2').checked){
    			if (document.getElementById("retId1").value == ''){
    				alert($("#errorreturnflight").val());
    				return false;
    			}		

    		}
*/
    		var isBDayVisible = isVisible($('#showBirthday0'));

 			var tmpGroups = collectSelection();//CheckedRadio();

 			if (tmpGroups.value1.length < 1 && tmpGroups.value2.length < 1){
 				alert('Please choose valid schedule !');
 				return false;
 			}
 			
 			$('[name="depId"]').val(tmpGroups.value1[0]);
 			$('[name="depTrans2Id"]').val(tmpGroups.value1[1]);
 			$('[name="depTrans3Id"]').val(tmpGroups.value1[2]);
 			$('[name="retId"]').val(tmpGroups.value2[0]);
 			$('[name="retTrans2Id"]').val(tmpGroups.value2[1]);
 			$('[name="retTrans3Id"]').val(tmpGroups.value2[2]);

 			
			var airlineAskIdCard = shouldIdCardExist(tmpGroups);
 			
    		try{
    			if (${maBookingForm.searchForm.adult} > 0){
    				var _fullName = [];
    		        for (var i = 1; i <= ${maBookingForm.searchForm.adult}; i++) {
    		            var nama = document.getElementById("adult" + (i-1) + ".fullName");
    		    		if (nama.value.trim().length < 2){
    		        		alert("Please fill passenger name for adult#" + i);
    		        		nama.focus();
    		        		return false;
    		        	}
    		    		var ttl = document.getElementById("adult" + (i-1) + ".title");
    		    		if (ttl.value.trim().length < 2){
    		    			alert("Please select title for adult#" + i);
    		    			ttl.focus();
    		    			return false;
    		    		}
    		    		
    		            var _idCard = document.getElementById("adult" + (i-1) + ".idCard");
    		    		if (_idCard.value.trim().length > 20){
    		        		alert("ID card too long for adult#" + i);
    		        		_idCard.focus();
    		        		return false;
    		        	}
    		    		
    		    		if (airlineAskIdCard){
    		    			alert("Please fill passenger ID card for adult#" + i);
    		    			_idCard.focus();
    		    			return false;
    		    		}

    		    		if (isBDayVisible){
        		    		var nat = document.getElementById("adult[" + (i-1) + "].countryCode");
        		    		if (nat.selectedIndex == 0){
        		    			alert('Please select Nationality for adult#' + i);
        		    			nat.focus();
        		    			return false
        		    		}
        		    		
    		    			if (!checkDate(document.getElementById("adult" + (i-1) + ".birthday"), false))
        				    	return false;	
    		    		}
    		    		
    		    		_fullName.push(nama);	
    		    	}
    			
	    			if (arrHasDupes(_fullName)){
	    				alert("Duplicate Adult Passenger found !")
	    				return false;
	    			}
    			}
    				
    			if (${maBookingForm.searchForm.children} > 0){
    				var _fullName = [];
    		        for (var i = 1; i <= ${maBookingForm.searchForm.children}; i++) {
    		            var nama = document.getElementById("child" + (i-1) + ".fullName");
    		    		if (nama.value.trim().length < 2){
    		        		alert("Please fill passenger name for children#" + i);
    		        		nama.focus();
    		        		return false;
    		        	}
    		    		var ttl = document.getElementById("child" + (i-1) + ".title");
    		    		if (ttl.value.trim().length < 2){
    		    			alert("Please select title for child#" + i);
    		    			ttl.focus();
    		    			return false;
    		    		}
    		            var _idCard = document.getElementById("child" + (i-1) + ".idCard");
    		    		if (_idCard.value.trim().length > 20){
    		        		alert("ID card too long for child#" + i);
    		        		_idCard.focus();
    		        		return false;
    		        	}
    			    	//
    			    	if (!checkDate(document.getElementById("child" + (i-1) + ".birthday"), false))
    				    	return false;	
    			    	
    			    	_fullName.push(nama);
    		    	}
    			
	    			if (arrHasDupes(_fullName)){
	    				alert("Duplicate Children Passenger found !")
	    				return false;
	    			}
    			}
    			
    			if (${maBookingForm.searchForm.infants} > 0){
    				
    				var _fullName = [];
    				var _parents = [];
    		        for (var i = 1; i <= ${maBookingForm.searchForm.infants}; i++) {
    		            var nama = document.getElementById("infant" + (i-1) + ".fullName");
    		    		if (nama.value.trim().length < 2){
    		        		alert("Please fill passenger name for infant#" + i);
    		        		nama.focus();
    		        		return false;
    		        	}
    		    		
    		    		var ttl = document.getElementById("infant" + (i-1) + ".title");
    		    		if (ttl.value.trim().length < 2){
    		    			alert("Please select title for infant#" + i);
    		    			ttl.focus();
    		    			return false;
    		    		}

    		            var _idCard = document.getElementById("infant" + (i-1) + ".idCard");
    		    		if (_idCard.value.trim().length > 20){
    		        		alert("ID card too long for infant#" + i);
    		        		_idCard.focus();
    		        		return false;
    		        	}

    		    		var num = document.getElementById("infant" + (i-1) + ".num");
    		    		if (num.value == 0){
    		    			alert("Please select adult id for infant#" + i);
    		    			num.focus();
    		    			return false;
    		    		}
    			    	_parents.push(num.value);  		

    			    	if (!checkInfantDate(document.getElementById("infant" + (i-1) + ".birthday"), false))
    				    	return false;	
    			    	
    			    	_fullName.push(nama);
    			    	
    		    	}
    		        
    		        if (arrHasDupes(_parents)){
    		        	
    		        	alert("Duplicate parents found ! Each of adult passenger can carry only one infant");
    		        	return false;
    		        }
    		        
	    			if (arrHasDupes(_fullName)){
        				alert("Duplicate Infant Passenger found !")
        				return false;
        			}

    			}

    	   		if (!checkEmpty(document.getElementById("customer.fullName"), "Customer Name")){
    	       		return false;
    	       	}
    	   		if (!checkEmpty(document.getElementById("customer.phone1"), "Customer Phone")){
    	       		return false;
    	       	}
    	   		if (!checkEmpty(document.getElementById("customer.agentName"), "Agent Name")){
    	   			return false;
    	   		}
    	   		if (!checkEmpty(document.getElementById("customer.agentEmail"), "Agent Email")){
    	   			return false;
    	   		}

    	       	
    		}catch(err) {
    			console.log(">>" + err.message);
    		    //document.getElementById("demo").innerHTML = err.message;
    		    return false;
    		}
    		
    		var r = confirm("Book Now ?");
    		if (r == false) {
    		    return false;
    		} 

    		return true;
    	}
    	
        
</script>

