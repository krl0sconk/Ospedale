/*
 * Archivo: Specialty.java
 * Propósito: Enumeración de especialidades médicas usadas en `doctor` y `Appointment`.
 * Relacionado con: `doctor`, `Appointment`, `JsonUserLoader`.
 * Impacto SOLID:
 *  - SRP: enum centrado en listar constantes del dominio.
 */
package core.models.enums;

public enum Specialty {
    
    GENERAL_MEDICINE,
    CARDIOLOGY,
    PEDIATRICS,
    NEUROLOGY,
    TRAUMATOLOGY_ORTHOPEDICS,
    GYNECOLOGY_OBSTETRICS,
    DERMATOLOGY,
    PSYCHIATRY,
    ONCOLOGY,
    OPHTHALMOLOGY,
    INTERNAL_MEDICINE
    
}
