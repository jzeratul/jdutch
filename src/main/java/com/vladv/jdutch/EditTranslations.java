package com.vladv.jdutch;

import java.util.ArrayList;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.wicketstuff.annotation.mount.MountPath;

@MountPath("/edit")
public class EditTranslations extends BasePage {

	public EditTranslations(final PageParameters parameters) {
		super(parameters);

		WebMarkupContainer thing = new WebMarkupContainer("thing");
		this.add(thing);

		LoadableDetachableModel<String> ldm = new LoadableDetachableModel<String>() {

			@Override
			protected String load() {
				Translations tr = new Translations();
				Properties pr = tr.load(lesson);
				Set<Entry<Object, Object>> entrySet = pr.entrySet();
				System.out.println("loading properties " + entrySet.size());

				Function<Entry<Object, Object>, String> addQuotes = (s) -> new StringBuilder(10).append("<div>").append(s).append("</div>").toString();

				ArrayList<Entry<Object, Object>> arrayList = new ArrayList<Entry<Object, Object>>(entrySet);
				arrayList.sort((p1, p2) -> {
					String s1 = (String) p1.getKey();
					String s2 = (String) p2.getKey();
					return s1.compareToIgnoreCase(s2);
				});
				String s = arrayList.stream().map(addQuotes).collect(Collectors.joining(""));

				return s;
			}
		};

		Label contents = new Label("contents", ldm);
		contents.setEscapeModelStrings(false);
		thing.add(contents);
	}
}