// we want to replace all the het/de words with actual input elements, as following:
// het xxx becomes:
//    <input type="radio" name="option1" value="het"> Het
//    <input type="radio" name="option1" value="de"> De
// and now the user can pick one of the two values.
// Additionally we want to send to the server the correct value as well since we already have it. 
// So we place it under a hidden field. With this trick we prevent the server to do additional text parsing which
// will consume time and memory for nothing.
replaceAllDeAndHetWithInput = function() {

	// we know that all the contents of the test are separated by <p> tags for each line - this is how the summernote component works
	var el = $('#inputform p');

	if ($.trim(el.html())) {
		
		el.each(function(index) {

			var text = $(this).text();
			var contents = $(this).contents();
			
			// there is an additional div enclosing the btn-group but that because of the call of .html() below
			var strelem = "<div><div class='btn-group space' data-toggle='buttons'>"
			      	      +"	   <label class='btn btn-default'>"
			              +"	     <input type='radio' name='options" + index + "' value='de'> De"
			              +"	   </label>"
			              +"	   <label class='btn btn-default'>"
				          +"	     <input type='radio' name='options" + index + "' value='het'> Het"
			              +"	   </label>"
			              +"    </div>";
			
			$(this).replaceWith(function(idx, text) {
				// if the word starts with de
				if(text.indexOf('de ') == 0) {
					// keeping the original value in a hidden element to avoid additional processing on server
					strelem = strelem + text.substring(2) + "<input type='text' name='value" + index + "' hidden='hidden' value='de'/><br/></div>";
					
					// will not contain the enclosing <div> because the html() takes the inner html not the outer html
					return $(strelem).html();

					// if the word starts with het
				} else if(text.indexOf('het ') == 0) {
					
					// keeping the original value in a hidden element to avoid additional processing on server
					strelem = strelem + text.substring(3) + "<input type='text' name='value" + index + "' hidden='hidden' value='het'/><br/></div>";
					
					// will not contain the enclosing <div> because the html() takes the inner html not the outer html
					return $(strelem).html();
				}
				return 'invalid de/het'
			});
		});
	}
};