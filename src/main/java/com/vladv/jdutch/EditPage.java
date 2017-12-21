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
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.wicketstuff.annotation.mount.MountPath;

import com.giffing.wicket.spring.boot.context.scan.WicketHomePage;
import com.vladv.jdutch.domain.TestPojo;

@WicketHomePage
@MountPath("/edit")
public class EditPage extends BasePage {
	private static final long serialVersionUID = 1L;

	public EditPage(final PageParameters parameters) {
		super(parameters);

		final CompoundPropertyModel<TestPojo> model = new CompoundPropertyModel<TestPojo>(new TestPojo());
		Form<TestPojo> form = new Form<TestPojo>("form", model) {

			@Override
			protected void onSubmit() {
				super.onSubmit();

				JDutchApplication.getApp().getRepository().save(this.getModelObject());

				this.setModelObject(new TestPojo());
			}
		};

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

		LoadableDetachableModel<List<TestPojo>> ldm = new LoadableDetachableModel<List<TestPojo>>() {

			@Override
			protected List<TestPojo> load() {
				List<TestPojo> findAll = JDutchApplication.getApp().getRepository().findAll();
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