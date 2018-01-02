package com.vladv.jdutch.verbtest;

import java.util.List;

import org.wicketstuff.annotation.mount.MountPath;

import com.vladv.jdutch.JDutchApplication;
import com.vladv.jdutch.pages.templates.EditTestPage;

@MountPath("/editverb")
public class EditVerbTestPage extends EditTestPage<VerbTest> {

	@Override
	protected String getTestHelpMessage() {
		return "some hits here";
	}

	@Override
	protected VerbTest getNewObject() {
		return new VerbTest();
	}

	@Override
	protected void saveTest(VerbTest test) {
		JDutchApplication.getApp().getVerbTestRepository().save(test);
	}

	@Override
	protected void deleteTest(String testname) {
		JDutchApplication.getApp().getVerbTestRepository().deleteVerbTestByTestname(testname);
	}

	@Override
	protected List<VerbTest> getTests() {
		return JDutchApplication.getAllVerbs();
	}

	@Override
	protected Class<? extends EditTestPage<?>> getEditPageClass() {
		return EditVerbTestPage.class;
	}
}