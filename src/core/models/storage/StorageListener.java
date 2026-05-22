package core.models.storage;

import java.util.HashMap;

/**
 * Listener interface for Storage events.
 */
public interface StorageListener {
    void onEvent(String eventName, HashMap<String, Object> payload);
}
