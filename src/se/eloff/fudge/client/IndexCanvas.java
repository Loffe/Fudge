package se.eloff.fudge.client;

import se.eloff.fudge.client.bean.Forum;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.smartgwt.client.types.Cursor;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.HStack;
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

	@Override
	protected void updateList(Forum[] forums) {
		super.updateList(forums);
		// Hack to force redraw on first page load
		this.resizeBy(1, 0);
		this.resizeBy(-1, 0);

	}

	@Override
	protected Canvas createItem(final Forum forum) {
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
