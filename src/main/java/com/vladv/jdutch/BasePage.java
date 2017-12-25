package com.vladv.jdutch;

import org.apache.wicket.markup.html.WebPage;

public class BasePage extends WebPage {

	public BasePage() {

		this.setStatelessHint(true);
		this.setVersioned(false);
	}
}