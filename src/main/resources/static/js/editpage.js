$(document).ready(function() {
	$('#summernote').summernote({
		  toolbar: [
		    // [groupName, [list of button]]
		    ['style', ['bold','clear']],
		  ]
		});

	$('.note-editable.panel-body').html($('#htc').text())
});


adaptForm = function() {
	$('#htc').text($('.note-editable.panel-body').html())
}