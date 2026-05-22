package core.models.storage;

import core.models.events.ModelEvent;
import java.util.HashMap;

/**
 * Listener interface for Storage events.
 */
public interface StorageListener {
    void onEvent(String eventName, HashMap<String, Object> payload);

    default void onEvent(ModelEvent event, HashMap<String, Object> payload) {
        // Default adapters call the legacy string-based handler so existing listeners keep working.
        onEvent(event.getEventName(), payload);
    }
}
