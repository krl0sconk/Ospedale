package core.models.events;

import core.models.storage.StorageListener;
import java.util.HashMap;

public interface EventBus {
    boolean addListener(StorageListener listener);
    boolean removeListener(StorageListener listener);
    void emitEvent(String eventName, HashMap<String, Object> payload);
}
