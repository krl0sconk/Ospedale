package core.models.storage;

import core.models.Appointment;
import java.util.ArrayList;

public interface IAppointmentRepository {
    boolean addAppointment(Appointment appointment);

    ArrayList<Appointment> getAppointments();

    Appointment getAppointmentById(String id);
}
