package com.vladv.jdutch;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public class BasePage extends WebPage {

	private static final long serialVersionUID = 1L;

	protected int lesson = 1;

	protected int cnt = 1;

	public BasePage(final PageParameters parameters) {
		super(parameters);

		Label header = new Label("lesson");
		add(header);
		header.setOutputMarkupId(true);

		Form<Bean> lessonsForm = new Form<Bean>("lessonsForm");
		add(lessonsForm);
		LoadableDetachableModel<List<String>> buttons = new LoadableDetachableModel<List<String>>() {
			private static final long serialVersionUID = 1L;

			@Override
			protected List<String> load() {

				List<String> res = IntStream.range(0, 45)
						.mapToObj(i -> "Les" + i)
						.collect(Collectors.toList());
				
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
				// TODO add button text here
				item.add(ajaxSubmitLink);
			}
		});
	}

	protected void lessonChanged(AjaxRequestTarget target) {

	}

	protected LoadableDetachableModel<String> getLoad(Object val) {
		return new LoadableDetachableModel<String>() {
			private static final long serialVersionUID = 1L;

			@Override
			protected String load() {
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
}