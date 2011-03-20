package se.eloff.fudge.client;

import se.eloff.fudge.client.bean.Forum;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.smartgwt.client.types.Cursor;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.HTMLFlow;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.Layout;
import com.smartgwt.client.widgets.layout.VStack;

public class IndexCanvas extends ItemCanvas<IndexCanvas.Index, Forum> {
	/**
	 * Dummy used as container for forums. Never instantiated since there
	 * is only one index.
	 */
	interface Index{}

	private ForumServiceAsync svc;

	public IndexCanvas(EventBus bus) {
		super(bus);
		this.setWidth("80%");
		showItem(null);
	}

	private ForumServiceAsync getService() {
		if (svc == null) {
			svc = (ForumServiceAsync) GWT.create(ForumService.class);
		}
		return svc;
	}

	@Override
	public void showItem(Index index) {
		super.showItem(null);
		getService().getAllForums(updateCallback);
	}

	public void showItem() {
		showItem(null);
	}

	@Override
	protected void updateList(Forum[] forums) {
		super.updateList(forums);
		// Hack to force redraw on first page load
		this.resizeBy(1, 0);
		this.resizeBy(-1, 0);

	}

	@Override
	protected Canvas createItem(final Forum forum) {
		Layout hstack = new HLayout();
		hstack.setPadding(5);
		hstack.setShowEdges(true);
		hstack.setWidth100();
		hstack.setAutoHeight();
		hstack.setStyleName("forum");
		hstack.setCursor(Cursor.HAND);
		
		VStack vstack = new VStack();
		Canvas forumLabel = new HTMLFlow(forum.getName());
		forumLabel.setStyleName("forumName");
		forumLabel.setCursor(Cursor.HAND);
		
		forumLabel.addClickHandler(new ClickHandler() {
			
			public void onClick(ClickEvent event) {
				bus.fireEvent(new ForumEvent(forum));
				
			}
		});

		vstack.addMember(forumLabel);
		vstack.addMember(new HTMLFlow(forum.getDescription()));

		hstack.addMember(vstack);
		int num = forum.getNrOfTopics();
		String numberOfTopics = String.valueOf(forum.getNrOfTopics()) + " topic";
		if (num != 1) {
			// Append pluralis s
			numberOfTopics += "s";
		}

		hstack.addMember(new HTMLFlow(numberOfTopics));
		
		

		return hstack;
	}
}
