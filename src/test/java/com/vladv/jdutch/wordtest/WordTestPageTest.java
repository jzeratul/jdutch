package com.vladv.jdutch.wordtest;

import org.junit.Test;

import com.vladv.jdutch.JWicketTest;
import com.vladv.jdutch.wordtest.WordTestPage;

public class WordTestPageTest extends JWicketTest {

	@Test
	public void testBasicRenderPage() {

		getTester().startPage(WordTestPage.class);
		getTester().assertRenderedPage(WordTestPage.class);
	}
}
