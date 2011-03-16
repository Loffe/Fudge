package se.eloff.fudge.client;

import se.eloff.fudge.client.bean.Forum;
import se.eloff.fudge.client.bean.Topic;
import se.eloff.fudge.client.bean.User;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;

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

	private TabSet tabSet;

	private Tab indexTab;

	private HLayout topMenu;

	private VLayout mainLayout;

	public static User user;

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		bus = new SimpleEventBus();

		initLayout();

		getLoginService().getLoggedInUser(new AsyncCallback<User>() {

			public void onSuccess(User user) {
				if (user == null) {
					initLoginComponents();
				} else {
					createComponents(user);
					createButtons();
				}
			}

			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub

			}
		});
	}

	protected void initLayout() {
		mainLayout = new VLayout();
		mainLayout.setHeight100();
		mainLayout.setWidth100();
		topMenu = new HLayout();
		topMenu.setHeight(40);
		mainLayout.addMember(topMenu);

		RootPanel.get("content").add(mainLayout);
	}

	private void initLoginComponents() {

		final Button showLoginDialogButton = new Button("Login");

		// We can add style names to widgets
		showLoginDialogButton.setStyleName("showLoginDialogButton");

		topMenu.addMember(showLoginDialogButton);

		// Create the popup dialog box
		final Window dialogBox = new Window();
		dialogBox.setAutoCenter(true);
		dialogBox.setAutoSize(true);
		dialogBox.setTitle("Login");
		dialogBox.setIsModal(true);
		dialogBox.setShowModalMask(true);

		final LoginScreen loginScreen = new LoginScreen(bus, loginService);
		dialogBox.addItem(loginScreen);

		showLoginDialogButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				dialogBox.show();
				loginScreen.focus();
			}
		});

		bus.addHandlerToSource(LoginEvent.TYPE, loginScreen,
				new LoginEventHandler() {

					public void onLogin(LoginEvent event) {
						dialogBox.hide();
						createComponents(event.getUser());
						showLoginDialogButton.clear();
						createButtons();
					}
				});
	}

	protected void createButtons() {
		// CREATE LOGOUT BUTTON **********************'
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
		topMenu.addMember(logoutButton);

		if (Fudge.user.getAdminRights()) {
			// CREATE ADMINBUTTON *******************
			final Button adminButton = new Button("Admin");

			// Create the popup dialog box
			final Window adminWindow = new Window();
			adminWindow.setAutoCenter(true);
			adminWindow.setAutoSize(true);
			adminWindow.setTitle("Admin");

			final AdminScreen adminScreen = new AdminScreen();
			adminWindow.addItem(adminScreen);

			adminButton.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					adminWindow.show();
					adminScreen.focus();
				}
			});
			topMenu.addMember(adminButton);
		}
	}

	private LoginServiceAsync getLoginService() {
		if (loginService == null) {
			loginService = (LoginServiceAsync) GWT.create(LoginService.class);
		}
		return loginService;
	}

	private void createComponents(User user) {
		dashboard = new Dashboard(user);
		dashboard.hide();

		index = new IndexCanvas(bus);
		bus.addHandler(ForumEvent.TYPE, new ForumEventHandler() {

			public void onShow(ForumEvent forumEvent) {
				System.out.println("Gonna show forum. "
						+ forumEvent.getForum().getId());
				Forum forum = forumEvent.getForum();
				Tab tab = openForum(forum);
				tabSet.addTab(tab);
				tabSet.selectTab(tab);
			}
		});

		bus.addHandler(TopicEvent.TYPE, new TopicEventHandler() {
			@Override
			public void onShow(TopicEvent topicEvent) {
				Topic t = topicEvent.getTopic();
				System.out.println("Gonna show topic " + t.getId()
						+ " in forum " + t.getForumId());
				Tab tab = openTopic(t);
				tabSet.addTab(tab);
				tabSet.selectTab(tab);
			}
		});

		// index.hide();

		indexTab = new Tab("Index");

		indexTab.setPane(index);

		tabSet = new TabSet();
		tabSet.setWidth100();
		tabSet.setHeight("*");

		tabSet.addTab(indexTab);

		mainLayout.addMember(tabSet);
	}

	protected Tab openForum(Forum forum) {
		Tab tab = new Tab(forum.getName());
		tab.setCanClose(true);
		ForumCanvas forumCanvas = new ForumCanvas(bus);
		forumCanvas.showItem(forum);
		tab.setPane(forumCanvas);
		return tab;
	}

	protected Tab openTopic(Topic topic) {
		Tab tab = new Tab(topic.getName());
		tab.setCanClose(true);
		TopicCanvas topicCanvas = new TopicCanvas(bus);
		topicCanvas.showItem(topic);
		tab.setPane(topicCanvas);
		return tab;
	}

	private native void reload() /*-{
		$wnd.location.reload();
	}-*/;
}
