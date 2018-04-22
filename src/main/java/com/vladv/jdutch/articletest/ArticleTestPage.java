package com.vladv.jdutch.articletest;

import java.util.List;

import org.wicketstuff.annotation.mount.MountPath;

import com.vladv.jdutch.JDutchApplication;
import com.vladv.jdutch.pages.templates.EditExamPage;
import com.vladv.jdutch.pages.templates.ExamPage;

@MountPath("/article")
public class ArticleTestPage extends ExamPage<ArticleTest> {

	@Override
	protected List<ArticleTest> getTests(String category) {
		return JDutchApplication.getAllArticles();
	}

	@Override
	protected String appendJavascriptOnTestClick() {
		return "replaceAllDeAndHetWithInput();";
	}

  @Override
  protected Class<? extends EditExamPage<?>> getEditPageClass() {
    return EditArticleTestPage.class;
  }
}