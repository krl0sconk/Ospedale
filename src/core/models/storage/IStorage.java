/*
 * Archivo: IStorage.java
 * Propósito: Interfaz compuesta usada para compatibilidad hacia atrás por controladores.
 * Relacionado con: `Storage`, `IUserRepository`, `IAppointmentRepository`, `IHospitalizationRepository`.
 * Impacto SOLID:
 *  - Temporal: mantiene una interfaz compuesta para compatibilidad; recomendamos migrar a las interfaces específicas (ISP).
 */
package core.models.storage;

import core.models.Appointment;
import core.models.Hospitalization;
import core.models.enums.Specialty;
import core.models.user.Administrator;
import core.models.user.Doctor;
import core.models.user.Patient;
import core.models.user.User;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author krl0s
 */
public interface IStorage {

    boolean addUser(User user);

    User getUserById(long id);

    User getUserByUsername(String username);

    ArrayList<Patient> getPatients();

    ArrayList<Doctor> getDoctors();

    ArrayList<Administrator> getAdministrators();

    void updatePatient(long id, String username, String firstname, String lastname, String password, String email, LocalDate birthdate, boolean gender, long phone, String address);

    void updateDoctor(long id, String username, String firstname, String lastname, String password, Specialty specialty, String licenceNumber, String assignedOffice);

    boolean addAppointment(Appointment appointment);

    ArrayList<Appointment> getAppointments();

    Appointment getAppointmentById(String id);

    boolean addHospitalization(Hospitalization hospitalization);

    ArrayList<Hospitalization> getHospitalizations();

    Hospitalization getHospitalizationById(String id);
    
    boolean addListener(StorageListener listener);

    boolean removeListener(StorageListener listener);

    void emitEvent(String eventName, java.util.HashMap<String, Object> payload);
}
    
