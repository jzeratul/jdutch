$(document).ready(function() {
	replaceAllBoldElementsWithInput();
});

replaceAllBoldElementsWithInput = function() {
	
	var el = $('b');
	if($.trim(el.html()) ) {
		el.replaceWith(function(){
			return $("<input type='text' autocomplete='off' />").append($(this).contents());
	    });
	}
};