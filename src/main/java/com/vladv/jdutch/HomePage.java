package com.vladv.jdutch;

import java.lang.reflect.Method;
import java.util.List;

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
@MountPath("/")
public class HomePage extends BasePage {
	private static final long serialVersionUID = 1L;

	public HomePage(final PageParameters parameters) {
		super(parameters);

		CompoundPropertyModel<TestPojo> model = new CompoundPropertyModel<TestPojo>(new TestPojo());
		Form<TestPojo> form = new Form<TestPojo>("form", model) {

			@Override
			protected void onSubmit() {
				super.onSubmit();

				JDutchApplication.getApp().getStorageService().save(this.getModelObject());
			}
		};

		form.add(new TextField<String>("testname"));
		form.add(new TextArea<String>("testcontents"));

		add(form);

		ListView<TestPojo> tests = new ListView<TestPojo>("tests", new LoadableDetachableModel<List<TestPojo>>() {

			@Override
			protected List<TestPojo> load() {
				List<TestPojo> findAll = JDutchApplication.getApp().getStorageService().findAll();
				return findAll;
			}
		}) {

			@Override
			protected void populateItem(ListItem<TestPojo> item) {
				
				Object o = item.getModelObject();
				
				Method[] declaredMethods = o.getClass().getDeclaredMethods();
				
				for(Method m : declaredMethods) {
					System.out.println(m.getName());
				}
				
				item.add(new Label("name", PropertyModel.of(item.getModelObject(), "testname")));
			}
		};

		add(tests);
	}
}