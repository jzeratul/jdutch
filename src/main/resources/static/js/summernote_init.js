$(document).ready(function() {
	$('#summernote').summernote({
		  toolbar: [
		    // [groupName, [list of button]]
		    ['style', ['bold','clear']],
		  ]
		});
});

adaptForm = function() {
	$('#htc').text($('.note-editable.panel-body').html())
}