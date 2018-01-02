package com.vladv.jdutch.pages.templates;

import java.util.List;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
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

import com.vladv.jdutch.domain.Test;

public abstract class EditTestPage<T extends Test> extends BasePage {

	private Component lastTest; // TODO must improve on handling this item
	
	@Override
	protected void onInitialize() {
		super.onInitialize();

		final CompoundPropertyModel<T> model = new CompoundPropertyModel<T>(getNewObject());
		Form<T> form = new Form<T>("form", model);

		form.add(new TextField<String>("testname"));
		form.add(new TextArea<String>("testcontents"));
		form.setOutputMarkupId(true);
		add(form);

		Label helpMessage = new Label("testhelpmessage", Model.of(getTestHelpMessage()));
		form.add(helpMessage);
		helpMessage.setEscapeModelStrings(false);
		
		form.add(new AjaxButton("delete", Model.of("Delete")) {

			@Override
			protected void onSubmit(AjaxRequestTarget target) {
				super.onSubmit(target);

				if(this.getModelObject().equals("Delete")) {
					this.setModelObject("Are you sure you want to delete this test?");
					target.add(this);
				} else {
					deleteTest(model.getObject().getTestname());
					reloadTests(target);
					form.setModelObject(getNewObject());
					this.setModelObject("Delete");
					target.add(getPage());
					lastTest = null; // TODO must improve on handling this item
				}
			}
		});

		form.add(new AjaxButton("save") {

			@Override
			protected void onSubmit(AjaxRequestTarget target) {

				T obj = form.getModelObject();
				
				if (obj.getTestcontents() == null || obj.getTestname() == null) {
					return;
				}
				
				obj.setTestcontents(obj.getTestcontents().toLowerCase());
				saveTest(obj);
				form.setModelObject(getNewObject());
				target.add(form);
				reloadTests(target);
				lastTest = null; // TODO must improve on handling this item
			}
		});
		
		LoadableDetachableModel<List<T>> ldm = new LoadableDetachableModel<List<T>>() {

			@Override
			protected List<T> load() {
				return getTests();
			}
		};
		ListView<T> tests = new ListView<T>("tests", ldm) {

			@Override
			protected void populateItem(ListItem<T> item) {

				item.add(new Label("name", PropertyModel.of(item.getModelObject(), "testname")));
				item.add(new AjaxEventBehavior("click") {

					@Override
					protected void onEvent(AjaxRequestTarget target) {

						model.setObject(item.getModelObject());

						if (lastTest != null) {
							lastTest.add(AttributeModifier.replace("class", Model.of("list-group-item list-group-item-action")));
							target.add(lastTest); // TODO must improve on handling this item
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

		WebMarkupContainer testslist = new WebMarkupContainer("testslist");
		testslist.setOutputMarkupId(true);
		add(testslist);
		testslist.add(tests);
		
		testslist.add(new AjaxLink<Void>("edittest") {
			@Override
			public void onClick(AjaxRequestTarget target) {
				setResponsePage(getEditPageClass());
			}
		});
	}

	protected void reloadTests(AjaxRequestTarget target) {
		target.add(get("testslist"));
	}
	
	protected abstract String getTestHelpMessage();
	protected abstract T getNewObject();
	protected abstract void saveTest(T test);
	protected abstract void deleteTest(String testname);
	protected abstract List<T> getTests();
	protected abstract Class<? extends EditTestPage<?>> getEditPageClass();
}