package com.vladv.jdutch.pages.templates;

import java.util.List;

import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.IRequestParameters;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.util.string.StringValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vladv.jdutch.components.JFeedbackPanel;
import com.vladv.jdutch.domain.Test;

public abstract class TestPage<T extends Test> extends BasePage {
	private static final Logger LOGGER = LoggerFactory.getLogger(TestPage.class);

	@Override
	protected void onInitialize() {
		super.onInitialize();

		addTestsList();
		addTestContents();
	}

	private void addTestsList() {
		LoadableDetachableModel<List<T>> ldm = new LoadableDetachableModel<List<T>>() {

			@Override
			protected List<T> load() {
				return getTests();
			}
		};

		ListView<T> tests = new ListView<T>("tests", ldm) {

			@Override
			protected void populateItem(ListItem<T> item) {

				item.add(new Label("name", PropertyModel.of(item.getModelObject(), "testname")));
				item.add(new AjaxEventBehavior("click") {

					@Override
					protected void onEvent(AjaxRequestTarget target) {

						refresh(target, item.getModelObject().getTestcontents());
						target.appendJavaScript(appendJavascriptOnTestClick());

						highlightSelection(item.getMarkupId(), target);

						target.add(item);
					}
				});
			}
		};

		WebMarkupContainer testslist = new WebMarkupContainer("testslist");
		testslist.setOutputMarkupId(true);
		testslist.add(tests);
		add(testslist);
	}

	private void highlightSelection(String itemMarkupId, AjaxRequestTarget target) {
		String js = String.format("makeActiveThisElement('#%s', '#%s a');", itemMarkupId, get("testslist").getMarkupId());
		target.appendJavaScript(js);
	}

	public void refresh(AjaxRequestTarget target, String contents) {

		target.add(this.get("testcontents:form:contents").setDefaultModelObject(contents));
	}

	private void addTestContents() {
		WebMarkupContainer testcontents = new WebMarkupContainer("testcontents");
		add(testcontents);
		Form<String> form = new Form<String>("form", Model.of(""));

		final Label contents = new Label("contents", Model.of(""));
		form.add(contents);
		contents.setEscapeModelStrings(false);
		contents.setOutputMarkupId(true);

		final JFeedbackPanel feedback = new JFeedbackPanel("feedback");
		feedback.setOutputMarkupId(true);
		feedback.setVisible(false);
		testcontents.add(feedback);

		contributeToForm(form);

		AjaxButton submittest = new AjaxButton("submittest") {

			@Override
			protected void onSubmit(AjaxRequestTarget target) {
				super.onSubmit(target);

				Request request = RequestCycle.get().getRequest();
				IRequestParameters requestParameters = request.getRequestParameters();

				try {
					String results = takeTest(contents.getDefaultModelObjectAsString(), requestParameters);
					info(results);
					LOGGER.info(results);
				} catch (Exception e) {
					error(e.getMessage());
					LOGGER.error(e.getMessage(), e);
				}
				feedback.setVisible(true);
				target.add(feedback);
				target.add(getPage());
			}
		};
		submittest.setOutputMarkupId(true);
		form.add(submittest);

		testcontents.add(form);
	}

	protected void contributeToForm(Form<String> form) {

	}

	protected long countPairs(IRequestParameters requestParameters) {

		return requestParameters.getParameterNames().stream().filter(p -> p.contains("newvalue") || p.contains("initialvalue")).count() / 2;
	}

	protected String takeTest(String obj, IRequestParameters requestParameters) throws Exception {

		long pairs = countPairs(requestParameters); // we have pairs of newvalueX and initialvalueX

		int answeredOk = 0;
		int answeredNok = 0;
		for (int i = 0; i < pairs; i++) {

			StringValue newValue = requestParameters.getParameterValue("newvalue" + i);
			StringValue initialValue = requestParameters.getParameterValue("initialvalue" + i);

			if (newValue.isEmpty() || initialValue.isEmpty()) {
				answeredNok++;
				continue;
			}

			String v1 = initialValue.toString();
			String v2 = newValue.toString();

			if (v1.length() > 50 || v2.length() > 50) {
				answeredNok++;
				continue;
			}

			if (v1.trim().equalsIgnoreCase(v2.trim())) {
				answeredOk++;
			} else {
				answeredNok++;
			}
		}

		StringBuilder results = new StringBuilder("Out of ").append(pairs).append(" items you got: ");
		results.append(answeredOk).append(" right and ").append(answeredNok).append(" wrong.");

		return results.toString();
	}

	protected abstract List<T> getTests();
	protected abstract String appendJavascriptOnTestClick();
}