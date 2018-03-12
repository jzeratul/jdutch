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
			var word = reverse ? elements[1].trim() : elements[0].trim();
			var meaning = reverse ? elements[0].trim() : elements[1].trim();
			
			$(this).replaceWith(function() {
				
				// there is an additional div enclosing the btn-group but that because of the call of .html() below
				var strelem = "<div class='form-group' id='formgroup" + index + "'>";
				strelem = strelem.concat(
				   "<label class='col-xs-3 control-label'>" + word + "</label>",
				   "<div class='col-xs-3 col-offset-3'>" +
				   		"<input type='text' autocomplete='off' name='typed" + index + "' id='typed" + index + "' class='noNotSubmitNnEnter form-control col-xs-12'  aria-describedby='helpBlock" + index + "' />",

					   	"<span id='feedback" + index + "' class='glyphicon form-control-feedback' aria-hidden='true'></span>",
					   	"<span class='sr-only'></span>",
				   		"<span id='helpBlock" + index + "' class='help-block'></span>",
				   "</div>", 
				   "<input type='text' autocomplete='off' name='definition" + index + "' id='definition" + index + "' hidden='hidden' value='" + meaning + "' />",
				   "<div class='col-xs-2'>" +
					   "<a class='btn btn-xs btn-default' onclick='justValidate(" + index + ");'><span class='glyphicon glyphicon-play-circle'>Test</span></a>",
					   "<a class='btn btn-xs btn-default' onclick='showHint(" + index + ");'><span class='glyphicon glyphicon-question-sign'>Hint</span></a>" +
					   "<a class='btn btn-xs btn-default' onclick='validateAndHelp(" + index + ");'><span class='glyphicon glyphicon-exclamation-sign'>TestShow</span></a>" +
				   "</div>",
				 "</div>"
				);

				return $(strelem);
		    });
		});
	}
};

$(function() {
	$('.noNotSubmitNnEnter').keydown(function(e) {
		if (e.keyCode == 13) {
			e.preventDefault();
		}
	});
});


function submitForm() {
	var el = $('#inputform .noNotSubmitNnEnter');
	el.each(function(index) {
		validateAndHelp(index);
	});
}

function showHint(index) {
	
	formgroup = $('#formgroup' + index);
	helpblock = $('#helpBlock' + index);
	definition = $('#definition' + index).val();
	if(definition) {
		definition = definition.trim();
	}
	hintstart = definition.indexOf("(");
	hintend = definition.indexOf(")");
	hint =  hintstart > 0 ? definition.substr(hintstart, hintend) : 0; 
	
	if(hint.length > 0 ) {
		helpblock.text("Hint: " + hint);
	} else {
		helpblock.text("No hint available. Add a hint between parentheses:  word=definition (hint) ");
	}
	formgroup.removeClass("has-success");
	
}

function justValidate(index) {
	
	formgroup = $('#formgroup' + index);
	helpblock = $('#helpBlock' + index);
	feedback = $('#feedback' + index);
	definition = $('#definition' + index).val();
	if(definition) {
		definition = definition.trim();
	}
	typed = $('#typed' + index).val().trim();
	hintstart = definition.indexOf("(");
	hintend = definition.indexOf(")");
	hint =  hintstart > 0 ? definition.substring(hintstart, hintend) : 0; 
	definitionWithoutHints = hintstart > 0 ? definition.substring(0, hintstart).trim() : definition.trim();
	
	var correct = false;
	
	synonymExists = definition.indexOf("|") > -1;
	if(synonymExists) {
		synonyms = definitionWithoutHints.split("|");
		for(var i = 0; i< synonyms.lenght; i++) {
			if(typed == synonyms[i].trim()) {
				correct = true;
				break;
			}
		}
	} else {
		if(typed == definitionWithoutHints) {
			correct = true;
		}
	}
	
	if(correct) {
		formgroup.addClass("has-success");
		formgroup.removeClass("has-error");
		helpblock.text("");
		feedback.removeClass("glyphicon-remove");
		feedback.addClass("glyphicon-ok");
	} else {
		formgroup.addClass("has-error");
		formgroup.removeClass("has-success");
		feedback.addClass("glyphicon-remove");
		feedback.removeClass("glyphicon-ok");
	}
}

function validateAndHelp(index) {
	
	formgroup = $('#formgroup' + index);
	helpblock = $('#helpBlock' + index);
	definition = $('#definition' + index).val();
	if(definition) {
		definition = definition.trim();
	}
	feedback = $('#feedback' + index);
	typed = $('#typed' + index).val().trim();
	hintstart = definition.indexOf("(");
	hintend = definition.indexOf(")");
	hint =  hintstart > 0 ? definition.substr(hintstart, hintend) : 0; 
	definitionWithoutHints = hintstart > 0 ? definition.substring(0, hintstart).trim() : definition.trim();
	
	var correct = false;
	
	synonymExists = definition.indexOf("|") > -1;
	if(synonymExists) {
		synonyms = definitionWithoutHints.split("|");
		for(var i = 0; i< synonyms.lenght; i++) {
			if(typed == synonyms[i].trim()) {
				correct = true;
				break;
			}
		}
	} else {
		if(typed == definitionWithoutHints) {
			correct = true;
		}
	}
	
	if(correct) {
		formgroup.addClass("has-success");
		formgroup.removeClass("has-error");
		helpblock.text("");
		feedback.removeClass("glyphicon-remove");
		feedback.addClass("glyphicon-ok");
	} else {
		formgroup.addClass("has-error");
		helpblock.text("ooopsie... expected: " + definition);
		formgroup.removeClass("has-success");
		feedback.addClass("glyphicon-remove");
		feedback.removeClass("glyphicon-ok");
	}
}