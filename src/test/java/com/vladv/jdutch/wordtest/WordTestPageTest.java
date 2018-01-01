package com.vladv.jdutch.pages;

import org.junit.Test;

import com.vladv.jdutch.JWicketTest;

public class WordTestPageTest extends JWicketTest {

	@Test
	public void testBasicRenderPage() {

		getTester().startPage(WordTestPage.class);
		getTester().assertRenderedPage(WordTestPage.class);
	}
}
