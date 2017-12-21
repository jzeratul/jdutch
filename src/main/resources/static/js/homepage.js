$(document).ready(function() {
	replaceAllBoldElementsWithInput();
});

replaceAllBoldElementsWithInput = function() {
	
	var el = $('b');
	if($.trim(el.html()) ) {
		el.replaceWith(function() {
			var newelem = $("<input type='text' autocomplete='off' />");
			newelem.attr('name', 'elem' + $(this).index());
			return newelem.append($(this).contents());
	    });
	}
};