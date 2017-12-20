package com.vladv.jdutch;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.OnChangeAjaxBehavior;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public class BasePage extends WebPage {
	private static final long serialVersionUID = 1L;

	protected static final String TYPE_EN_DT = "English to Dutch";
	protected static final String TYPE_DT_EN = "Dutch to English";

	protected String testtype = TYPE_EN_DT;
	protected int lesson = 0;
	protected int listItemPosition = 1;

	protected boolean showtranslations = false;

	public BasePage(final PageParameters parameters) {
		super(parameters);

		Label header = new Label("lesson");
		add(header);
		header.setOutputMarkupId(true);

		Form<String> formlang = new Form<String>("formlang") {
			@Override
			public boolean isVisible() {
				return showToolbar();
			}
		};
		add(formlang);

		formlang.add(new AjaxButton("showall") {
			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target) {
				showtranslations = !showtranslations;
				showTranslations(target);
				if (showtranslations) {
					this.setModel(getLoad("Hide Translations"));
				} else {
					this.setModel(getLoad("Translate"));
				}
				target.add(this);
			}
		});

		DropDownChoice<String> testChoice = new DropDownChoice<String>("testtype", Model.of(TYPE_EN_DT), Arrays.asList(TYPE_EN_DT, TYPE_DT_EN));
		formlang.add(testChoice);
		testChoice.setNullValid(false);

		testChoice.add(new OnChangeAjaxBehavior() {
			private static final long serialVersionUID = 1L;

			@Override
			protected void onUpdate(AjaxRequestTarget target) {
				testtype = testChoice.getDefaultModelObjectAsString();
				testTypeChanged(target);
			}
		});

		Form<Bean> lessonsForm = new Form<Bean>("lessonsForm");
		add(lessonsForm);
		LoadableDetachableModel<List<String>> buttons = new LoadableDetachableModel<List<String>>() {
			private static final long serialVersionUID = 1L;

			@Override
			protected List<String> load() {

				List<String> res = IntStream.range(0, 16).mapToObj(i -> "Les" + i).collect(Collectors.toList());

				return res;
			}
		};

		lessonsForm.add(new ListView<String>("repeater", buttons) {
			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(ListItem<String> item) {
				AjaxButton ajaxSubmitLink = new AjaxButton("choselesson", getLoad("Les" + (item.getIndex() + 1))) {
					private static final long serialVersionUID = 1L;

					@Override
					protected void onSubmit(AjaxRequestTarget target) {
						lesson = item.getIndex() + 1;
						header.setDefaultModel(getLoad("Les" + lesson));
						lessonChanged(target);
						target.add(header);
					}
				};
				item.add(ajaxSubmitLink);
			}
		});
	}

	protected boolean showToolbar() {
		return false;
	}

	protected void showTranslations(AjaxRequestTarget target) {

	}

	protected void testTypeChanged(AjaxRequestTarget target) {

	}

	protected void lessonChanged(AjaxRequestTarget target) {

	}

	protected LoadableDetachableModel<String> getLoad(Object val) {
		return new LoadableDetachableModel<String>() {
			private static final long serialVersionUID = 1L;

			@Override
			protected String load() {
				if (val == null) {
					return "";
				}
				return val.toString();
			}
		};
	}

	protected static final class Bean implements Serializable {
		private static final long serialVersionUID = 1L;
		private String text;

		public String getText() {
			return text;
		}

		public void setText(String text) {
			this.text = text;
		}
	}

	protected boolean isSwitched() {
		return testtype.equals(TYPE_EN_DT);
	}

	protected String getFrom() {
		return testtype.equals(TYPE_EN_DT) ? "English" : "Dutch";
	}

	protected String getTo() {
		return testtype.equals(TYPE_EN_DT) ? "Dutch" : "English";
	}
}