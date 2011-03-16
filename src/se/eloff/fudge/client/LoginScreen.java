package se.eloff.fudge.client;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import se.eloff.fudge.client.bean.User;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.form.fields.PasswordItem;
import com.smartgwt.client.widgets.form.fields.StaticTextItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.ClickEvent;
import com.smartgwt.client.widgets.form.fields.events.ClickHandler;
import com.smartgwt.client.widgets.form.fields.events.KeyPressEvent;
import com.smartgwt.client.widgets.form.fields.events.KeyPressHandler;

public class LoginScreen extends DynamicForm implements ClickHandler {

	private TextItem usernameItem;
	private PasswordItem passwordItem;
	private LoginServiceAsync svc;
	private StaticTextItem errorItem;
	private final EventBus bus;
	private boolean isChecking;
	private ButtonItem buttonItem;

	public LoginScreen(EventBus bus, LoginServiceAsync loginService) {

		this.bus = bus;
		svc = loginService;
		
		usernameItem = new TextItem();
		usernameItem.setTitle("Username");
		usernameItem.setRequired(true);

		passwordItem = new PasswordItem();
		passwordItem.setTitle("Password");
		passwordItem.setRequired(true);

		buttonItem = new ButtonItem("Login");
		buttonItem.addClickHandler(this);
		
		passwordItem.addKeyPressHandler(new KeyPressHandler() {
			public void onKeyPress(KeyPressEvent event) {
				String a = event.getKeyName();
				if (event.getKeyName().equals("Enter")) {
					onClick(null);
				}
			}
		});

		
		errorItem = new StaticTextItem();
		errorItem.setTitle("");

		this.setFields(usernameItem, passwordItem, errorItem, buttonItem);
	}

	/**
	 * This method is called when the button is clicked
	 */
	private void checkLogin(String userName, String password) {
		if (isChecking)
			return;
		
		isChecking = true;
		buttonItem.disable();
		
		System.out.println("Checking login for " + userName);
		AsyncCallback<User> callback = new AsyncCallback<User>() {

			public void onFailure(Throwable caught) {
				isChecking = false;
				buttonItem.enable();
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
				Fudge.user = result;
				bus.fireEventFromSource(new LoginEvent(result), LoginScreen.this);
				
								
			}
		};
		svc.checkLogin(userName, password, callback);
	}

	public void onClick(ClickEvent event) {
		checkLogin(usernameItem.getValueAsString(), passwordItem
				.getValueAsString());
	}
	
	@Override
	public void focus() {
		usernameItem.focusInItem();
	}
}