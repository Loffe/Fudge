package se.eloff.fudge.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.RootPanel;
import com.smartgwt.client.widgets.Window;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Fudge implements EntryPoint {
	/**
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 */
	private static final String SERVER_ERROR = "An error occurred while "
			+ "attempting to contact the server. Please check your network "
			+ "connection and try again.";

	/**
	 * Create a remote service proxy to talk to the server-side Greeting service.
	 */
	private final GreetingServiceAsync greetingService = GWT
			.create(GreetingService.class);

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		final Button showLoginDialogButton = new Button("Skicka då");

		// We can add style names to widgets
		showLoginDialogButton.addStyleName("showLoginDialogButton");

		RootPanel.get("topMenu").add(showLoginDialogButton);

		// Create the popup dialog box
		final Window dialogBox = new Window();
		dialogBox.setTitle("Login");
		dialogBox.setIsModal(true);
		final Button closeButton = new Button("Close");
		// We can set the id of a widget by accessing its Element
		closeButton.getElement().setId("closeButton");
		LoginScreen loginScreen = new LoginScreen();
		dialogBox.addChild(loginScreen);
		
		showLoginDialogButton.addClickHandler(new ClickHandler() {
			
			public void onClick(ClickEvent event) {
				dialogBox.show();
			}
		});

		// Add a handler to close the DialogBox
		closeButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				dialogBox.hide();
			}
		});


	}
}
