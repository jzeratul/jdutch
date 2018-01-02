package com.vladv.jdutch.gatentekst;

import java.util.List;
import java.util.Set;

import org.apache.wicket.request.IRequestParameters;
import org.wicketstuff.annotation.mount.MountPath;

import com.vladv.jdutch.JDutchApplication;
import com.vladv.jdutch.pages.templates.EditTestPage;
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

	@Override
	protected Class<? extends EditTestPage<?>> getEditPageClass() {
		return EditGatenTekstTestPage.class;
	}

	@Override
	protected String takeTest(String obj, IRequestParameters requestParameters) throws Exception {
		Set<String> parameterNames = requestParameters.getParameterNames();
		int nrParams = parameterNames.size() - 2; // two params sent from ui are out of scope

		int items = nrParams / 2;

		StringBuilder results = new StringBuilder("Out of ").append(items).append(" items you got: ");

		int ok = 0;
		int nok = 0;
		for (int p = 0; p < items; p++) {

			String paramName = "element" + p;
			String paramNameOriginal = "element" + p + "_original";
			String paramNewValue = requestParameters.getParameterValue(paramName).toString();
			String paramOriginalValue = requestParameters.getParameterValue(paramNameOriginal).toString();

			if (paramOriginalValue.trim().equalsIgnoreCase(paramNewValue.trim())) {
				ok++;
			} else {
				nok++;
			}
		}
		results.append(ok + " right and ").append(nok + " wrong.");

		if (nok == 0 && ok == items) {
			results.append(" You are awesomeeee!!");
		}

		return results.toString();
	}

}