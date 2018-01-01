package com.vladv.jdutch.home;

import org.junit.Test;

import com.vladv.jdutch.JWicketTest;
import com.vladv.jdutch.home.HomePage;

public class HomePageTest extends JWicketTest {

	@Test
	public void testBasicRenderPage() {

		getTester().startPage(HomePage.class);
		getTester().assertRenderedPage(HomePage.class);
	}
}
