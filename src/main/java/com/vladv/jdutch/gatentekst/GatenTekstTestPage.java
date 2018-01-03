package com.vladv.jdutch.gatentekst;

import java.util.List;

import org.wicketstuff.annotation.mount.MountPath;

import com.vladv.jdutch.JDutchApplication;
import com.vladv.jdutch.pages.templates.TestPage;

@MountPath("/gatentekst")
public class GatenTekstTestPage extends TestPage<GatenTekstTest> {

	@Override
	protected List<GatenTekstTest> getTests() {
		return JDutchApplication.getAllGatenTeksts();
	}

	@Override
	protected String appendJavascriptOnTestClick() {
		return "replaceAllBoldElementsWithInput();";
	}
}