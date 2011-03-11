package se.eloff.fudge.client;

import se.eloff.fudge.client.bean.User;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.ListGridEditEvent;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.EditCompleteEvent;
import com.smartgwt.client.widgets.grid.events.EditCompleteHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class AdminScreen extends Canvas {

	private ListGrid userGrid;
	private UserServiceAsync svc;

	public AdminScreen() {
		userGrid = new ListGrid();
		userGrid.setWidth(500);
		userGrid.setHeight(224);
		userGrid.setShowAllRecords(false);
		userGrid.setAlternateRecordStyles(true);
		userGrid.setCanEdit(true);
		userGrid.setEditEvent(ListGridEditEvent.CLICK);
		userGrid.setModalEditing(true);
		userGrid.addEditCompleteHandler(new EditCompleteHandler() {

			@Override
			public void onEditComplete(EditCompleteEvent event) {
				System.out.println("finished editing of a record detected....");
				final ListGridRecord record = userGrid.getRecord(event
						.getRowNum());
				User user = (User) record.getAttributeAsObject("userObject");
				User newUser = null;

				if (user == null) {
					newUser = new User();
					newUser
							.setUsername(record
									.getAttributeAsString("userName"));
					newUser.setModeratorRights(record
							.getAttributeAsBoolean("isMod"));

					AsyncCallback<User> callback = new AsyncCallback<User>() {

						public void onFailure(Throwable caught) {
							System.out.println("failed to create new user");
						}

						public void onSuccess(User result) {
							System.out.println("success in creating new user");
							record.setAttribute("userObject", result);
							System.out.println("object created, id set to: " + result.getId());

						}
					};
					getService().createUser(newUser, callback);

				}

				else {

					AsyncCallback<User> callback = new AsyncCallback<User>() {

						public void onFailure(Throwable caught) {
							System.out.println("failed to edit user");
						}

						public void onSuccess(User result) {
							System.out.println("success in editing user");
							record.setAttribute("userObject", result);
							System.out.println("EFTER: "+result.getId());
						}
					};
					user.setModeratorRights(record
							.getAttributeAsBoolean("isMod"));
					for (String attr : record.getAttributes()) {
						System.out.println(attr + " = "
								+ record.getAttribute(attr));
					}
					System.out.println("upptäckte att recordets modrights är "
							+ record.getAttributeAsBoolean("isMod"));
					user.setUsername(record.getAttributeAsString("userName"));
					System.out.println("FÖRE: "+user.getId());

					getService().editUser(user, callback);
				}


				System.out.println("removefield: "
						+ record.getAttributeAsBoolean("removeField"));

				AsyncCallback<User> callback = new AsyncCallback<User>() {

					public void onFailure(Throwable caught) {
						System.out.println("failed to remove user");
					}

					public void onSuccess(User result) {
						System.out.println("success in removing user");
						//populateTable();
						
					}
				};
				if (record.getAttributeAsBoolean("removeField") == true) {
					getService().removeUser(user, callback);
				}

			}
		});

		ListGridField nameField = new ListGridField("userName", "Username");

		ListGridField moderatorField = new ListGridField("isMod", "Moderator?");
		moderatorField.setAlign(Alignment.CENTER);
		moderatorField.setType(ListGridFieldType.BOOLEAN);

		ListGridField removeField = new ListGridField("removeField",
				"Remove User?");
		removeField.setAlign(Alignment.CENTER);
		removeField.setType(ListGridFieldType.BOOLEAN);

		userGrid.setFields(nameField, moderatorField, removeField);

		VLayout vl = new VLayout();
		vl.addMember(userGrid);
		HLayout hl = new HLayout();
		vl.addMember(hl);
		Button saveButton = new Button("Add another user");
		hl.addMember(saveButton);
		saveButton.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				ListGridRecord defaultRecordValue = new ListGridRecord();
				userGrid.startEditingNew(defaultRecordValue);

			}
		});
	
		Button updateButton = new Button("Update");
		hl.addMember(updateButton);
		updateButton.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				populateTable();
				

			}
		});

		this.addChild(vl);

		populateTable();
		ListGridRecord defaultRecordValue = new ListGridRecord();
		userGrid.startEditingNew(defaultRecordValue);
	}

	private UserServiceAsync getService() {
		if (svc == null) {
			svc = (UserServiceAsync) GWT.create(UserService.class);
		}
		return svc;
	}

	public void populateTable() {
		AsyncCallback<User[]> callback = new AsyncCallback<User[]>() {

			public void onFailure(Throwable caught) {
				System.out.println("Failed to get users!");

			}

			public void onSuccess(User[] result) {
				for (ListGridRecord r : userGrid.getRecords())
					userGrid.removeData(r);
					
				System.out.println("Successfully got users");

				for (int i = 0; i < result.length; i++) {
					ListGridRecord rec = new ListGridRecord();
					rec.setAttribute("userName", result[i].getUsername());
					rec.setAttribute("isMod", result[i].getModeratorRights());
					rec.setAttribute("id", result[i].getId());
					rec.setAttribute("userObject", result[i]);
					userGrid.addData(rec);

				}

			}
		};
		getService().getAllUsers(callback);
	}

}
