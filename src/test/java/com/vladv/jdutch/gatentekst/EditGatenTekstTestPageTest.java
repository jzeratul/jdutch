package com.vladv.jdutch.pages;

import org.junit.Test;

import com.vladv.jdutch.JWicketTest;

public class EditGatenTekstTestPageTest extends JWicketTest {

	@Test
	public void testBasicRenderPage() {

		getTester().startPage(EditGatenTekstTestPage.class);
		getTester().assertRenderedPage(EditGatenTekstTestPage.class);
	}
}
