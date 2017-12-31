// TODO
prepareWordTestPage = function() {
	
	// we know that all the contents of the test are separated by <p> tags for each line - this is how the summernote component works
	var el = $('#inputform p');

	var testtype = $('#inputform select').find(":selected").text();
	if(testtype=="RO 2 NL") {
		var reverse = true
	}
	
	if ($.trim(el.html())) {
		
		el.each(function(index) {

			var text = $(this).text();
			var elements = text.split("=");
			
			$(this).replaceWith(function() {
				
				// there is an additional div enclosing the btn-group but that because of the call of .html() below
				var strelem = "<div class='form-group'>";
				strelem = strelem.concat(
				   "<label class='col-sm-2 control-label'>" + ((reverse) ? elements[1] : elements[0]) + "</label>",
				   "<div class='col-sm-10'><input type='text' autocomplete='off' name='element" + index + "' class='form-control' /></div>", 
				   "<input type='text' autocomplete='off' name='element_original" + index + "' hidden='hidden' value='" + ((reverse) ? elements[0] : elements[1]) + "' />",
				   "</div>"
				);

				return $(strelem);
		    });
		});
	}
};