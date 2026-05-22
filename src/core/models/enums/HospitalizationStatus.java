/*
 * Archivo: HospitalizationStatus.java
 * Propósito: Estados posibles para una `Hospitalization`.
 * Relacionado con: `Hospitalization`, `HospitalizationService`.
 * Impacto SOLID:
 *  - SRP: enum enfocado en representar el estado del proceso de hospitalización.
 */
package core.models.enums;

public enum HospitalizationStatus {

    REQUESTED,
    ONGOING,
    CANCELED

}
