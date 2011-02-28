package se.eloff.fudge.client;

import se.eloff.fudge.client.bean.User;

import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.Label;

public class Dashboard extends Canvas {
	public Dashboard(User user) {
		Label label = new Label("Hello" + user.getUsername());
		
		this.addChild(label);
	}
}
