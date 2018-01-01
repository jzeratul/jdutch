package com.vladv.jdutch.verbtest;

import org.junit.Test;

import com.vladv.jdutch.JWicketTest;
import com.vladv.jdutch.verbtest.VerbTestPage;

public class VerbTestPageTest extends JWicketTest {

	@Test
	public void testBasicRenderPage() {

		getTester().startPage(VerbTestPage.class);
		getTester().assertRenderedPage(VerbTestPage.class);
	}
}
