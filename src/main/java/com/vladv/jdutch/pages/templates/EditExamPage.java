package com.vladv.jdutch.pages.templates;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;
import org.wicketstuff.lambda.components.ComponentFactory;

import com.vladv.jdutch.domain.Test;

public abstract class EditExamPage<T extends Test> extends BaseExamPage<T> {
  
	@Override
	protected void onInitialize() {
		super.onInitialize();

		final CompoundPropertyModel<T> model = new CompoundPropertyModel<T>(getNewObject());
		Form<T> form = new Form<T>("form", model);

    form.add(new TextField<String>("testname"));
    form.add(new TextField<String>("category"));
		form.add(new TextArea<String>("testcontents"));
		form.setOutputMarkupId(true);
		add(form);

		Label helpMessage = new Label("testhelpmessage", () -> getTestHelpMessage());
		form.add(helpMessage);
		helpMessage.setEscapeModelStrings(false);

		form.add(ComponentFactory.ajaxButton("delete", (button, target) -> {
		  
		  if(button.getModelObject() == null) {
		    button.setModel(Model.of("Are you sure you want to delete this test?"));
        target.add(this);
      } else {
        deleteTest(model.getObject().getTestname());
        reloadTests(target);
        form.setModelObject(getNewObject());
        button.setModelObject(null);
        target.add(getPage());
        target.appendJavaScript(getOnSaveJS());
      }
		}));
		
		WebMarkupContainer savefeedback = new WebMarkupContainer("savefeedback");
		savefeedback.setOutputMarkupId(true);
		form.add(savefeedback);
		
		form.add(ComponentFactory.ajaxButton("save", (button, target) -> {
      
		  T obj = form.getModelObject();
      saveTest(obj);
      
      form.setModelObject(getNewObject());
      target.add(getPage().get("categoriescontainerbase"));
      target.add(getPage().get("form:categoriescontainer"));
      target.add(getPage().get("form:savefeedback").setVisible(true));
      if(obj.getId() == null) {
         // new items must appear in the list
        target.add(getPage().get("testslist"));
      }
    }));
		
    ListView<String> categories = new ListView<String>("categories", () -> getTestCategories()) {
      
      @Override
      protected void populateItem(ListItem<String> item) {
        
        item.add(new Label("category", () -> item.getModelObject() ));
      }
    };
    
    WebMarkupContainer wmc = new WebMarkupContainer("categoriescontainer");
    wmc.setOutputMarkupId(true);
    wmc.add(categories);
    form.add(wmc);
	}

	protected String getOnSaveJS() {
    return "";
  }


	protected void reloadTests(AjaxRequestTarget target) {
		target.add(get("testslist"));
	}

  @Override
  protected void onTestClick(ListItem<T> item, AjaxRequestTarget target) {
    target.appendJavaScript("prepareSummerNote();");
    target.add(getPage().get("form:savefeedback").setVisible(false));
    target.add(get("form").setDefaultModelObject(item.getModelObject()));
  }

	protected abstract String getTestHelpMessage();
	protected abstract T getNewObject();
	protected abstract void saveTest(T test);
	protected abstract void deleteTest(String testname);
	protected abstract Class<? extends EditExamPage<?>> getEditPageClass();
}