package com.vladv.jdutch.wordtest;

import java.util.List;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.wicketstuff.annotation.mount.MountPath;

import com.vladv.jdutch.JDutchApplication;
import com.vladv.jdutch.pages.templates.BasePage;

@MountPath("/editword")
public class EditWordTestPage extends BasePage {
	// private static final Logger LOGGER =
	// LoggerFactory.getLogger(EditWordTestPage.class);

	@Override
	protected void onInitialize() {
		super.onInitialize();

		final CompoundPropertyModel<WordTest> model = new CompoundPropertyModel<WordTest>(new WordTest());
		Form<WordTest> form = new Form<WordTest>("form", model);

		form.add(new TextField<String>("testname"));
		form.add(new TextArea<String>("testcontents"));
		form.setOutputMarkupId(true);
		add(form);

		form.add(new AjaxButton("delete", Model.of("Delete")) {

			@Override
			protected void onSubmit(AjaxRequestTarget target) {
				super.onSubmit(target);

				if(this.getModelObject().equals("Delete")) {
					this.setModelObject("Are you sure you want to delete this test?");
					target.add(this);
				} else {
					JDutchApplication.getApp().getWordTestRepository().deleteWordTestByTestname(model.getObject().getTestname());
					setResponsePage(EditWordTestPage.class);
				}
			}
		});

		form.add(new AjaxButton("save") {

			@Override
			protected void onSubmit(AjaxRequestTarget target) {

				WordTest wordtest = form.getModelObject();
				// this is to avoid "De vs de and Het vs het" checks
				if (wordtest.getTestcontents() != null) {
					wordtest.setTestcontents(wordtest.getTestcontents().toLowerCase());
				}
				JDutchApplication.getApp().getWordTestRepository().save(wordtest);
				form.setModelObject(new WordTest());

				target.add(form);
				refreshTests(target);
			}
		});

		WebMarkupContainer container = new WebMarkupContainer("container");
		container.setOutputMarkupId(true);
		add(container);

		LoadableDetachableModel<List<WordTest>> ldm = new LoadableDetachableModel<List<WordTest>>() {

			@Override
			protected List<WordTest> load() {
				return JDutchApplication.getApp().getWordTestRepository().findAll();
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

						model.setObject(item.getModelObject());

						if (lastTest != null) {
							lastTest.add(AttributeModifier.replace("class", Model.of("list-group-item list-group-item-action")));
							target.add(lastTest);
						}

						item.add(AttributeModifier.replace("class", Model.of("list-group-item list-group-item-action active")));
						target.add(item);

						lastTest = item;
						target.add(form);

						target.appendJavaScript("prepareSummerNote();");
					}
				});
			}
		};

		container.add(tests);
	}

	protected void refreshTests(AjaxRequestTarget target) {
		target.add(get("container"));
	}
}