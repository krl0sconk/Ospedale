/*
 * Archivo: EventBus.java
 * Propósito: Interfaz que define el contrato para publicar y suscribir eventos del dominio.
 * Relacionado con: `ModelEventBus`, `Storage`, `StorageListener`, servicios (`UserService`, ...).
 * Impacto SOLID:
 *  - DIP: permite depender de una abstracción para emitir/recibir eventos (mejora DIP y testabilidad).
 */
package core.models.events;

import core.models.storage.StorageListener;
import java.util.HashMap;

public interface EventBus {
    boolean addListener(StorageListener listener);
    boolean removeListener(StorageListener listener);
    void emitEvent(String eventName, HashMap<String, Object> payload);
    void emitEvent(ModelEvent event, HashMap<String, Object> payload);
}
