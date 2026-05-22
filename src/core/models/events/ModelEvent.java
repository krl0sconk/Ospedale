/*
 * Archivo: ModelEvent.java
 * Propósito: Enumeración de eventos canonizados usados por el `EventBus`.
 * Relacionado con: `ModelEventBus`, `Storage`, `services`.
 * Impacto SOLID:
 *  - SRP/DIP: centraliza nombres de eventos para evitar string literals dispersos y facilitar el DIP.
 */
package core.models.events;

public enum ModelEvent {
    USER_ADDED("user.added"),
    USER_UPDATED("user.updated"),
    APPOINTMENT_ADDED("appointment.added"),
    APPOINTMENT_UPDATED("appointment.updated"),
    APPOINTMENT_PRESCRIPTION_ADDED("appointment.prescription_added"),
    HOSPITALIZATION_ADDED("hospitalization.added"),
    HOSPITALIZATION_UPDATED("hospitalization.updated");

    private final String eventName;

    ModelEvent(String eventName) {
        this.eventName = eventName;
    }

    public String getEventName() {
        return this.eventName;
    }

    public static ModelEvent fromString(String name) {
        if (name == null) return null;
        for (ModelEvent e : values()) {
            if (e.eventName.equalsIgnoreCase(name.trim())) return e;
        }
        return null;
    }
}
