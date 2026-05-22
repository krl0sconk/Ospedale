/*
 * Archivo: AppointmentStatus.java
 * Propósito: Estados posibles para `Appointment`.
 * Relacionado con: `Appointment`, `AppointmentService`.
 * Impacto SOLID:
 *  - SRP: enum que modela estados del ciclo de vida de la cita.
 */
package core.models.enums;

public enum AppointmentStatus {
    
    REQUESTED, 
    PENDING, 
    COMPLETED, 
    CANCELED
    
}
