replaceAllBoldElementsWithInput = function() {
	
	var idx = 0;
	var el = $('b');
	if($.trim(el.html()) ) {
		
		el.replaceWith(function() {
			
			var txt = $(this).contents().text();
			var newelem = "<input type='text' autocomplete='off' name='newvalue"+idx+"' id='newvalue"+idx+"' class='newvalues' />";
			var hidelem = "<input type='text' autocomplete='off' name='initialvalue"+idx+"' id='initialvalue"+idx+"' value='"+txt+"' class='oldvalues' hidden='hidden'  />";
			idx++;
			return newelem = newelem.concat(hidelem);
	    });
	}
};

submitForm = function() {

}