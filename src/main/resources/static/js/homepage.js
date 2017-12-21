$(document).ready(function() {
	replaceAllBoldElementsWithInput();
});

var idx = 0;
replaceAllBoldElementsWithInput = function() {
	
	var el = $('b');
	if($.trim(el.html()) ) {
		el.replaceWith(function() {
			var newelem = $("<input type='text' autocomplete='off' />");
			newelem.attr('name', 'elem' + idx);
			idx++;
			return newelem.append($(this).contents());
	    });
	}
};