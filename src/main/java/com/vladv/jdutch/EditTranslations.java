package com.vladv.jdutch;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.wicketstuff.annotation.mount.MountPath;

@MountPath("/edit")
public class EditTranslations extends BasePage {
	private static final long serialVersionUID = 1L;

	public EditTranslations(final PageParameters parameters) {
		super(parameters);

		WebMarkupContainer thing = new WebMarkupContainer("thing");
		this.add(thing);

		LoadableDetachableModel<String> ldm = new LoadableDetachableModel<String>() {
			private static final long serialVersionUID = 1L;

			@Override
			protected String load() {

				if (lesson == 0) {
					return "pick a lesson";
				}

				Translations tr = new Translations();
				Properties pr = tr.load(lesson);
				Set<Entry<Object, Object>> entrySet = pr.entrySet();
				System.out.println("loading properties " + entrySet.size());

				Function<Entry<Object, Object>, String> addQuotes = (s) -> new StringBuilder(10).append(s).toString();

				ArrayList<Entry<Object, Object>> arrayList = new ArrayList<Entry<Object, Object>>(entrySet);
				arrayList.sort((p1, p2) -> {
					String s1 = (String) p1.getKey();
					String s2 = (String) p2.getKey();
					return s1.compareToIgnoreCase(s2);
				});
				String s = arrayList.stream().map(addQuotes).collect(Collectors.joining("\n"));

				return s;
			}
		};

		TextArea<String> contents = new TextArea<>("contents", ldm);
		contents.setEscapeModelStrings(false);
		contents.setOutputMarkupId(true);
		thing.add(contents);

		Form<String> form = new Form<String>("form");
		form.add(thing);
		add(form);
		form.add(new AjaxButton("save") {
			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target) {
				super.onSubmit(target);

				processAndSave(contents.getDefaultModelObjectAsString());
			}
		});
	}

	protected void processAndSave(String contents) {

		Translations tr = new Translations();
		Properties pr = tr.load(lesson);

		String[] properties = contents.split("\r\n");
		for(String property: properties) {
			String[] keyval = property.split("=");
			pr.setProperty(keyval[0], keyval[1]);
		}

		URL url = ClassLoader.getSystemResource("les" + lesson + ".properties");

		File file = new File(url.getPath());
		try(FileOutputStream fileOut = new FileOutputStream(file, true)) {
			pr.store(fileOut, "sample properties");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Override
	protected void lessonChanged(AjaxRequestTarget target) {
		super.lessonChanged(target);

		target.add(get("form").get("thing").get("contents"));
	}
}