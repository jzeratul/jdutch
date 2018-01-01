package com.vladv.jdutch.articletest;

import org.junit.Test;

import com.vladv.jdutch.JWicketTest;
import com.vladv.jdutch.articletest.ArticleTestPage;

public class ArticleTestPageTest extends JWicketTest {

	@Test
	public void testBasicRenderPage() {

		getTester().startPage(ArticleTestPage.class);
		getTester().assertRenderedPage(ArticleTestPage.class);
	}
}
