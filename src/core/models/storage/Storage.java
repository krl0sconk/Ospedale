/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.models.storage;

import core.models.Appointment;
import core.models.Hospitalization;
import core.models.Prescription;
import core.models.enums.Specialty;
import core.models.user.Administrator;
import core.models.user.Doctor;
import core.models.user.Patient;
import core.models.user.User;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 *
 * @author krl0s
 */
public class Storage implements IStorage {

    // Instancia Singleton
    private static Storage instance;

    // Atributos del Storage
    // Usuarios
    private ArrayList<User> users;
    private ArrayList<Appointment> apps;
    private ArrayList<Hospitalization> hosps;

    // Metodos Storage
    private Storage() {
        this.users = new ArrayList<>();
        this.apps = new ArrayList<>();
        this.hosps = new ArrayList<>();
    }

    public static IStorage getInstance() {
        if (instance == null) {
            instance = new Storage();
        }
        return instance;
    }

    @Override
    public boolean addUser(User newUser) {
        for (User user : users) {
            if (user.getId() == newUser.getId()) {
                return false;
            }
        }
        users.add(newUser);
        return true;
    }

    @Override
    public User getUserById(long id) {
        for (User user : users) {
            if (user.getId() == id) {
                return user;
            }
        }
        return null;
    }

    @Override
    public User getUserByUsername(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username.trim())) {
                return user;
            }
        }
        return null;
    }

    @Override
    public ArrayList<Patient> getPatients() {
        ArrayList<Patient> patients = new ArrayList<>();
        for (User user : users) {
            if (user instanceof Patient patient) {
                patients.add(patient);
            }
        }
        return patients;
    }

    @Override
    public ArrayList<Doctor> getDoctors() {
        ArrayList<Doctor> doctors = new ArrayList<>();
        for (User user : users) {
            if (user instanceof Doctor doctor) {
                doctors.add(doctor);
            }
        }
        return doctors;
    }

    @Override
    public ArrayList<Administrator> getAdministrators() {
        ArrayList<Administrator> admins = new ArrayList<>();
        for (User user : users) {
            if (user instanceof Administrator admin) {
                admins.add(admin);
            }
        }
        return admins;
    }

    @Override
    public boolean addAppointment(Appointment newApp) {
        for (Appointment app : apps) {
            if (app.getId().equals(newApp.getId())) {
                return false;
            }
        }
        apps.add(newApp);
        return true;
    }

    @Override
    public ArrayList<Appointment> getAppointments() {
        return apps;
    }

    @Override
    public Appointment getAppointmentById(String id) {
        for (Appointment app : apps) {
            if (app.getId().equals(id)) {
                return app;
            }
        }
        return null;
    }

    @Override
    public boolean addHospitalization(Hospitalization newHosp) {
        for (Hospitalization hosp : hosps) {
            if (hosp.getId().equals(newHosp.getId())) {
                return false;
            }
        }
        hosps.add(newHosp);
        return true;
    }

    @Override
    public ArrayList<Hospitalization> getHospitalizations() {
        return hosps;
    }

    @Override
    public Hospitalization getHospitalizationById(String id) {
        for (Hospitalization hosp : hosps) {
            if (hosp.getId().equals(id)) {
                return hosp;
            }
        }
        return null;
    }

    @Override
    public void updatePatient(long id, String username, String firstname, String lastname, String password, String email, LocalDate birthdate, boolean gender, long phone, String address) {
        for (User user : users) {
            if (user.getId() == id && user instanceof Patient patient) {
                //TODO: Metodo update (Migue)
                patient.update(username, firstname, lastname, password, email, birthdate, gender, phone, address);
                return;
            }
        }
    }

    @Override
    public void updateDoctor(long id, String username, String firstname, String lastname, String password, Specialty specialty, String licenceNumber, String assignedOffice) {
        for (User user : users) {
            if (user.getId() == id && user instanceof Doctor doctor) {
                //TODO: Metodo update (Migue)
                doctor.update(username, firstname, lastname, password, specialty, licenceNumber, assignedOffice);
                return;
            }
        }
    }
}
