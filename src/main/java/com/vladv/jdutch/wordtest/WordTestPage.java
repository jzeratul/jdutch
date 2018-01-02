package com.vladv.jdutch.wordtest;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.DropDownChoice;
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
import com.vladv.jdutch.pages.templates.BasePage;

@MountPath("/words")
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

		WebMarkupContainer container = new WebMarkupContainer("container");
		container.setOutputMarkupId(true);
		add(container);
		container.add(tests);
	}

	private static final class TestPanel extends Panel {

		public TestPanel(final String id) {
			super(id);

			this.setOutputMarkupId(true);
		}

		public void refresh(AjaxRequestTarget target, String contents) {

			target.add(this.get("form:contents").setDefaultModelObject(contents));
			target.add(this.get("form:testtype").setVisible(true));
			target.add(this.get("form:submittest").setVisible(true));
			target.add(this.get("feedback").setVisible(false));
			target.add(this);
		}

		@Override
		protected void onInitialize() {
			super.onInitialize();

			Form<String> form = new Form<String>("form", Model.of(""));

			Model<String> testTypeModel = Model.of("NL 2 RO");
			DropDownChoice<String> testtype = new DropDownChoice<>("testtype", testTypeModel, Model.ofList(Arrays.asList("NL 2 RO", "RO 2 NL")));
			form.add(testtype);
			testtype.add(new AjaxFormComponentUpdatingBehavior("change") {

				@Override
				protected void onUpdate(AjaxRequestTarget target) {
					target.add(TestPanel.this);
					target.appendJavaScript("prepareWordTestPage();");
				}
			});

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
						if(e.getMessage() == null) {
							error(e);
						} else {
							error(e.getMessage());
						}
						LOGGER.error(e.getMessage(), e);
					}
					feedback.setVisible(true);
					target.add(feedback);
					testtype.setVisible(false);
					this.setVisible(false);
					target.add(this);
					target.add(testtype);
					target.add(TestPanel.this);
				}

				private String takeTest(String obj, IRequestParameters requestParameters) throws Exception {
					Set<String> parameterNames = requestParameters.getParameterNames();

					// there are two additional items that should not be considered - the submit button and the testtype
					int numberOfItems = (parameterNames.size() - 2) / 2;

					StringBuilder results = new StringBuilder("Out of ").append(numberOfItems).append(" items you got: ");

					int ok = 0;
					int nok = 0;
					for (int p = 0; p < numberOfItems; p++) {
						StringValue typed = requestParameters.getParameterValue("element" + p);
						StringValue original = requestParameters.getParameterValue("element_original" + p);

						if(typed.isEmpty() || original.isEmpty()) {
							nok++;
							LOGGER.error("Null value in element" + p);
						} else {
							
							if (typed.toString().trim().equalsIgnoreCase(original.toString().trim())) {
								ok++;
							} else {
								nok++;
							}
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
			testtype.setOutputMarkupId(true);
			
			form.add(submitbutton);

			add(form);
		}
	}
}