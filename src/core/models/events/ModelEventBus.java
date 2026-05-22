/*
 * Archivo: ModelEventBus.java
 * Propósito: Implementación concreta de `EventBus` basada en listeners en memoria.
 * Relacionado con: `EventBus`, `StorageListener`, `Storage` y servicios que emiten eventos.
 * Impacto SOLID:
 *  - SRP: responsable sólo de la gestión de listeners y distribución de eventos.
 *  - DIP: implementa la abstracción `EventBus` para permitir inyección y mocks en tests.
 */
package core.models.events;

import core.models.storage.StorageListener;
import java.util.ArrayList;
import java.util.HashMap;
import core.models.events.ModelEvent;

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
        ModelEvent ev = ModelEvent.fromString(eventName);
        if (ev != null) {
            emitEvent(ev, payload);
            return;
        }
        for (StorageListener listener : new ArrayList<>(this.listeners)) {
            try {
                listener.onEvent(eventName, payload);
            } catch (Exception ex) {
                // Ignore listener failures to keep notifications isolated.
            }
        }
    }

    @Override
    public void emitEvent(ModelEvent event, HashMap<String, Object> payload) {
        for (StorageListener listener : new ArrayList<>(this.listeners)) {
            try {
                listener.onEvent(event, payload);
            } catch (Exception ex) {
                // Ignore listener failures to keep notifications isolated.
            }
        }
    }
}
