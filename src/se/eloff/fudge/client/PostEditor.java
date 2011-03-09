package se.eloff.fudge.client;

import com.smartgwt.client.types.TitleOrientation;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.form.fields.RichTextItem;
import com.smartgwt.client.widgets.form.fields.TextItem;

public class PostEditor extends DynamicForm {
	private RichTextItem message;
	private TextItem title;
	private ButtonItem submitButton;

	public PostEditor() {
		this(false);
	}

	public PostEditor(boolean showTitle) {
		this.setShowEdges(true);
		this.setColWidths("*");
		this.setTitleOrientation(TitleOrientation.TOP);

		if (showTitle) {
			title = new TextItem("Title");
			title.setWidth("*");
			title.setRequired(true);
		}

		message = new RichTextItem("Message");
		message.setShowTitle(true);
		message.setRequired(true);
		message.setWidth("*");

		submitButton = new ButtonItem("Post");

		if (showTitle) {
			this.setFields(title, message, submitButton);
		} else {
			this.setFields(message, submitButton);
		}
	}

	public void setTitle(String title) {
		this.title.setValue(title);
	}

	public String getTitle() {
		return this.title.getValueAsString();
	}

	public void setMessage(String message) {
		this.message.setValue(message);
	}

	public String getMessage() {
		return (String) this.message.getValue();
	}
}
