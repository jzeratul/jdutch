package com.vladv.jdutch.pages;

import org.junit.Test;

import com.vladv.jdutch.JWicketTest;

public class ArticleTestPageTest extends JWicketTest {

	@Test
	public void testBasicRenderPage() {

		getTester().startPage(ArticleTestPage.class);
		getTester().assertRenderedPage(ArticleTestPage.class);
	}
}
