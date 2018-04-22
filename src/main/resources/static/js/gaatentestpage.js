
replaceAllBoldElementsWithInput = function() {
	
	var idx = 0;
	var el = $('b');
	if($.trim(el.html()) ) {
		
		el.replaceWith(function() {
			
			var txt = $(this).contents().text();
			var newelem = "<input type='text' autocomplete='off' name='newvalue"+idx+"' id='newvalue"+idx+"' class='newvalues' />" +
					"<span class='btn btn-danger hideme' id='result"+idx+"'></span>";
			var hidelem = "<input type='text' autocomplete='off' name='initialvalue"+idx+"' id='initialvalue"+idx+"' value='"+txt+"' class='oldvalues' hidden='hidden'/>";
			idx++;
			return newelem = newelem.concat(hidelem);
	    });
	}
};

submitForm = function() {

	var inputs = $('.newvalues');
	var x = 0;
	for(var i in inputs) {
		
		var v1 = $('#newvalue' + x).val();
		var v2 = $('#initialvalue' + x).val();
		
		if(v1 == v2) {
		} else {
			$('#result' + x).removeClass("hideme");
			$('#result' + x).html($('#initialvalue' + x).val());
		}
		x++;
	}
}