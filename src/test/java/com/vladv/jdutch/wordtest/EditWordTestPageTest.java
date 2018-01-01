package com.vladv.jdutch.pages;

import org.junit.Test;

import com.vladv.jdutch.JWicketTest;

public class EditWordTestPageTest extends JWicketTest {

	@Test
	public void testBasicRenderPage() {

		getTester().startPage(EditWordTestPage.class);
		getTester().assertRenderedPage(EditWordTestPage.class);
	}
}
