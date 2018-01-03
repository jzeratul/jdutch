package com.vladv.jdutch.articletest;

import java.util.List;

import org.wicketstuff.annotation.mount.MountPath;

import com.vladv.jdutch.JDutchApplication;
import com.vladv.jdutch.pages.templates.TestPage;

@MountPath("/article")
public class ArticleTestPage extends TestPage<ArticleTest> {

	@Override
	protected List<ArticleTest> getTests() {
		return JDutchApplication.getAllArticles();
	}

	@Override
	protected String appendJavascriptOnTestClick() {
		return "replaceAllDeAndHetWithInput();";
	}
}