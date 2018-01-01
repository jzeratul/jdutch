package com.vladv.jdutch.pages;

import org.junit.Test;

import com.vladv.jdutch.JWicketTest;

public class GatenTekstTestPageTest extends JWicketTest {

	@Test
	public void testBasicRenderPage() {

		getTester().startPage(GatenTekstTestPage.class);
		getTester().assertRenderedPage(GatenTekstTestPage.class);
	}
}
