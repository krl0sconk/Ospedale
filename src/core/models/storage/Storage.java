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
import core.models.user.doctor;
import core.models.user.patient;
import core.models.user.User;
import java.time.LocalDate;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

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
    private ArrayList<StorageListener> listeners;

    // Metodos Storage
    private Storage() {
        this.users = new ArrayList<>();
        this.apps = new ArrayList<>();
        this.hosps = new ArrayList<>();
        this.listeners = new ArrayList<>();
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
        // Emit event for user added
        emitEvent("user.added", new java.util.HashMap<>());
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
    public ArrayList<patient> getPatients() {
        ArrayList<patient> patients = new ArrayList<>();
        for (User user : users) {
            if (user instanceof patient patient) {
                patients.add(patient);
            }
        }
        return patients;
    }

    @Override
    public ArrayList<doctor> getDoctors() {
        ArrayList<doctor> doctors = new ArrayList<>();
        for (User user : users) {
            if (user instanceof doctor doctor) {
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
        // Emit event for appointment added
        emitEvent("appointment.added", newApp.serialize());
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
        // Emit event for hospitalization added
        emitEvent("hospitalization.added", newHosp.serialize());
        return true;
    }

    @Override
    public boolean addListener(StorageListener listener) {
        if (listener == null) return false;
        if (this.listeners == null) this.listeners = new ArrayList<>();
        if (this.listeners.contains(listener)) return false;
        this.listeners.add(listener);
        return true;
    }

    @Override
    public boolean removeListener(StorageListener listener) {
        if (listener == null || this.listeners == null) return false;
        return this.listeners.remove(listener);
    }

    @Override
    public void emitEvent(String eventName, java.util.HashMap<String, Object> payload) {
        if (this.listeners == null || this.listeners.isEmpty()) return;
        for (StorageListener l : new ArrayList<>(this.listeners)) {
            try {
                l.onEvent(eventName, payload);
            } catch (Exception ex) {
                // ignore listener exceptions
            }
        }
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
            if (user.getId() == id && user instanceof patient patient) {
                patient.update(username, firstname, lastname, password, email, birthdate, gender, phone, address);
                emitEvent("user.updated", patient.serialize());
                return;
            }
        }
    }

    @Override
    public void updateDoctor(long id, String username, String firstname, String lastname, String password, Specialty specialty, String licenceNumber, String assignedOffice) {
        for (User user : users) {
            if (user.getId() == id && user instanceof doctor doctor) {
                doctor.update(username, firstname, lastname, password, specialty, licenceNumber, assignedOffice);
                emitEvent("user.updated", doctor.serialize());
                return;
            }
        }
    }

    public void loadUsersFromFile(String filePath) throws Exception {
        String content = Files.readString(Path.of(filePath));
        JSONObject root = new JSONObject(content);
        JSONArray usersArray = root.getJSONArray("users");

        for (int i = 0; i < usersArray.length(); i++) {
            JSONObject userJson = usersArray.getJSONObject(i);
            String type = userJson.getString("type");
            long id = userJson.getLong("id");
            String username = userJson.getString("username");
            String firstname = userJson.getString("firstname");
            String lastname = userJson.getString("lastname");
            String password = userJson.getString("password");

            if ("admin".equalsIgnoreCase(type)) {
                this.addUser(new Administrator(id, username, firstname, lastname, password));
                continue;
            }

            if ("patient".equalsIgnoreCase(type)) {
                String email = userJson.getString("email");
                LocalDate birthdate = LocalDate.parse(userJson.getString("birthdate"));
                boolean gender = userJson.getBoolean("gender");
                long phone = userJson.getLong("phone");
                String address = userJson.getString("address");
                this.addUser(new patient(id, username, firstname, lastname, password, email, birthdate, gender, phone, address));
                continue;
            }

            if ("doctor".equalsIgnoreCase(type)) {
                Specialty specialty = mapSpecialty(userJson.getString("specialty"));
                String licenceNumber = userJson.getString("licenceNumber");
                String assignedOffice = userJson.getString("assignedOffice");
                this.addUser(new doctor(id, username, firstname, lastname, password, specialty, licenceNumber, assignedOffice));
            }
        }
    }

    private Specialty mapSpecialty(String specialtyName) {
        if (specialtyName == null) {
            return null;
        }

        return switch (specialtyName.trim().toUpperCase()) {
            case "ORTHOPEDICS" -> Specialty.TRAUMATOLOGY_ORTHOPEDICS;
            case "GYNECOLOGY" -> Specialty.GYNECOLOGY_OBSTETRICS;
            default -> Specialty.valueOf(specialtyName.trim().toUpperCase());
        };
    }
}
