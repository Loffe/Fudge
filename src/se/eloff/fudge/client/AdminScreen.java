package se.eloff.fudge.client;

import se.eloff.fudge.client.bean.User;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class AdminScreen extends Canvas {

	private ListGrid userGrid;
	private UserServiceAsync svc;


	public AdminScreen() {
		userGrid = new ListGrid();
		userGrid.setWidth(500);
		userGrid.setHeight(224);
		userGrid.setShowAllRecords(false);
		userGrid.setCanEdit(false);

		ListGridField nameField = new ListGridField("userName", "Username");
		ListGridField moderatorField = new ListGridField("isModerator",
				"Moderator?");
		ListGridField removeField = new ListGridField("removeField",
				"Remove User?");
		userGrid.setFields(nameField, moderatorField, removeField);

		this.addChild(userGrid);

		populateTable();
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
				
				for(int i = 0; i < result.length ; i++){
					ListGridRecord rec = new ListGridRecord();
					rec.setAttribute("userName", result[i].getUsername());
					userGrid.addData(rec);
					
				}
				
				
			}
		};
		getService().getAllUsers(callback);
	}


}
