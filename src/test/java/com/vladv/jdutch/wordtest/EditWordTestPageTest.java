package com.vladv.jdutch.wordtest;

import org.junit.Test;

import com.vladv.jdutch.JWicketTest;
import com.vladv.jdutch.wordtest.EditWordTestPage;

public class EditWordTestPageTest extends JWicketTest {

	@Test
	public void testBasicRenderPage() {

		getTester().startPage(EditWordTestPage.class);
		getTester().assertRenderedPage(EditWordTestPage.class);
	}
}
