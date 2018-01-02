package com.vladv.jdutch.wordtest;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.IRequestParameters;
import org.apache.wicket.util.string.StringValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wicketstuff.annotation.mount.MountPath;

import com.vladv.jdutch.JDutchApplication;
import com.vladv.jdutch.pages.templates.EditTestPage;
import com.vladv.jdutch.pages.templates.TestPage;

@MountPath("/words")
public class WordTestPage extends TestPage<WordTest> {
	private static final Logger LOGGER = LoggerFactory.getLogger(WordTestPage.class);

	@Override
	protected List<WordTest> getTests() {
		return JDutchApplication.getAllWordTests();
	}

	@Override
	protected String appendJavascriptOnTestClick() {
		return "prepareWordTestPage();";
	}

	@Override
	protected Class<? extends EditTestPage<?>> getEditPageClass() {
		return EditWordTestPage.class;
	}

	@Override
	protected void contributeToForm(Form<String> form) {
		Model<String> testTypeModel = Model.of("NL 2 RO");
		DropDownChoice<String> testtype = new DropDownChoice<>("testtype", testTypeModel, Model.ofList(Arrays.asList("NL 2 RO", "RO 2 NL")));
		form.add(testtype);
		testtype.add(new AjaxFormComponentUpdatingBehavior("change") {

			@Override
			protected void onUpdate(AjaxRequestTarget target) {
				target.add(form);
				target.appendJavaScript("prepareWordTestPage();");
			}
		});
	}

	@Override
	protected String takeTest(String obj, IRequestParameters requestParameters) throws Exception {
		Set<String> parameterNames = requestParameters.getParameterNames();

		// there are two additional items that should not be considered - the submit
		// button and the testtype
		int numberOfItems = (parameterNames.size() - 2) / 2;

		StringBuilder results = new StringBuilder("Out of ").append(numberOfItems).append(" items you got: ");

		int ok = 0;
		int nok = 0;
		for (int p = 0; p < numberOfItems; p++) {
			StringValue typed = requestParameters.getParameterValue("element" + p);
			StringValue original = requestParameters.getParameterValue("element_original" + p);

			if (typed.isEmpty() || original.isEmpty()) {
				nok++;
				LOGGER.error("Null value in element" + p);
			} else {

				if (typed.toString().trim().equalsIgnoreCase(original.toString().trim())) {
					ok++;
				} else {
					nok++;
				}
			}
		}
		results.append(ok + " right and ").append(nok + " wrong.");

		if (nok == 0 && ok == numberOfItems) {
			results.append(" You are awesomeeee!!");
		}

		return results.toString();
	}

}