package com.vladv.jdutch.admin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Serializable;
import java.io.StringReader;
import java.util.List;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.request.IRequestParameters;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.util.string.StringValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.wicketstuff.annotation.mount.MountPath;

import com.vladv.jdutch.domain.Test;

@MountPath("/admin")
public class AdminPage extends WebPage {

	private static final Logger LOG = LoggerFactory.getLogger(AdminPage.class);

	@Override
	protected void onInitialize() {
		super.onInitialize();

		final CompoundPropertyModel<Bean> model = new CompoundPropertyModel<Bean>(new Bean());
		Form<Bean> form = new Form<Bean>("form", model) {

			@Override
			protected void onSubmit() {

				Request request = RequestCycle.get().getRequest();
				IRequestParameters requestParameters = request.getRequestParameters();
				StringValue type = requestParameters.getParameterValue("optionselected");
				if (type.isEmpty()) {
					throw new IllegalArgumentException(" No domain type selected ! use the drop down from the menu! ");
				}
				Integer st = type.toInteger();
				MongoRepository<? extends Test, Long> repo = IMPORT_EXPORT.get(st).getRepo();

				boolean isExport = st % 2 == 0; // otherwise is import

				if (isExport) {

					List<? extends Test> all = repo.findAll();
					StringBuilder sb = new StringBuilder();

					for (Test t : all) {
						String testname = t.getTestname();
						if (testname.startsWith("Import")) {
							testname = testname.substring(testname.indexOf(' '));
						}
						sb.append("\r\r").append(testname).append(t.getTestcontents());
					}

					Bean bexport = new Bean();
					bexport.test = sb.toString();
					this.setModelObject(bexport);

				} else {

					Bean bean = this.getModelObject();

					BufferedReader bufReader = new BufferedReader(new StringReader(bean.getTest()));

					Test newTest = null;
					String line = null;
					StringBuilder newTestContents = new StringBuilder();

					try {
						while ((line = bufReader.readLine()) != null) {
							if (line.startsWith("#")) {

								if (newTest != null) {
									newTest.setTestcontents(newTestContents.toString());
									LOG.info(newTest.getTestname() + "\n" + newTest.getTestcontents());
									IMPORT_EXPORT.get(st).save(newTest);
								}

								newTest = new Test();
								newTest.setTestname("Import: " + line.trim());
								newTestContents = new StringBuilder();
							} else {
								newTestContents.append("</p>").append(line).append("</p>");
							}
						}
						newTest.setTestcontents(newTestContents.toString());

						if (newTest.getTestcontents().length() > 0) {
							LOG.info(newTest.getTestname() + "\n" + newTest.getTestcontents());
							IMPORT_EXPORT.get(st).save(newTest);
						}
					} catch (IOException e) {
						LOG.error(e.getMessage(), e);
					}

					this.setModelObject(new Bean());
				}
			}
		};
		form.add(new TextArea<String>("test"));

		add(form);
	}

	private static final class Bean implements Serializable {
		private String test;

		public String getTest() {
			return test;
		}
	}
}