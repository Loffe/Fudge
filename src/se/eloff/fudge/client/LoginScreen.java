package se.eloff.fudge.client;

import se.eloff.fudge.client.bean.User;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.form.fields.PasswordItem;
import com.smartgwt.client.widgets.form.fields.StaticTextItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.ClickEvent;
import com.smartgwt.client.widgets.form.fields.events.ClickHandler;

public class LoginScreen extends DynamicForm implements ClickHandler {

	private TextItem usernameItem;
	private PasswordItem passwordItem;
	private LoginServiceAsync svc;
	private StaticTextItem errorItem;
	private final EventBus bus;

	public LoginScreen(EventBus bus) {

		this.bus = bus;
		
		usernameItem = new TextItem();
		usernameItem.setTitle("Username");
		usernameItem.setRequired(true);

		passwordItem = new PasswordItem();
		passwordItem.setTitle("Password");
		passwordItem.setRequired(true);

		ButtonItem buttonItem = new ButtonItem("Login");
		buttonItem.addClickHandler(this);

		
		errorItem = new StaticTextItem();
		errorItem.setTitle("");

		this.setFields(usernameItem, passwordItem, errorItem, buttonItem);
	}

	private LoginServiceAsync getService() {
		if (svc == null) {
			svc = (LoginServiceAsync) GWT.create(LoginService.class);
		}
		return svc;
	}

	/**
	 * This method is called when the button is clicked
	 */
	private void checkLogin(String userName, String password) {
		System.out.println("Checking login for " + userName);
		AsyncCallback<User> callback = new AsyncCallback<User>() {

			public void onFailure(Throwable caught) {
				errorItem.show();
				if (caught != null) {
					errorItem.setValue(caught.getMessage());
				}
				else {
					errorItem.setValue("Error");
				}
			}

			public void onSuccess(User result) {
				System.out.println("Success");
				errorItem.setValue("");
				bus.fireEventFromSource(new LoginEvent(), LoginScreen.this);
								
			}
		};
		getService().checkLogin(userName, password, callback);
	}

	public void onClick(ClickEvent event) {
		checkLogin(usernameItem.getValueAsString(), passwordItem
				.getValueAsString());
	}
}