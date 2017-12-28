package com.vladv.jdutch.components;

import org.apache.wicket.feedback.IFeedbackMessageFilter;
import org.apache.wicket.markup.html.panel.FeedbackPanel;

public class JFeedbackPanel extends FeedbackPanel {

	public JFeedbackPanel(String id, IFeedbackMessageFilter filter) {
		super(id, filter);
	}

	public JFeedbackPanel(String id) {
		super(id);
	}

}
