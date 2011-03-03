package se.eloff.fudge.client;

import se.eloff.fudge.client.bean.User;

import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class AdminScreen extends Canvas {

	private ListGrid userGrid;

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

	private void populateTable() {
		//User[] users = 
		
		//for(int i = 0; i < users.length ; i++){
		for(int i = 0; i < 5 ; i++){
			ListGridRecord rec = new ListGridRecord();
			rec.setAttribute("userName", "User: " + i);;
			userGrid.addData(rec);
		}
	}

}
