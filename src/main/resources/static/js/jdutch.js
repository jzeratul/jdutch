$(document).ready(function() {
	adaptToNiceScroll();
	showHideSubmitButton();
});

adaptToNiceScroll = function() {

	$(".scroll").niceScroll({
		autohidemode : 'false'
	});
	
}

showHideSubmitButton = function() {
	var contents = $("#testcontents").html();
	
	var submit = $("#submitid");
	var testme = $("#testme");
	
	if(contents) {
		submit.show();
		testme.show();
	} else {
		testme.hide();
		submit.hide();
	}
	
}

makeActiveThisElement = function(itemId, allItems) {

	var item = $(itemId);
	var all = $(allItems);

	jQuery.each(all, function(i, el) {
		$(this).removeClass('active');
	});

	item.addClass('active');
}