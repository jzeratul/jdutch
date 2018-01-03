replaceAllBoldElementsWithInput = function() {
	
	var idx = 0;
	var el = $('b');
	if($.trim(el.html()) ) {
		el.replaceWith(function() {
			var newelem = $("<input type='text' autocomplete='off' />");
			newelem.attr('name', 'newvalue' + idx);
			var oldelem = $("<input type='text' hidden='hidden' autocomplete='off' />");
			oldelem.attr('name', 'initialvalue' + idx);
			oldelem.attr('value', $(this).contents().text());
			idx++;
			return newelem.append($(this).contents()).append(oldelem);
	    });
	}
};