package com.vladv.jdutch.pages;

import org.junit.Test;

import com.vladv.jdutch.JWicketTest;

public class EditArticleTekstTestPageTest extends JWicketTest {

	@Test
	public void testBasicRenderPage() {

		getTester().startPage(EditArticleTestPage.class);
		getTester().assertRenderedPage(EditArticleTestPage.class);
	}
}
