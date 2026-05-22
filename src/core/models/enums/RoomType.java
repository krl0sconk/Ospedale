/*
 * Archivo: RoomType.java
 * Propósito: Tipos de habitaciones usadas por `Hospitalization`.
 * Relacionado con: `Hospitalization`.
 * Impacto SOLID:
 *  - SRP: enum simple que clasifica tipos de habitación.
 */
package core.models.enums;

public enum RoomType {
    
    STANDARD,
    ICU,            // Intensive Care Unit
    NICU,           // Neonatal Intensive Care Unit
    IMC,            // Intermediate Care Unit
    ISOLATION
    
}
