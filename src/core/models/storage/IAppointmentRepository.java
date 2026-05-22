/*
 * Archivo: IAppointmentRepository.java
 * Propósito: Contrato específico para operaciones sobre citas (`Appointment`).
 * Relacionado con: `AppointmentService`, `Storage`.
 * Impacto SOLID:
 *  - ISP: interfaz enfocada para operaciones de citas.
 */
package core.models.storage;

import core.models.Appointment;
import java.util.ArrayList;

public interface IAppointmentRepository {
    boolean addAppointment(Appointment appointment);

    ArrayList<Appointment> getAppointments();

    Appointment getAppointmentById(String id);
}
