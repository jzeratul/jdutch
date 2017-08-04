package com.vladv.jdutch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormSubmitBehavior;
import org.apache.wicket.ajax.form.OnChangeAjaxBehavior;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.wicketstuff.annotation.mount.MountPath;

import com.giffing.wicket.spring.boot.context.scan.WicketHomePage;

@WicketHomePage
@MountPath("/")
public class HomePage extends BasePage {
	private static final long serialVersionUID = 1L;

	public HomePage(final PageParameters parameters) {
		super(parameters);

		WebMarkupContainer container = new WebMarkupContainer("container");
		container.setOutputMarkupId(true);
		
		Form<Bean> form = new Form<Bean>("form");
		
		add(form);
		form.add(container);

		LoadableDetachableModel<List<Entry<Object, Object>>> ldm = new LoadableDetachableModel<List<Entry<Object, Object>>>() {
			private static final long serialVersionUID = 1L;

			@Override
			protected List<Entry<Object, Object>> load() {
				Translations tr = new Translations();
				Properties pr = tr.load(lesson);
				Set<Entry<Object, Object>> entrySet = pr.entrySet();
				cnt = 1;
				System.out.println("Loading file " + lesson + " having " + entrySet.size() + " entries.");
				return new ArrayList<>(entrySet);
			}
		};

		ListView<Entry<Object, Object>> repeater = new ListView<Entry<Object, Object>>("repeater", ldm) {
			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(ListItem<Entry<Object, Object>> item) {
				Entry<Object, Object> obj = (Entry<Object, Object>) item.getDefaultModelObject();

				String key = (String) obj.getKey();
				String val = (String) obj.getValue();

				item.add(new Label("counter", getLoad("" + (cnt++))));
				item.add(new Label("dutch", getLoad(obj.getKey())));
				TextField<String> textField = new TextField<String>("text", getLoad(obj.getValue()));
				item.add(textField);
				item.add(new Button("submit"));

				textField.add(new OnChangeAjaxBehavior() {
					private static final long serialVersionUID = 1L;

					@Override
					protected void onUpdate(AjaxRequestTarget target) {
						System.out.println(">>>>>>");
						System.out.println(key + " " + val + " " + textField.getDefaultModelObjectAsString());
					}
				});
				textField.add(new AjaxFormSubmitBehavior("keypress") {
					private static final long serialVersionUID = 1L;

					@Override
					protected void onSubmit(AjaxRequestTarget target) {
						super.onSubmit(target);
						System.out.println("<<<<");
						System.out.println(Arrays.asList(this.getAttributes().getEventNames()));
						System.out.println(this.getAttributes().getExtraParameters());
						System.out.println(key + " " + val + " " + textField.getDefaultModelObjectAsString());
					}

				});
			}
		};
		
		container.add(repeater);
	}

	@Override
	protected void lessonChanged(AjaxRequestTarget target) {
		super.lessonChanged(target);
		
		target.add(get("form").get("container"));
	}
}