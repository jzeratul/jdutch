package com.vladv.jdutch.pages;

import org.junit.Test;

import com.vladv.jdutch.JWicketTest;

public class EditVerbTestPageTest extends JWicketTest {

	@Test
	public void testBasicRenderPage() {

		getTester().startPage(EditVerbTestPage.class);
		getTester().assertRenderedPage(EditVerbTestPage.class);
	}
}
