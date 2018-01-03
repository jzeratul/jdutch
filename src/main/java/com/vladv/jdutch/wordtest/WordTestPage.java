package com.vladv.jdutch.wordtest;

import java.util.Arrays;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.Model;
import org.wicketstuff.annotation.mount.MountPath;

import com.vladv.jdutch.JDutchApplication;
import com.vladv.jdutch.pages.templates.EditTestPage;
import com.vladv.jdutch.pages.templates.TestPage;

@MountPath("/words")
public class WordTestPage extends TestPage<WordTest> {

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
}