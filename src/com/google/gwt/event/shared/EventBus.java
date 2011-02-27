package com.google.gwt.event.shared;

import com.google.gwt.event.shared.GwtEvent.Type;

/**
 * Dispatches {@link GwtEvent}s to interested parties. Eases decoupling by
 * allowing objects to interact without having direct dependencies upon one
 * another, and without requiring event sources to deal with maintaining handler
 * lists. There will typically be one EventBus per application, broadcasting
 * events that may be of general interest.
 *
 * @see SimpleEventBus
 * @see ResettableEventBus
 * @see com.google.gwt.event.shared.testing.CountingEventBus
 */
public abstract class EventBus implements HasHandlers {

  /**
   * Adds an unfiltered handler to receive events of this type from all sources.
   * <p>
   * It is rare to call this method directly. More typically a {@link GwtEvent}
   * subclass will provide a static <code>register</code> method, or a widget
   * will accept handlers directly.
   * <p>
   * A tip: to make a handler de-register itself, the following works:
   * <code><pre>new MyHandler() {
   *  HandlerRegistration reg = MyEvent.register(eventBus, this);
   *
   *  public void onMyThing(MyEvent event) {
   *    {@literal /}* do your thing *{@literal /}
   *    reg.removeHandler();
   *  }
   * };
   * </pre></code>
   *
   * @param <H> The type of handler
   * @param type the event type associated with this handler
   * @param handler the handler
   * @return the handler registration, can be stored in order to remove the
   *         handler later
   */
  public abstract <H extends EventHandler> HandlerRegistration addHandler(
      Type<H> type, H handler);

  /**
   * Adds a handler to receive events of this type from the given source.
   * <p>
   * It is rare to call this method directly. More typically a {@link GwtEvent}
   * subclass will provide a static <code>register</code> method, or a widget
   * will accept handlers directly.
   *
   * @param <H> The type of handler
   * @param type the event type associated with this handler
   * @param source the source associated with this handler
   * @param handler the handler
   * @return the handler registration, can be stored in order to remove the
   *         handler later
   */
  public abstract <H extends EventHandler> HandlerRegistration addHandlerToSource(
      Type<H> type, Object source, H handler);

  /**
   * Fires the event from no source. Only unfiltered handlers will receive it.
   *
   * @param event the event to fire
   */
  public abstract void fireEvent(GwtEvent<?> event);

  /**
   * Fires the given event to the handlers listening to the event's type.
   * <p>
   * Any exceptions thrown by handlers will be bundled into a
   * {@link UmbrellaException} and then re-thrown after all handlers have
   * completed. An exception thrown by a handler will not prevent other handlers
   * from executing.
   *
   * @param event the event to fire
   */
  public abstract void fireEventFromSource(GwtEvent<?> event, Object source);
}
