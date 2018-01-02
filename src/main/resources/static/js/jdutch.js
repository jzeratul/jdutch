$(document).ready(function() {
	$(".scroll").niceScroll({
		autohidemode : 'false'
	});
});

makeActiveThisElement = function(itemId, allItems) {

	var item = $(itemId);
	var all = $(allItems);

	jQuery.each(all, function(i, el) {
		$(this).removeClass('active');
	});

	item.addClass('active');
}