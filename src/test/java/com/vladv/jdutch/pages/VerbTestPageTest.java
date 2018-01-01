package com.vladv.jdutch.pages;

import org.junit.Test;

import com.vladv.jdutch.JWicketTest;

public class VerbTestPageTest extends JWicketTest {

	@Test
	public void testBasicRenderPage() {

		getTester().startPage(VerbTestPage.class);
		getTester().assertRenderedPage(VerbTestPage.class);
	}
}
