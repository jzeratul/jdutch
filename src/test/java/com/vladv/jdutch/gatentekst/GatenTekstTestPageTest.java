package com.vladv.jdutch.gatentekst;

import org.junit.Test;

import com.vladv.jdutch.JWicketTest;
import com.vladv.jdutch.gatentekst.GatenTekstTestPage;

public class GatenTekstTestPageTest extends JWicketTest {

	@Test
	public void testBasicRenderPage() {

		getTester().startPage(GatenTekstTestPage.class);
		getTester().assertRenderedPage(GatenTekstTestPage.class);
	}
}
