package com.vladv.jdutch.gatentekst;

import java.util.List;

import org.wicketstuff.annotation.mount.MountPath;

import com.giffing.wicket.spring.boot.context.scan.WicketHomePage;
import com.vladv.jdutch.JDutchApplication;
import com.vladv.jdutch.pages.templates.EditExamPage;
import com.vladv.jdutch.pages.templates.ExamPage;

@WicketHomePage
@MountPath("/gatentekst")
public class GatenTekstTestPage extends ExamPage<GatenTekstTest> {

	@Override
	protected List<GatenTekstTest> getTests(String category) {
		return JDutchApplication.getAllGatenTeksts();
	}

	@Override
	protected String appendJavascriptOnTestClick() {
		return "replaceAllBoldElementsWithInput();";
	}

  @Override
  protected Class<? extends EditExamPage<?>> getEditPageClass() {
    return EditGatenTekstTestPage.class;
  }
}