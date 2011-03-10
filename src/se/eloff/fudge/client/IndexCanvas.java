package se.eloff.fudge.client;

import se.eloff.fudge.client.bean.Forum;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.types.Cursor;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.HStack;
import com.smartgwt.client.widgets.layout.VStack;

public class IndexCanvas extends VStack {

	private ForumServiceAsync svc;
	private final EventBus bus;

	public IndexCanvas(EventBus bus) {
		this.bus = bus;
		this.setWidth("80%");
		refresh();
	}

	private ForumServiceAsync getService() {
		if (svc == null) {
			svc = (ForumServiceAsync) GWT.create(ForumService.class);
		}
		return svc;
	}

	protected void refresh() {
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
			this.addMember(createForumItem(f));
		}
		// Hack to force redraw on first page load
		this.resizeBy(1, 0);
		this.resizeBy(-1, 0);

	}

	protected Canvas createForumItem(final Forum forum) {
		HStack hstack = new HStack();
		hstack.setStyleName("forum");
		hstack.setWidth("80%");
		
		VStack vstack = new VStack();
		Label forúmlabel = new Label(forum.getName());
		forúmlabel.setStyleName("forumName");
		forúmlabel.setWidth(400);
		forúmlabel.setHeight(40);
		forúmlabel.setCursor(Cursor.HAND);
		
		forúmlabel.addClickHandler(new ClickHandler() {
			
			public void onClick(ClickEvent event) {
				bus.fireEvent(new ForumEvent(forum));
				
			}
		});
		
		vstack.addMember(forúmlabel);
		vstack.addMember(new Label(forum.getDescription()));
		
		hstack.addMember(vstack);
		hstack.addMember(new Label(String.valueOf(forum.getNrOfTopics())));
		
		

		return hstack;
	}
}
