package se.eloff.fudge.client;

import se.eloff.fudge.client.bean.Forum;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.layout.VStack;

public class IndexCanvas extends VStack {

	private ForumServiceAsync svc;
	public IndexCanvas(){
		refresh();
	}
	private ForumServiceAsync getService() {
		if (svc == null) {
			svc = (ForumServiceAsync) GWT.create(ForumService.class);
		}
		return svc;
	}
	
	protected void refresh(){
		AsyncCallback<Forum[]> callback = new AsyncCallback<Forum[]>() {

			public void onFailure(Throwable caught) {
				System.out.println("Failed to get forums!");
			}

			public void onSuccess(Forum[] result) {
				System.out.println("Successfully got forums");
				updateList(result);
			}
		};
		getService().getAllForums(callback);
	}
	protected void updateList(Forum[] forums) {
		this.clear();
		System.out.println("update list" + forums.length);
		
		for (Forum f : forums) {
			Label forum = new Label(f.getName());
			this.addMember(forum);
		}
		this.draw();
		
	}
}