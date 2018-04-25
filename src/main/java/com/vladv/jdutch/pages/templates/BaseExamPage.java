package com.vladv.jdutch.pages.templates;

import java.util.List;

import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.IRequestParameters;
import org.apache.wicket.util.string.StringValue;
import org.wicketstuff.lambda.components.ComponentFactory;

import com.vladv.jdutch.domain.Test;

public abstract class BaseExamPage<T extends Test> extends WebPage {

  String selection = "Select test type";
  
  public BaseExamPage() {

    this.setVersioned(false);

    addTestsListHeader();
    addTestsList();

    add(ComponentFactory.ajaxLink("edittest", (link, target) -> {
      setResponsePage(getEditPageClass());
    }));
  }

  private void addTestsListHeader() {
    Label selected = new Label("selected", PropertyModel.of(this, "selection"));
    selected.setOutputMarkupId(true);
    add(selected);
    
    ListView<String> option = new ListView<String>("option", () -> {
      
      List<String> testCategories = getTestCategories();
      testCategories.add(0, "ALL      ");
      return testCategories;
    }) {
      
      @Override
      protected void populateItem(ListItem<String> item) {
        
        AjaxLink<Object> ajaxLink = ComponentFactory.ajaxLink("link", (link, target) -> {
          selection = item.getModelObject();
          target.add(getPage().get("testslist"));
          target.add(selected);
        });
        ajaxLink.setOutputMarkupId(true);
        item.add(ajaxLink);
        ajaxLink.add(new Label("name", item.getModelObject()));
      }
    };
    
    WebMarkupContainer categoriescontainer = new WebMarkupContainer("categoriescontainer");
    categoriescontainer.setOutputMarkupId(true);
    categoriescontainer.add(option);
    add(categoriescontainer);
    
  }

  private void addTestsList() {
    
    ListView<T> tests = new ListView<T>("tests", new LoadableDetachableModel<List<T>>() {

      @Override
      protected List<T> load() {
        return getTests(selection);
      }}) {

      @Override
      protected void populateItem(ListItem<T> item) {

        item.add(new Label("name", PropertyModel.of(item.getModelObject(), "testname")));
        item.add(AjaxEventBehavior.onEvent("click", target -> {

          onTestClick(item, target);

          highlightSelection(item.getMarkupId(), target);

          target.add(item);
        }));
      }
    };

    WebMarkupContainer testslist = new WebMarkupContainer("testslist");
    testslist.setOutputMarkupId(true);
    testslist.add(tests);
    add(testslist);
  }

  private void highlightSelection(String itemMarkupId, AjaxRequestTarget target) {
    String js = String.format("makeActiveThisElement('#%s', '#%s a');", itemMarkupId, get("testslist").getMarkupId());
    target.appendJavaScript(js);
    target.appendJavaScript("showHideSubmitButton();");
  }

  public void refresh(AjaxRequestTarget target, String contents) {

    target.add(this.get("form:contents").setDefaultModelObject(contents));
  }

  protected String takeTest(String obj, IRequestParameters requestParameters) throws Exception {

    long pairs = requestParameters.getParameterNames().stream().filter(p -> p.contains("newvalue")).count();

    int answeredOk = 0;
    int answeredNok = 0;
    for (int i = 0; i < pairs; i++) {

      StringValue newValue = requestParameters.getParameterValue("newvalue" + i);
      StringValue initialValue = requestParameters.getParameterValue("initialvalue" + i);

      if (newValue.isEmpty() || initialValue.isEmpty()) {
        answeredNok++;
        continue;
      }

      String v1 = initialValue.toString();
      String v2 = newValue.toString();

      if (v1.length() > 50 || v2.length() > 50) {
        answeredNok++;
        continue;
      }

      if (v1.trim().equalsIgnoreCase(v2.trim())) {
        answeredOk++;
      } else {
        answeredNok++;
      }
    }

    StringBuilder results = new StringBuilder("Out of ").append(pairs).append(" items you got: ");
    results.append(answeredOk).append(" right and ").append(answeredNok).append(" wrong.");

    return results.toString();
  }

  protected String appendJavascriptOnTestClick() {
    return "";
  }

  protected void contributeToForm(Form<String> form) {

  }

  protected abstract Class<? extends EditExamPage<?>> getEditPageClass();

  protected abstract List<String> getTestCategories();
  
  protected abstract List<T> getTests(String ofCategory);

  protected abstract void onTestClick(ListItem<T> item, AjaxRequestTarget target);
}
