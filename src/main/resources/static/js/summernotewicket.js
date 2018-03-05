$(document).ready(function() {
	prepareSummerNote();
});

prepareSummerNote = function() {
	$('#summernote').summernote({
		  toolbar: [
		    // [groupName, [list of button]]
		    ['style', ['bold', 'underline', 'clear']],
		  ]
		});

	$('.note-editable.panel-body').html($('#htc').text())	
}

adaptForm = function() {
	$('#htc').text($('.note-editable.panel-body').html())
}