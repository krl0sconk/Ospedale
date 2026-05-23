/*
 * Archivo: IHospitalizationRepository.java
 * Propósito: Contrato específico para operaciones sobre hospitalizaciones.
 * Relacionado con: `HospitalizationService`, `Storage`.
 * Impacto SOLID:
 *  - ISP: interfaz enfocada que facilita pruebas y separación de responsabilidades.
 */
package core.models.storage;

import core.models.Hospitalization;
import java.util.ArrayList;

public interface IHospitalizationRepository {
    boolean addHospitalization(Hospitalization hospitalization);

    ArrayList<Hospitalization> getHospitalizations();

    Hospitalization getHospitalizationById(String id);
}
