package se.eloff.fudge.client;

import com.google.gwt.user.client.ui.Label;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.PasswordItem;
import com.smartgwt.client.widgets.form.fields.SubmitItem;
import com.smartgwt.client.widgets.form.fields.TextItem;

public class LoginScreen extends DynamicForm {

	// Error Label
	private Label lblError = new Label();

	public LoginScreen() {
		
		TextItem usernameItem = new TextItem();
		usernameItem.setTitle("Username");
		usernameItem.setRequired(true);
		
		PasswordItem passwordItem = new PasswordItem();
		passwordItem.setTitle("Password");
		passwordItem.setRequired(true);
		
		SubmitItem submitItem = new SubmitItem();
		
		this.setFields(usernameItem, passwordItem, submitItem);

		
		//this.draw();
	}

	/**
	 * This method is called when the button is clicked
	 */
	private void checkLogin(String userName, String password) {
		/** This is just a stub. More code to be added later */
		System.out.println("Checking login for " + userName);
	}

	private void setErrorText(String errorMessage) {
		lblError.setText(errorMessage);
	}
}