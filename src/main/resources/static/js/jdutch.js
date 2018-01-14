$(document).ready(function() {
	$(".scroll").niceScroll({
		autohidemode : 'false'
	});
	
	showHideSubmitButton();
});

showHideSubmitButton = function() {
	var contents = $("#testcontents").html();
	
	var submit = $("#submitid");
	
	if(contents) {
		submit.show();
	} else {
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