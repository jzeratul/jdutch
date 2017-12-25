replaceAllBoldElementsWithInput = function() {
	
	var idx = 0;
	var el = $('b');
	if($.trim(el.html()) ) {
		el.replaceWith(function() {
			var newelem = $("<input type='text' autocomplete='off' />");
			newelem.attr('name', 'element' + idx);
			var oldelem = $("<input type='text'  hidden='hidden' autocomplete='off' />");
			oldelem.attr('name', 'element' + idx + '_original');
			oldelem.attr('value', $(this).contents().text());
			idx++;
			return newelem.append($(this).contents()).append(oldelem);
	    });
	}
};