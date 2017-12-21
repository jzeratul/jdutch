package com.vladv.jdutch;

import java.util.List;
import java.util.Set;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
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

		Form<String> form = new Form<String>("form", Model.of("")) {

		};
		AjaxButton submitbutton = new AjaxButton("submittest") {

			@Override
			protected void onSubmit(AjaxRequestTarget target) {
				super.onSubmit(target);



				Request request = RequestCycle.get().getRequest();
				System.out.println(request);
				IRequestParameters requestParameters = request.getRequestParameters();
				Set<String> parameterNames = requestParameters.getParameterNames();
				for (String s : parameterNames) {
					System.out.println(s + ": " + requestParameters.getParameterValue(s));
				}

				System.out.println("> " + form.getModelObject());
			}
		};
		form.add(submitbutton);
		submitbutton.setVisible(false);

		final Label contents = new Label("contents", Model.of("Select Test"));
		form.add(contents);
		contents.setEscapeModelStrings(false);
		contents.setOutputMarkupId(true);
		add(form);

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

						contents.setDefaultModelObject(item.getModelObject().getTestcontents());
						Component submitbutton = HomePage.this.get("form").get("submittest");
						submitbutton.setVisible(true);
						target.add(HomePage.this);
						target.add(submitbutton);
					}
				});
			}
		};

		add(tests);
	}
}