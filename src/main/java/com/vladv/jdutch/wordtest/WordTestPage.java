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
import com.vladv.jdutch.pages.templates.EditExamPage;
import com.vladv.jdutch.pages.templates.ExamPage;

@MountPath("/words")
public class WordTestPage extends ExamPage<WordTest> {

	@Override
	protected List<WordTest> getTests(String ofCategory) {
		return JDutchApplication.getAllWordTests(ofCategory);
	}

	@Override
	protected String appendJavascriptOnTestClick() {
		return "prepareWordTestPage();";
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
  protected Class<? extends EditExamPage<?>> getEditPageClass() {
    return EditWordTestPage.class;
  }
}