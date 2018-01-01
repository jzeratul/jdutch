package com.vladv.jdutch.home;

import org.wicketstuff.annotation.mount.MountPath;

import com.giffing.wicket.spring.boot.context.scan.WicketHomePage;
import com.vladv.jdutch.pages.templates.BasePage;

@WicketHomePage
@MountPath("/")
public class HomePage extends BasePage {

	@Override
	protected void onInitialize() {
		super.onInitialize();
	}
}