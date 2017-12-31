package com.vladv.jdutch.pages;

import java.util.List;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
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
import com.vladv.jdutch.domain.ArticleTest;
import com.vladv.jdutch.pages.templates.BasePage;

@MountPath("/editarticle")
public class EditArticleTestPage extends BasePage {
	// private static final Logger LOGGER =
	// LoggerFactory.getLogger(EditWordTestPage.class);

	@Override
	protected void onInitialize() {
		super.onInitialize();

		final CompoundPropertyModel<ArticleTest> model = new CompoundPropertyModel<ArticleTest>(new ArticleTest());
		Form<ArticleTest> form = new Form<ArticleTest>("form", model);

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
					JDutchApplication.getApp().getWordTestRepository().deleteArticleTestByTestname(model.getObject().getTestname());
					setResponsePage(EditArticleTestPage.class);
				}
			}
		});

		form.add(new AjaxButton("save") {

			@Override
			protected void onSubmit(AjaxRequestTarget target) {

				ArticleTest wordtest = form.getModelObject();
				// this is to avoid "De vs de and Het vs het" checks
				if (wordtest.getTestcontents() != null) {
					wordtest.setTestcontents(wordtest.getTestcontents().toLowerCase());
				}
				JDutchApplication.getApp().getWordTestRepository().save(wordtest);
				form.setModelObject(new ArticleTest());

				target.add(EditArticleTestPage.this);
			}
		});

		LoadableDetachableModel<List<ArticleTest>> ldm = new LoadableDetachableModel<List<ArticleTest>>() {

			@Override
			protected List<ArticleTest> load() {
				return JDutchApplication.getApp().getWordTestRepository().findAll();
			}
		};
		ListView<ArticleTest> tests = new ListView<ArticleTest>("tests", ldm) {

			private Component lastTest;

			@Override
			protected void populateItem(ListItem<ArticleTest> item) {

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

		add(tests);
	}
}