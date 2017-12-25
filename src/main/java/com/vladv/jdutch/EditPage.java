package com.vladv.jdutch;

import java.util.List;

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
import org.apache.wicket.model.PropertyModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wicketstuff.annotation.mount.MountPath;

import com.vladv.jdutch.domain.TestPojo;

@MountPath("/edit")
public class EditPage extends BasePage {
	private static final Logger LOGGER = LoggerFactory.getLogger(EditPage.class);

	@Override
	protected void onInitialize() {
		super.onInitialize();

		final CompoundPropertyModel<TestPojo> model = new CompoundPropertyModel<TestPojo>(new TestPojo());
		Form<TestPojo> form = new Form<TestPojo>("form", model);

		form.add(new TextField<String>("testname"));
		form.add(new TextArea<String>("testcontents"));

		add(form);

		form.add(new AjaxButton("delete") {

			@Override
			protected void onSubmit(AjaxRequestTarget target) {
				super.onSubmit(target);

				JDutchApplication.getApp().getRepository().deleteTestPojoByTestname(model.getObject().getTestname());

				target.add(EditPage.this);
			}
		});
		
		form.add(new AjaxButton("save") {
			
			@Override
			protected void onSubmit(AjaxRequestTarget target) {

				JDutchApplication.getApp().getRepository().save(form.getModelObject());
				form.setModelObject(new TestPojo());
				
				target.add(EditPage.this);
			}
		});

		LoadableDetachableModel<List<TestPojo>> ldm = new LoadableDetachableModel<List<TestPojo>>() {

			@Override
			protected List<TestPojo> load() {
				List<TestPojo> findAll = JDutchApplication.getApp().getRepository().findAll();
				
				LOGGER.info("Retrieving Tests: " + findAll.size());
				
				return findAll;
			}
		};
		ListView<TestPojo> tests = new ListView<TestPojo>("tests", ldm) {

			@Override
			protected void populateItem(ListItem<TestPojo> item) {

				item.add(new Label("name", PropertyModel.of(item.getModelObject(), "testname")));
				item.add(new AjaxEventBehavior("click") {

					@Override
					protected void onEvent(AjaxRequestTarget target) {

						model.setObject(item.getModelObject());
						target.add(EditPage.this);
					}
				});
			}
		};

		add(tests);
	}
}