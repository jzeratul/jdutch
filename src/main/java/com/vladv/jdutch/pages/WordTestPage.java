package com.vladv.jdutch.pages;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wicketstuff.annotation.mount.MountPath;

@MountPath("/words")
public class WordTestPage extends BasePage {
	private static final Logger LOGGER = LoggerFactory.getLogger(WordTestPage.class);

	@Override
	protected void onInitialize() {
		super.onInitialize();
	}
}