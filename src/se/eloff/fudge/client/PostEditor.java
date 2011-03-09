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
		this.setShowEdges(true);
		this.setColWidths("*");
		this.setTitleOrientation(TitleOrientation.TOP);
		
		title = new TextItem("Title");
		title.setRequired(true);
		title.setWidth("*");
		
		message = new RichTextItem("Message");
		message.setShowTitle(true);
		message.setRequired(true);
		message.setWidth("*");
		
		submitButton = new ButtonItem("Post");
		
		this.setFields(title, message, submitButton);
	}
}
