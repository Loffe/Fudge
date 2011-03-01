package se.eloff.fudge.client;

import se.eloff.fudge.client.bean.User;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;

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
	 * Create a remote service proxy to talk to the server-side Greeting
	 * service.
	 */
	private final GreetingServiceAsync greetingService = GWT
			.create(GreetingService.class);

	private EventBus bus;

	LoginServiceAsync loginService;

	protected Dashboard dashboard;

	protected IndexCanvas index;

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		bus = new SimpleEventBus();

		getLoginService().getLoggedInUser(new AsyncCallback<User>() {

			public void onSuccess(User user) {
				if (user == null) {
					initLogin();
				} else {
					createComponents(user);
					createLogoutButton();
				}
			}

			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub

			}
		});
	}

	private void initLogin() {

		final Button showLoginDialogButton = new Button("Login");

		// We can add style names to widgets
		showLoginDialogButton.setStyleName("showLoginDialogButton");

		RootPanel.get("topMenu").add(showLoginDialogButton);

		// Create the popup dialog box
		final Window dialogBox = new Window();
		dialogBox.setAutoCenter(true);
		dialogBox.setAutoSize(true);
		dialogBox.setTitle("Login");
		dialogBox.setIsModal(true);
		dialogBox.setShowModalMask(true);

		LoginScreen loginScreen = new LoginScreen(bus, loginService);
		dialogBox.addItem(loginScreen);

		showLoginDialogButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				dialogBox.show();
			}
		});

		bus.addHandlerToSource(LoginEvent.TYPE, loginScreen,
				new LoginEventHandler() {

					public void onLogin(LoginEvent event) {
						dialogBox.hide();
						createComponents(event.getUser());
						showLoginDialogButton.hide();
						createLogoutButton();

						switchToView(index);
					}

				});
	}

	protected void createLogoutButton() {
		final Button logoutButton = new Button("Logout");
		logoutButton.setStyleName("showLoginDialogButton");
		logoutButton.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				getLoginService().logout(new AsyncCallback<Void>() {

					public void onFailure(Throwable caught) {
						reload();
					}

					public void onSuccess(Void result) {
						reload();

					}
				});

			}
		});

		RootPanel.get("topMenu").add(logoutButton);
	}

	private LoginServiceAsync getLoginService() {
		if (loginService == null) {
			loginService = (LoginServiceAsync) GWT.create(LoginService.class);
		}
		return loginService;
	}

	protected void switchToView(Canvas canvas) {
		dashboard.hide();
		// index.hide();
		canvas.show();
	}

	private void createComponents(User user) {
		dashboard = new Dashboard(user);
		dashboard.hide();
		RootPanel.get("content").add(dashboard);

		index = new IndexCanvas(bus);
		bus.addHandler(ForumEvent.TYPE, new ForumEventHandler() {

			public void onShow(ForumEvent forumEvent) {
				System.out.println("Gonna show forum. "
						+ forumEvent.getForum().getId());
			}
		});
		// index.hide();
		RootPanel.get("content").add(index);
	}

	private native void reload() /*-{
		$wnd.location.reload();
	}-*/;
}
