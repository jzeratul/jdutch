package com.vladv.jdutch.pages;

import org.junit.Test;

import com.vladv.jdutch.JWicketTest;

public class HomePageTest extends JWicketTest {

	@Test
	public void testBasicRenderPage() {

		getTester().startPage(HomePage.class);
		getTester().assertRenderedPage(HomePage.class);
	}
}
