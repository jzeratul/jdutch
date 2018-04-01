package com.vladv.jdutch.wordtest;

import java.util.List;

import org.wicketstuff.annotation.mount.MountPath;

import com.vladv.jdutch.JDutchApplication;
import com.vladv.jdutch.pages.templates.EditTestPage;

@MountPath("/editword")
public class EditWordTestPage extends EditTestPage<WordTest> {

	@Override
	protected String getTestHelpMessage() {
		return "Input text line by line in this format:<br/>\r\n" + 
				"          het huis=casa<br/>\r\n" + 
				"          dus=deci<br/>\r\n" + 
				"          ....";
	}

	@Override
	protected WordTest getNewObject() {
		return new WordTest();
	}

	@Override
	protected void saveTest(WordTest test) {
		JDutchApplication.getApp().getWordTestRepository().save(test);
	}

	@Override
	protected void deleteTest(String testname) {
		JDutchApplication.getApp().getWordTestRepository().deleteWordTestByTestname(testname);
	}

	@Override
  protected List<WordTest> getTests() {
    return JDutchApplication.getAllWordTests();
  }

  @Override
	protected Class<? extends EditTestPage<?>> getEditPageClass() {
		return EditWordTestPage.class;
	}

  @Override
  protected String getOnSaveJS() {
    return "prepareWordTestPage();";
  }
}