package com.vladv.jdutch.pages;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wicketstuff.annotation.mount.MountPath;

import com.giffing.wicket.spring.boot.context.scan.WicketHomePage;

@WicketHomePage
@MountPath("/")
public class HomePage extends BasePage {
	private static final Logger LOGGER = LoggerFactory.getLogger(HomePage.class);

	@Override
	protected void onInitialize() {
		super.onInitialize();
	}
}