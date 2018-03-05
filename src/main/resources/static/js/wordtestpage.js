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
				var strelem = "<div class='form-group' id='formgroup" + index + "'>";
				strelem = strelem.concat(
				   "<label class='col-xs-3 control-label'>" + ((reverse) ? elements[1] : elements[0]) + "</label>",
				   "<div class='col-xs-3 col-offset-3'>" +
				   		"<input type='text' autocomplete='off' name='newvalue" + index + "' id='newvalue" + index + "' class='form-control col-xs-12'  aria-describedby='helpBlock" + index + "' />" +
				   		"<span id='helpBlock" + index + "' class='help-block'></span>",
				   "</div>", 
				   "<input type='text' autocomplete='off' name='initialvalue" + index + "' id='initialvalue" + index + "' hidden='hidden' value='" + ((reverse) ? elements[0] : elements[1]) + "' />",
				   "<div class='col-xs-2'>" +
					   "<a class='btn btn-default' onclick='justValidate(" + index + ");'><span class='glyphicon glyphicon-play-circle'></span></a>",
					   "<a class='btn btn-default' onclick='validateAndHelp(" + index + ");'><span class='glyphicon glyphicon-question-sign'></span></a>" +
				   "</div>",
				 "</div>"
				);

				return $(strelem);
		    });
		});
	}
};

function justValidate(index) {

	formgroup = $('#formgroup' + index);
	if($('#newvalue' + index).val() == $('#initialvalue' + index).val()) {
		formgroup.addClass("has-success");
		formgroup.removeClass("has-error");
		$('#helpBlock' + index).text("");
	} else {
		formgroup.addClass("has-error");
		formgroup.removeClass("has-success");
	}
}

function validateAndHelp(index) {
	
	if($('#newvalue' + index).val() == $('#initialvalue' + index).val()) {
		$('#formgroup' + index).addClass("has-success");
		formgroup.removeClass("has-error");
		$('#helpBlock' + index).text("");
	} else {
		$('#formgroup' + index).addClass("has-error");
		$('#helpBlock' + index).text("ooopsie... expected: " + $('#initialvalue' + index).val());
		formgroup.removeClass("has-success");
	}
}