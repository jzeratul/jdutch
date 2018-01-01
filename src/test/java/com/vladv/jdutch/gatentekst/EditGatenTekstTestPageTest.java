package com.vladv.jdutch.gatentekst;

import org.junit.Test;

import com.vladv.jdutch.JWicketTest;
import com.vladv.jdutch.gatentekst.EditGatenTekstTestPage;

public class EditGatenTekstTestPageTest extends JWicketTest {

	@Test
	public void testBasicRenderPage() {

		getTester().startPage(EditGatenTekstTestPage.class);
		getTester().assertRenderedPage(EditGatenTekstTestPage.class);
	}
}
