package com.vladv.jdutch.verbtest;

import org.junit.Test;

import com.vladv.jdutch.JWicketTest;
import com.vladv.jdutch.verbtest.EditVerbTestPage;

public class EditVerbTestPageTest extends JWicketTest {

	@Test
	public void testBasicRenderPage() {

		getTester().startPage(EditVerbTestPage.class);
		getTester().assertRenderedPage(EditVerbTestPage.class);
	}
}
