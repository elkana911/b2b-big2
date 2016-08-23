(function ($) {	

	var selectorOwner,
	    cCount = 0,
	    activePanel,	
		cTemplates = {
		panel : $('<div class="citiesPicker-panel" />'),
        kota  : $('<div class="citiesPicker-kota">&nbsp;</div>'),
        divTable  : $('<table class="citiesPicker-table" width="950px" height="500px">&nbsp;</table>'),
        divRow  : $('<tr class="citiesPicker-row">&nbsp;</tr>'),
//        divCol  : $('<td class="citiesPicker-col" style="width:100px; vertical-align:top; border-right:thin solid #fff;">&nbsp;</td>')
        divCol  : $('<td class="citiesPicker-col">&nbsp;</td>')
        
	};	
	
	$.fn.citiesPicker = function (options){
		
		return this.each(function(){
			var element = $(this),
				opts         = $.extend({}, $.fn.citiesPicker.defaults, options),
				newPanel = cTemplates.panel.clone().attr('id', 'citiesPicker_panel-' + cCount),
				panelId = newPanel[0].id,
				divTable = cTemplates.divTable.clone(),		
				divRow = cTemplates.divRow.clone(),				
				divKolom1 = cTemplates.divCol.clone(),
				divKolom2 = cTemplates.divCol.clone(),
				divKolom3 = cTemplates.divCol.clone(),
				divKolom4 = cTemplates.divCol.clone(),
				kota
			;

			
			divKolom1.appendTo(divRow);
			divKolom2.appendTo(divRow);
			divKolom3.appendTo(divRow);
			divKolom4.appendTo(divRow);

			divRow.appendTo(divTable);
			divTable.appendTo(newPanel);
			
			$.each(opts.cities, function (i, value) {
                kota = cTemplates.kota.clone();
                //kota.css("background-color", "magenta");
				
				kota.text(value);

                if (opts.cities[i] === "Jakarta") {
                  //  kota.addClass(transparent).text('X');
                  //  $.fn.colorPicker.bindPanel(newHexField, kota, transparent);
                } else {
                  //  kota.css("background-color", "#" + this);
                    $.fn.citiesPicker.bindPanel(kota, value);
                }
                
                kota.appendTo(divTable);
            });


			
			$("body").append(newPanel);
            newPanel.hide();
			
			//modif input
			//element.css("background-color", "#000");

            element.bind("click", function () {
                if( element.is( ':not(:disabled)' ) ) {
					//nongolin popup
					//alert('hola ' + panelId);
                   $.fn.citiesPicker.togglePanel($('#' + panelId), $(this));
                }
            });
            element.bind("keydown", function () {
				$.fn.citiesPicker.hidePanel();
            });
        //    if( options && options.onColorChange ) {
       //       element.data('onColorChange', options.onColorChange);
         //   } else {
        //      element.data('onColorChange', function() {} );
          //  }
			
			
		//	alert('hola ' + cCount);
			
			cCount++;		
		});
	}
	
	    /**
     * Extend colorPicker with... all our functionality.
    **/
    $.extend(true, $.fn.citiesPicker, {
		/**
         * Check whether user clicked on the selector or owner.
        **/
        checkMouse : function (event, panelId) {
            var selector = activePanel,
                selectorParent = $(event.target).parents("#" + selector.attr('id')).length;

            if (event.target === $(selector)[0] || event.target === selectorOwner[0] || selectorParent > 0) {
                return;
            }

            $.fn.citiesPicker.hidePanel();
        },
		
        /**
         * Hide the color palette modal.
        **/
        hidePanel : function () {//alert('hidePanel');
            $(document).unbind("mousedown", $.fn.citiesPicker.checkMouse);

            $('.citiesPicker-panel').hide();
        },

        /**
         * Show the color palette modal.
        **/
        showPanel : function (panel) {
			
         //   var hexColor = selectorOwner.prev("input").val();
//alert('showPanel' + selectorOwner.val());
            panel.css({
                top: selectorOwner.offset().top + (selectorOwner.outerHeight()),
                left: selectorOwner.offset().left
            });
          //  $("#color_value").val(hexColor);
            panel.show();
            $(document).bind("mousedown", $.fn.citiesPicker.checkMouse);
        },

        /**
         * Toggle visibility of the colorPicker palette.
        **/
        togglePanel : function (panel, origin) {
			//alert('togglePanel');
            // selectorOwner is the clicked .colorPicker-picker.
            if (origin) {
                selectorOwner = origin;
            }
//alert('togglePanel1');
            activePanel = panel;

            if (activePanel.is(':visible')) {
                $.fn.citiesPicker.hidePanel();

            } else {
                $.fn.citiesPicker.showPanel(panel);

            }
        },
        /**
         * Update the input with a newly selected cities.
        **/
        changeCity : function (value) {
            selectorOwner.val(value).change();
            $.fn.citiesPicker.hidePanel();
        },
		      
		        /**
         * Bind events to the color palette swatches.
        */
        bindPanel : function (element, city) {
            //color = color ? color : $.fn.citiesPicker.toHex(element.css("background-color"));

            element.bind({
                click : function (ev) {
                    //lastColor = color;

                	$.fn.citiesPicker.changeCity(city);
                },
                mouseover : function (ev) {
                 //   lastColor = paletteInput.val();

                 //   $(this).css("border-color", "#598FEF");

                  //  paletteInput.val(color);

                  //  $.fn.colorPicker.previewColor(color);
                },
                mouseout : function (ev) {
                  //  $(this).css("border-color", "#000");

                  //  paletteInput.val(selectorOwner.css("background-color"));

                  //  paletteInput.val(lastColor);

                   // $.fn.colorPicker.previewColor(lastColor);
                }
            });
        }


	});
		
	$.fn.citiesPicker.defaults = {
        pickerDefault : "FFFFFF",

        // Default color set.
       cities: [
'area:JAWA', 'Bandung', 'Banyuwangi', 'Jakarta', 'Malang', 'Semarang', 'Solo', 'Surabaya', 'Yogyakarta', 'area:SUMATERA', 'Aceh', 'Lampung', 'Batam', 'Bengkulu', 'Sitoli', 'Jambi', 'Lhokseumawe', 'Medan', 'Meulaboh', 'Natuna', 'Padang', 'Palembang', 'area:KALIMANTAN', 'Balikpapan', 'Banjarmasin', 'Berau', 'Ketapang', 'Kotabaru', 'Malinau', 'Melak', 'Nunukan', 'Palangkaraya', 'area:SULAWESI', 'Buton', 'Gorontalo', 'Kendari', 'Luwuk', 'Makassar', 'Mamuju', 'Manado', 'Naha', 'Palu', 'Pomala', 'Poso', 'Talaud', 'Wakatobi', 'Selayar', 'area:BALI', 'Denpasar', 'area:NUSA TENG.', 'Alor', 'Bima', 'Ende', 'Kupang', 'area:PAPUA', 'Sorong', 'Ternate', 'Timika', 'Tobelo', 'Wamena', 'area:INTERNATIONAL', 'Singapore', 'Kuala Lumpur', 'Bangkok'
//            "Jakarta (CGK)", "Solo", "Semarang", "Denpasar"
        ],
//        cities: window.airports.slice(0), percuma
        // If we want to simply add more colors to the default set, use addColors.
       // addColors : [],

        // Show hex field
        //showHexField: true
    };

})(jQuery);
