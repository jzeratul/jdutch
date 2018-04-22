package com.vladv.jdutch.pages.templates;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.IRequestParameters;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.cycle.RequestCycle;
import org.wicketstuff.lambda.components.ComponentFactory;

import com.vladv.jdutch.components.JFeedbackPanel;
import com.vladv.jdutch.domain.Test;

public abstract class ExamPage<T extends Test> extends BaseExamPage<T> {
  
  @Override
  protected void onInitialize() {
    super.onInitialize();

    Form<String> form = new Form<String>("form", Model.of(""));

    final Label contents = new Label("contents", "");
    form.add(contents);
    contents.setEscapeModelStrings(false);
    contents.setOutputMarkupId(true);

    final JFeedbackPanel feedback = new JFeedbackPanel("feedback");
    feedback.setOutputMarkupId(true);
    feedback.setVisible(false);
    add(feedback);

    contributeToForm(form);

    form.add(ComponentFactory.ajaxButton("submittest", (button, target) -> {
      Request request = RequestCycle.get().getRequest();
      IRequestParameters requestParameters = request.getRequestParameters();

      try {
        String results = takeTest(contents.getDefaultModelObjectAsString(), requestParameters);
        info(results);
      } catch (Exception e) {
        error(e.getMessage());
      }
      target.appendJavaScript(appendJavascriptOnTestClick());
      feedback.setVisible(true);
      target.add(feedback);
      target.add(getPage());
    }));

    add(form);
  }

  @Override
  protected void onTestClick(ListItem<T> item, AjaxRequestTarget target) {
    refresh(target, item.getModelObject().getTestcontents());
    target.appendJavaScript(appendJavascriptOnTestClick());
  }
}