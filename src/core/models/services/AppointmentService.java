/*
 * Archivo: AppointmentService.java
 * Propósito: Servicio que coordina operaciones sobre `Appointment` y publica eventos correspondientes.
 * Relacionado con: `IAppointmentRepository`, `EventBus`, controladores que manejan citas.
 * Impacto SOLID:
 *  - SRP: separa la orquestación y publicación de eventos de la entidad `Appointment`.
 *  - DIP: depende de abstracciones para facilitar pruebas y desacoplar implementaciones.
 */
package core.models.services;

import core.models.Appointment;
import core.models.Prescription;
import core.models.storage.IAppointmentRepository;
import core.models.events.EventBus;
import java.util.ArrayList;
import java.util.HashMap;

public final class AppointmentService {

    private final IAppointmentRepository repo;
    private final EventBus bus;

    public AppointmentService(IAppointmentRepository repo, EventBus bus) {
        this.repo = repo;
        this.bus = bus;
    }

    public boolean addAppointment(Appointment appointment) {
        boolean added = repo.addAppointment(appointment);
        if (added) {
            bus.emitEvent(core.models.events.ModelEvent.APPOINTMENT_ADDED, appointment.serialize());
        }
        return added;
    }

    public ArrayList<Appointment> getAppointments() {
        return repo.getAppointments();
    }

    public Appointment getAppointmentById(String id) {
        return repo.getAppointmentById(id);
    }

    public boolean addPrescription(String appointmentId, Prescription prescrip) {
        Appointment app = repo.getAppointmentById(appointmentId);
        if (app == null) return false;
        boolean added = app.addPrescription(prescrip);
        if (added) {
            bus.emitEvent(core.models.events.ModelEvent.APPOINTMENT_PRESCRIPTION_ADDED, app.serialize());
        }
        return added;
    }

    public void updateStatus(String appointmentId, core.models.enums.AppointmentStatus status) {
        Appointment app = repo.getAppointmentById(appointmentId);
        if (app == null) return;
        app.setStatus(status);
        bus.emitEvent(core.models.events.ModelEvent.APPOINTMENT_UPDATED, app.serialize());
    }
}
