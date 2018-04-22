package com.vladv.jdutch.articletest;

import java.util.List;

import org.wicketstuff.annotation.mount.MountPath;

import com.vladv.jdutch.JDutchApplication;
import com.vladv.jdutch.pages.templates.EditExamPage;

@MountPath("/editarticle")
public class EditArticleTestPage extends EditExamPage<ArticleTest> {

	@Override
	protected String getTestHelpMessage() {
		return "Input text line by line in this format:<br/>" + 
				"          het huis<br/>" + 
				"          de vrouw<br/>" + 
				"          ....";
	}

	@Override
	protected ArticleTest getNewObject() {
		return new ArticleTest();
	}

	@Override
	protected void saveTest(ArticleTest test) {
		JDutchApplication.getApp().getArticleTestRepository().save(test);
	}

	@Override
	protected void deleteTest(String testname) {
		JDutchApplication.getApp().getArticleTestRepository().deleteArticleTestByTestname(testname);
	}

	@Override
	protected List<ArticleTest> getTests(String category) {
		return JDutchApplication.getAllArticles();
	}

	@Override
	protected Class<? extends EditExamPage<?>> getEditPageClass() {
		return EditArticleTestPage.class;
	}
}