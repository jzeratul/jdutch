package com.vladv.jdutch.pages;

import java.util.List;
import java.util.Set;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.IRequestParameters;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.util.string.StringValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wicketstuff.annotation.mount.MountPath;

import com.vladv.jdutch.JDutchApplication;
import com.vladv.jdutch.components.JFeedbackPanel;
import com.vladv.jdutch.domain.WordTest;
import com.vladv.jdutch.pages.templates.BasePage;

@MountPath("/word")
public class WordTestPage extends BasePage {
	private static final Logger LOGGER = LoggerFactory.getLogger(WordTestPage.class);

	@Override
	protected void onInitialize() {
		super.onInitialize();

		TestPanel contents = new TestPanel("switchComponent");
		add(contents);

		LoadableDetachableModel<List<WordTest>> ldm = new LoadableDetachableModel<List<WordTest>>() {

			@Override
			protected List<WordTest> load() {
				List<WordTest> findAll = JDutchApplication.getApp().getWordTestRepository().findAll();
				return findAll;
			}
		};

		ListView<WordTest> tests = new ListView<WordTest>("tests", ldm) {

			private Component lastTest;

			@Override
			protected void populateItem(ListItem<WordTest> item) {

				item.add(new Label("name", PropertyModel.of(item.getModelObject(), "testname")));
				item.add(new AjaxEventBehavior("click") {

					@Override
					protected void onEvent(AjaxRequestTarget target) {

						contents.refresh(target, item.getModelObject().getTestcontents());
						target.appendJavaScript("prepareWordTestPage();");

						if (lastTest != null) {
							lastTest.add(AttributeModifier.replace("class", Model.of("list-group-item list-group-item-action")));
							target.add(lastTest);
						}

						item.add(AttributeModifier.replace("class", Model.of("list-group-item list-group-item-action active")));
						target.add(item);

						lastTest = item;
					}
				});
			}
		};
		add(tests);
	}

	private static final class TestPanel extends Panel {

		public TestPanel(final String id) {
			super(id);

			this.setOutputMarkupId(true);
		}

		public void refresh(AjaxRequestTarget target, String contents) {

			target.add(this.get("form:contents").setDefaultModelObject(contents));
		}

		@Override
		protected void onInitialize() {
			super.onInitialize();

			Form<String> form = new Form<String>("form", Model.of(""));

			final Label contents = new Label("contents", Model.of("Select Test"));
			form.add(contents);
			contents.setEscapeModelStrings(false);
			contents.setOutputMarkupId(true);

			final JFeedbackPanel feedback = new JFeedbackPanel("feedback");
			feedback.setOutputMarkupId(true);
			feedback.setVisible(false);
			add(feedback);

			AjaxButton submitbutton = new AjaxButton("submittest") {

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
					target.add(TestPanel.this);
				}

				private String takeTest(String obj, IRequestParameters requestParameters) throws Exception {
					Set<String> parameterNames = requestParameters.getParameterNames();

					// there is one additional item that should not be considered - the submit button
					int numberOfItems = (parameterNames.size() - 1) / 2;

					StringBuilder results = new StringBuilder("Out of ").append(numberOfItems).append(" items you got: ");

					int ok = 0;
					int nok = 0;
					for (int p = 0; p < numberOfItems; p++) {
						StringValue selected = requestParameters.getParameterValue("options" + p);
						StringValue original = requestParameters.getParameterValue("value" + p);

						if (selected.equals(original)) {
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
			};
			submitbutton.setOutputMarkupId(true);

			form.add(submitbutton);

			add(form);
		}
	}
}