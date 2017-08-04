package com.vladv.jdutch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormSubmitBehavior;
import org.apache.wicket.ajax.form.OnChangeAjaxBehavior;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
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

	private boolean showtranslations = false;

	private Map<String, String> responses = new HashMap<>();
	private Map<Integer, Properties> propertiesCache = new HashMap<>();

	public HomePage(final PageParameters parameters) {
		super(parameters);

		WebMarkupContainer container = new WebMarkupContainer("container");
		container.setOutputMarkupId(true);

		Form<Bean> form = new Form<Bean>("form");

		add(form);
		form.add(container);

		form.add(new AjaxButton("showall") {
			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target) {
				showtranslations = !showtranslations;

				target.add(this.getParent().get("container"));
			}
		});

		LoadableDetachableModel<List<Entry<Object, Object>>> ldm = new LoadableDetachableModel<List<Entry<Object, Object>>>() {
			private static final long serialVersionUID = 1L;

			@Override
			protected List<Entry<Object, Object>> load() {

				if (lesson == 0) {
					return Collections.emptyList();
				}
				
				Properties pr = propertiesCache.get(lesson);
				if(pr == null) {
					Translations translations = new Translations();
					pr = translations.load(lesson);
					propertiesCache.put(lesson, pr);
				}
				
				Set<Entry<Object, Object>> entrySet = pr.entrySet();
				listItemPosition = 1;
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

				item.add(new Label("counter", getLoad("" + (listItemPosition++))));
				item.add(new Label("dutch", getLoad(key)));
				String response = responses.get(lesson + key);
				TextField<String> textField = new TextField<String>("text", getLoad(response));
				WebMarkupContainer fieldcontainer = new WebMarkupContainer("fieldcontainer");
				WebMarkupContainer statusicon = new WebMarkupContainer("statusicon");
				
				if(showtranslations) {

					Properties pr = propertiesCache.get(lesson);
					String real = pr.getProperty(key);
					
					if(real.equals(response)) {
						fieldcontainer.add(AttributeModifier.replace("class", getLoad("form-group has-success has-feedback")));
						statusicon.add(AttributeModifier.replace("class", getLoad("glyphicon glyphicon-ok form-control-feedback")));
					} else {
						fieldcontainer.add(AttributeModifier.replace("class", getLoad("form-group has-error has-feedback")));
						statusicon.add(AttributeModifier.replace("class", getLoad("glyphicon glyphicon-remove form-control-feedback")));
					}
					
				}
				
				fieldcontainer.add(textField);
				item.add(fieldcontainer);
				fieldcontainer.add(statusicon);
				item.add(new Label("real", getLoad(showtranslations ? val : "")));

				textField.add(new OnChangeAjaxBehavior() {
					private static final long serialVersionUID = 1L;

					@Override
					protected void onUpdate(AjaxRequestTarget target) {
						String typed = textField.getDefaultModelObjectAsString();
						responses.put(lesson + key, typed);
					}
				});
				// textField.add(new AjaxFormSubmitBehavior("keypress") {
				// private static final long serialVersionUID = 1L;
				//
				// @Override
				// protected void onSubmit(AjaxRequestTarget target) {
				// super.onSubmit(target);
				// System.out.println("keypress" + key + " " + val + " " +
				// textField.getDefaultModelObjectAsString());
				// }
				//
				// });
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