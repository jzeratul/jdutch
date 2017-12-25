package com.vladv.jdutch;

import java.util.List;
import java.util.Set;

import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.IRequestParameters;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.cycle.RequestCycle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wicketstuff.annotation.mount.MountPath;

import com.giffing.wicket.spring.boot.context.scan.WicketHomePage;
import com.vladv.jdutch.domain.TestPojo;

@WicketHomePage
@MountPath("/")
public class HomePage extends BasePage {
	private static final Logger LOGGER = LoggerFactory.getLogger(HomePage.class);

	@Override
	protected void onInitialize() {
		super.onInitialize();
		
		TestPanel contents = new TestPanel("switchComponent");
		add(contents);
		
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

						contents.refresh(target, item.getModelObject().getTestcontents());
						target.appendJavaScript("replaceAllBoldElementsWithInput();");
					}
				}); 
			}
		};
		add(tests);
	}
	
	private static final class TestPanel extends Panel {
		
		public TestPanel(final String id) {
			super(id);
			
			this.setOutputMarkupId(true);
		}

		public void refresh(AjaxRequestTarget target, String contents) {
			
			target.add(this.get("form:contents").setDefaultModelObject(contents));
		}
		
		@Override
		protected void onInitialize() {
			super.onInitialize();
			
			Label alert = new Label("alert", Model.of(""));
			alert.setVisible(false);
			alert.setOutputMarkupId(true);
			
			Form<String> form = new Form<String>("form", Model.of(""));
			
			form.add(alert);
			final Label contents = new Label("contents", Model.of("Select Test"));
			form.add(contents);
			contents.setEscapeModelStrings(false);
			contents.setOutputMarkupId(true);

			AjaxButton submitbutton = new AjaxButton("submittest") {

				@Override
				protected void onSubmit(AjaxRequestTarget target) {
					super.onSubmit(target);

					Request request = RequestCycle.get().getRequest();
					IRequestParameters requestParameters = request.getRequestParameters();

					try {
						String results = takeTest(contents.getDefaultModelObjectAsString(), requestParameters);
						alert.setDefaultModelObject(results);
						alert.setVisible(true);
						target.add(alert);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

				private String takeTest(String obj, IRequestParameters requestParameters) throws Exception {
					Set<String> parameterNames = requestParameters.getParameterNames();
					int nrParams = parameterNames.size() - 2; // two params sent from ui are out of scope
					
					int items = nrParams / 2;

					StringBuilder results = new StringBuilder("Out of " + items + " items you got: ");
					
					int ok = 0;
					int nok = 0;
					for (int p = 0; p < items; p++) {
						
						String paramName = "element" + p;
						String paramNameOriginal = "element" + p + "_original";
						String paramNewValue = requestParameters.getParameterValue(paramName).toString();
						String paramOriginalValue = requestParameters.getParameterValue(paramNameOriginal).toString();
						
						if(paramOriginalValue.equalsIgnoreCase(paramNewValue)) {
							ok++;
						} else {
							nok++;
						}
					}
					results.append(ok + " right and ").append(nok + " wrong.");
					
					if(nok==0) {
						results.append(" You are awesomeeee!!");
					}
					
					return results.toString();
				}
			};
			form.add(submitbutton);

			add(form);
		}
	}
}