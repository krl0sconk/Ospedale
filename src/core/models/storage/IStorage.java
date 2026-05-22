/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package core.models.storage;

import core.models.Appointment;
import core.models.Hospitalization;
import core.models.enums.Specialty;
import core.models.user.Administrator;
import core.models.user.doctor;
import core.models.user.patient;
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

    ArrayList<patient> getPatients();

    ArrayList<doctor> getDoctors();

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
    
