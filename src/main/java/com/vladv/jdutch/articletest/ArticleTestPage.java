package com.vladv.jdutch.articletest;

import java.util.List;
import java.util.Set;

import org.apache.wicket.request.IRequestParameters;
import org.apache.wicket.util.string.StringValue;
import org.wicketstuff.annotation.mount.MountPath;

import com.vladv.jdutch.JDutchApplication;
import com.vladv.jdutch.pages.templates.TestPage;

@MountPath("/article")
public class ArticleTestPage extends TestPage<ArticleTest> {

	@Override
	protected List<ArticleTest> getTests() {
		return JDutchApplication.getAllArticles();
	}

	@Override
	protected String appendJavascriptOnTestClick() {
		return "replaceAllDeAndHetWithInput();";
	}

	@Override
	protected String takeTest(String obj, IRequestParameters requestParameters) throws Exception {
		Set<String> parameterNames = requestParameters.getParameterNames();

		// there is one additional item that should not be considered - the submit button
		int numberOfItems = (parameterNames.size() - 1) / 2;

		StringBuilder results = new StringBuilder("Out of ").append(numberOfItems).append(" items you got: ");

		int ok = 0;
		int nok = 0;
		for (int p = 0; p < numberOfItems; p++) {
			StringValue selected = requestParameters.getParameterValue("options" + p);
			StringValue original = requestParameters.getParameterValue("value" + p);

			if (selected.toString().trim().equalsIgnoreCase(original.toString().trim())) {
				ok++;
			} else {
				nok++;
			}
		}
		results.append(ok + " right and ").append(nok + " wrong.");

		if (nok == 0 && ok == numberOfItems) {
			results.append(" You are awesomeeee!!");
		}

		return results.toString();
	}
}