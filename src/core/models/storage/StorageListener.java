/*
 * Archivo: StorageListener.java
 * Propósito: Interfaz para escuchar eventos emitidos por el `Storage`/`EventBus`.
 * Relacionado con: `EventBus`, `ModelEventBus`, servicios y controladores que reaccionan a cambios.
 * Impacto SOLID:
 *  - DIP: define un contrato para listeners, permitiendo implementar handlers desacoplados de la fuente.
 */
package core.models.storage;

import core.models.events.ModelEvent;
import java.util.HashMap;

public interface StorageListener {
    void onEvent(String eventName, HashMap<String, Object> payload);

    default void onEvent(ModelEvent event, HashMap<String, Object> payload) {
        onEvent(event.getEventName(), payload);
    }
}
