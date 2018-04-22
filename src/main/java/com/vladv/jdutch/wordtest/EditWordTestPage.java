package com.vladv.jdutch.wordtest;

import java.util.List;

import org.wicketstuff.annotation.mount.MountPath;

import com.vladv.jdutch.JDutchApplication;
import com.vladv.jdutch.pages.templates.EditExamPage;

@MountPath("/editword")
public class EditWordTestPage extends EditExamPage<WordTest> {

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
  protected List<WordTest> getTests(String category) {
    return JDutchApplication.getAllWordTests(category);
  }

  @Override
	protected Class<? extends EditExamPage<?>> getEditPageClass() {
		return EditWordTestPage.class;
	}

  @Override
  protected String getOnSaveJS() {
    return "prepareWordTestPage();";
  }

  @Override
  protected List<String> getTestCategories() {
    return JDutchApplication.getAllWordTestCategories();
  }
}