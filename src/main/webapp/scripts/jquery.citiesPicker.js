(function ($) {	

	var selectorOwner,
		cCount = 0,
		maxCitiesPerRow = 19,
	    activePanel,	
		cTemplates = {
		divPanel : $('<div class="citiesPicker-panel" />'),
        divTitle  : $('<div class="citiesPicker-title"></div>'),
        divTable  : $('<table class="citiesPicker-table" width="750px" height="330px">&nbsp;</table>'),
        divRow  : $('<tr class="citiesPicker-row">&nbsp;</tr>'),
        divCol  : $('<td class="citiesPicker-col" style="width:60px; vertical-align:top; border-right:thin solid #fff;"></td>'),
        divKota  : $('<div class="citiesPicker-kota">&nbsp;</div>')
	};	
	
	$.fn.citiesPicker = function (options){
		
		return this.each(function(){
			var element = $(this),
				colCounter = 0,
				rowCounter = 0,
				opts         = $.extend({}, $.fn.citiesPicker.defaults, options),
				newPanel = cTemplates.divPanel.clone().attr('id', 'citiesPicker_panel-' + cCount),
				panelId = newPanel[0].id,
				divTitle = cTemplates.divTitle.clone(),
				divTable = cTemplates.divTable.clone(),				
				divRow = cTemplates.divRow.clone(),				
				divKolom1 = cTemplates.divCol.clone(),				
				divKolom2 = cTemplates.divCol.clone(),				
				divKolom3 = cTemplates.divCol.clone(),				
				divKolom4 = cTemplates.divCol.clone(),				
				divKota
			;
			
			divKolom4.css('border-right', 'none');
			
			var dataList = $("#searchresults");
			dataList.empty();
			
			if (cCount === 0){
				divTitle.text("Pilih Kota Asal");
			} else {
				divTitle.text("Pilih Kota Tujuan");
			}
			divTitle.appendTo(newPanel);
			
			
			divKolom1.appendTo(divRow);
			divKolom2.appendTo(divRow);
			divKolom3.appendTo(divRow);
			divKolom4.appendTo(divRow);
			
			var firstarea = true;
			$.each(opts.cities, function (i, value) {
				
                divKota = cTemplates.divKota.clone();
                			
				var separator = value.substring(0, 5);
				if (separator == "area:" ) {
					filter = value.substring(5);
					
					if (firstarea){
						divKota.removeClass("citiesPicker-kota").addClass("citiesPicker-area-first");
						firstarea = false;
					}else{
						divKota.removeClass("citiesPicker-kota").addClass("citiesPicker-area");
					}
					
					divKota.text(filter);
				} else {
					divKota.text(value);
					$.fn.citiesPicker.bindPanel(divKota, value);
				}
				
				if (separator != "area:" ) {
					var opt = $("<option></option>").attr("value", value);
					dataList.append(opt);
				}
				
               	rowCounter++;
				
				if (rowCounter > maxCitiesPerRow){
					colCounter++;
					rowCounter = 1;
				}

				if (colCounter == 0)
                divKota.appendTo(divKolom1);
				else
				if (colCounter == 1)
                divKota.appendTo(divKolom2);
				else
				if (colCounter == 2)
                divKota.appendTo(divKolom3);
				else
				if (colCounter == 3)
                divKota.appendTo(divKolom4);
			
            });

			divRow.appendTo(divTable);
			divTable.appendTo(newPanel);

			$("body").append(newPanel);
            newPanel.hide();

            element.bind("click", function () {
                if( element.is( ':not(:disabled)' ) ) {
                   $.fn.citiesPicker.togglePanel($('#' + panelId), $(this));
                }
            });
			
			element.bind("keydown", function () {
				$.fn.citiesPicker.hidePanel();
                //if (dataList.is(':visible')) {
				//	$.fn.citiesPicker.hidePanel();
				//} else {
				//	$.fn.citiesPicker.showPanel(panel);
				//}
            });
			
			cCount++;		
		});
	}
	
	    /**
     * Extend citiesPicker with... all our functionality.
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
         * Hide the cities palette modal.
        **/
        hidePanel : function () {//alert('hidePanel');
            $(document).unbind("mousedown", $.fn.citiesPicker.checkMouse);

            $('.citiesPicker-panel').hide();
        },

        /**
         * Show the cities palette modal.
        **/
        showPanel : function (panel) {
            panel.css({
                top: selectorOwner.offset().top + (selectorOwner.outerHeight()),
                left: selectorOwner.offset().left
            });
        
            panel.show();
            $(document).bind("mousedown", $.fn.citiesPicker.checkMouse);
        },

        /**
         * Toggle visibility of the citiesPicker palette.
        **/
        togglePanel : function (panel, origin) {
            if (origin) {
                selectorOwner = origin;
            }

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
         * Bind events to the cities palette swatches.
        */
        bindPanel : function (element, city) {
            element.bind({
                click : function (ev) {
                    $.fn.citiesPicker.changeCity(city);
                },
                mouseover : function (ev) {},
                mouseout : function (ev) {}
            });
        }


	});
		
	$.fn.citiesPicker.defaults = {
        pickerDefault : "FFFFFF",

        // Default cities value set.
        cities: [
            'area:JAWA - BALI', 'Jakarta (CGK)', 'Jakarta (HLP)', 'Denpasar', 'Bandung', 'Banyuwangi', 'Malang', 'Semarang', 'Solo', 'Surabaya', 'Yogyakarta', 'area:SUMATERA', 'Aceh', 'Lampung', 'Batam', 'Bengkulu', 'Sitoli', 'Jambi', 'Lhokseumawe', 'Medan (KNO)', 'Meulaboh', 'Natuna', 'Padang', 'Palembang', 'area:KALIMANTAN', 'Balikpapan', 'Banjarmasin', 'Berau', 'Ketapang', 'Kotabaru', 'Malinau', 'Nunukan', 'Palangkaraya', 'area:SULAWESI', 'Gorontalo', 'Kendari', 'Luwuk', 'Makassar', 'Mamuju', 'Manado', 'Naha', 'Palu', 'Pomala', 'Poso', 'Wakatobi', 'Selayar', 'area:BALI', 'Denpasar', 'area:NUSA TENG.', 'Alor', 'Bima', 'Ende', 'Kupang', 'area:PAPUA', 'Sorong', 'Ternate', 'Timika', 'Tobelo', 'Wamena', 'area:INTERNATIONAL', 'Singapore (SIN)', 'Kuala Lumpur (KUL)', 'Bangkok'
//            'Bandung', 'Banyuwangi', 'Jakarta' 
        ],
    };

})(jQuery);

