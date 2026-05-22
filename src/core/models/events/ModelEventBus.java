package core.models.events;

import core.models.storage.StorageListener;
import java.util.ArrayList;
import java.util.HashMap;

public final class ModelEventBus implements EventBus {

    private static final ModelEventBus instance = new ModelEventBus();

    private final ArrayList<StorageListener> listeners;

    private ModelEventBus() {
        this.listeners = new ArrayList<>();
    }

    public static ModelEventBus getInstance() {
        return instance;
    }

    public boolean addListener(StorageListener listener) {
        if (listener == null || this.listeners.contains(listener)) {
            return false;
        }
        return this.listeners.add(listener);
    }

    public boolean removeListener(StorageListener listener) {
        if (listener == null) {
            return false;
        }
        return this.listeners.remove(listener);
    }

    public void emitEvent(String eventName, HashMap<String, Object> payload) {
        for (StorageListener listener : new ArrayList<>(this.listeners)) {
            try {
                listener.onEvent(eventName, payload);
            } catch (Exception ex) {
                // Ignore listener failures to keep notifications isolated.
            }
        }
    }
}
