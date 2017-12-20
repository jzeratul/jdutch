package com.vladv.jdutch;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
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
	}
}