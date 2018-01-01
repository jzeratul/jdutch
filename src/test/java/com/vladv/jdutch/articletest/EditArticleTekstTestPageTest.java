package com.vladv.jdutch.articletest;

import org.junit.Test;

import com.vladv.jdutch.JWicketTest;
import com.vladv.jdutch.articletest.EditArticleTestPage;

public class EditArticleTekstTestPageTest extends JWicketTest {

	@Test
	public void testBasicRenderPage() {

		getTester().startPage(EditArticleTestPage.class);
		getTester().assertRenderedPage(EditArticleTestPage.class);
	}
}
