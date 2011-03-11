package se.eloff.fudge.client;

import java.util.ArrayList;

import se.eloff.fudge.client.bean.User;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.layout.VStack;

public abstract class ItemCanvas<ContainerType, ItemType> extends VStack {
	protected ContainerType currentContainer;
	protected ArrayList<ItemType> currentItems;
	protected VStack itemCanvas;
	protected User user;

	AsyncCallback<ItemType[]> updateCallback = new AsyncCallback<ItemType[]>() {
		@Override
		public void onFailure(Throwable caught) {
			System.out.println("Failed to get items!");
		}

		@Override
		public void onSuccess(ItemType[] result) {
			System.out.println("Successfully got posts");
			currentItems.clear();
			for (ItemType item : result) {
				currentItems.add(item);
			}
			updateList(result);
		}
	};
	protected final EventBus bus;

	public ItemCanvas(EventBus bus) {
		this.bus = bus;
		currentItems = new ArrayList<ItemType>();
		itemCanvas = new VStack();
		this.addMember(itemCanvas);
	}

	public void setUser(User user) {
		this.user = user;
	}

	void showItem(ContainerType container) {
		currentContainer = container;
	}

	protected void updateList(ItemType[] items) {
		itemCanvas.setAnimateMembers(false);
		itemCanvas.removeMembers(itemCanvas.getMembers());

		System.out.println("update list of items: " + items.length);

		for (ItemType item : items) {
			itemCanvas.addMember(createItem(item));
		}

	}

	protected void appendItem(ItemType item) {
		itemCanvas.setAnimateMembers(true);
		int position = currentItems.size();
		currentItems.add(item);
		itemCanvas.addMember(createItem(item), position);
	}

	protected abstract Canvas createItem(ItemType item);

}
