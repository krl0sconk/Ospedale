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
            bus.emitEvent("appointment.added", appointment.serialize());
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
            bus.emitEvent("appointment.prescription_added", app.serialize());
        }
        return added;
    }

    public void updateStatus(String appointmentId, core.models.enums.AppointmentStatus status) {
        Appointment app = repo.getAppointmentById(appointmentId);
        if (app == null) return;
        app.setStatus(status);
        bus.emitEvent("appointment.updated", app.serialize());
    }
}
