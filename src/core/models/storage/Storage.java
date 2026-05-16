/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.models.storage;

import core.models.Appointment;
import core.models.Hospitalization;
import core.models.Prescription;
import core.models.user.Administrator;
import core.models.user.Doctor;
import core.models.user.Patient;
import core.models.user.User;
import java.util.ArrayList;

/**
 *
 *
 * @author krl0s
 */
public class Storage {

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

    public static Storage getInstance() {
        if (instance == null) {
            instance = new Storage();
        }
        return instance;
    }

    public boolean addUser(User newUser) {
        for (User user : users) {
            if (user.getId() == newUser.getId()) {
                return false;
            }
        }
        users.add(newUser);
        return true;
    }

    public User getUserById(long id) {
        for (User user : users) {
            if (user.getId() == id) {
                return user;
            }
        }
        //TODO: Error?
        return null;
    }

    public User getUserByUsername(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username.trim())) {
                return user;
            }
        }
        //TODO: Error x2?
        return null;
    }

    public ArrayList<Patient> getPatients() {
        ArrayList<Patient> patients = new ArrayList<>();
        for (User user : users) {
            if (user instanceof Patient patient) {
                patients.add(patient);
            }
        }
        return patients;
    }

    public ArrayList<Doctor> getDoctors() {
        ArrayList<Doctor> doctors = new ArrayList<>();
        for (User user : users) {
            if (user instanceof Doctor doctor) {
                doctors.add(doctor);
            }
        }
        return doctors;
    }

    public ArrayList<Administrator> getAdministrators() {
        ArrayList<Administrator> admins = new ArrayList<>();
        for (User user : users) {
            if (user instanceof Administrator admin) {
                admins.add(admin);
            }
        }
        return admins;
    }

    public boolean addAppointment(Appointment newApp) {
        for (Appointment app : apps) {
            if (app.getId().equals(newApp.getId())) {
                return false;
            }
        }
        apps.add(newApp);
        return true;
    }

    public ArrayList<Appointment> getAppointments() {
        return apps;
    }

    public Appointment getAppointmentById(String id) {
        for (Appointment app : apps) {
            if (app.getId().equals(id)) {
                return app;
            }
        }
        //TODO: Error? x3
        return null;
    }

    public boolean addHospitalization(Hospitalization newHosp) {
        for (Hospitalization hosp : hosps) {
            if (hosp.getId().equals(newHosp.getId())) {
                return false;
            }
        }
        hosps.add(newHosp);
        return true;
    }

    public ArrayList<Hospitalization> getHospitalizations() {
        return hosps;
    }

    public Hospitalization getHospitalizationById(String id) {
        for (Hospitalization hosp : hosps) {
            if (hosp.getId().equals(id)) {
                return hosp;
            }
        }
        //TODO: Error? x4
        return null;
    }
}
