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

		ListGridField nameField = new ListGridField("userName", "Username");

		ListGridField moderatorField = new ListGridField("isModerator",
				"Moderator?");
		moderatorField.setAlign(Alignment.CENTER);
		moderatorField.setType(ListGridFieldType.BOOLEAN);

		ListGridField removeField = new ListGridField("removeField",
				"Remove User?");
		removeField.setAlign(Alignment.CENTER);
		removeField.setType(ListGridFieldType.BOOLEAN);

		userGrid.setFields(nameField, moderatorField, removeField);

		VLayout vl = new VLayout();
		vl.addMember(userGrid);
		Button saveButton = new Button("Update changes");
		vl.addMember(saveButton);
		saveButton.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				ListGridRecord[] recs = userGrid.getRecords();
				for( ListGridRecord rec : recs){
					setModerator(rec.getAttributeAsString("userName"), rec.getAttributeAsBoolean("isModerator"));

					if(rec.getAttributeAsBoolean("removeField") == true){
						removeUser(rec.getAttributeAsString("userName"));						
					}
					
					//System.out.println(userGrid.getEditedCell(0, "isModerator"));
					//userGrid.setEditValue(rowNum, fieldName, value)

				}
				// Do some magic selection
				//userGrid.getAllEditRows();
				
				//userGrid.setEdit
				
			}
			
		});

		this.addChild(vl);

		populateTable();
	}

	protected void setModerator(String user,
			Boolean isMod) {
		AsyncCallback<Boolean> callback = new AsyncCallback<Boolean>() {

			public void onFailure(Throwable caught) {
				System.out.println("Failed to set user mod rights!");

			}

			public void onSuccess(Boolean result) {
				System.out.println("Successfully set user mod rights");
				}

			
		};
		getService().setModerator(user, isMod, callback);
		
	}

	protected void removeUser(String user) {
		AsyncCallback<Boolean> callback = new AsyncCallback<Boolean>() {

			public void onFailure(Throwable caught) {
				System.out.println("Failed to remove user!");

			}

			public void onSuccess(Boolean result) {
				System.out.println("Successfully removed user");
				}

			
		};
		getService().removeUser(user, callback);
		
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
				System.out.println("Successfully got users");

				for (int i = 0; i < result.length; i++) {
					ListGridRecord rec = new ListGridRecord();
					rec.setAttribute("userName", result[i].getUsername());
					System.out.println("MODRIGHTS:  " + result[i].getModeratorRights());
					rec.setAttribute("isModerator", result[i].getModeratorRights());
					userGrid.addData(rec);

				}

			}
		};
		getService().getAllUsers(callback);
	}

}
