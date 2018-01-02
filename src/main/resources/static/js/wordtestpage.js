// TODO
prepareWordTestPage = function() {
	
	// we know that all the contents of the test are separated by <p> tags for each line - this is how the summernote component works
	var el = $('#inputform p');

	var testtype = $('#inputform select').find(":selected").text();
	
	// the default value is NL 2 RO
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
				   "<label class='col-xs-5 control-label'>" + ((reverse) ? elements[1] : elements[0]) + "</label>",
				   "<div class='col-xs-4'><input type='text' autocomplete='off' name='element" + index + "' class='form-control col-sm-3' /></div>", 
				   "<input type='text' autocomplete='off' name='element_original" + index + "' hidden='hidden' value='" + ((reverse) ? elements[0] : elements[1]) + "' />",
				   "</div>"
				);

				return $(strelem);
		    });
		});
	}
};