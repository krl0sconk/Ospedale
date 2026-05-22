/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/*
 * Archivo: patient.java
 * Propósito: Representa a un paciente del hospital, con datos personales y citas.
 * Relacionado con: `Appointment`, `Hospitalization`, `IUserRepository`, `UserService`.
 * Impacto SOLID:
 *  - SRP: encapsula estado del paciente; las operaciones transaccionales (update + eventos) se realizan en `UserService`.
 */
package core.models.user;

import java.time.LocalDate;
import java.util.ArrayList;
import core.models.Appointment;
import core.models.Hospitalization;
import java.util.HashMap;

/**
 *
 * @author edangulo
 */
public class patient extends User {
    
    private String email;
    private LocalDate birthdate;
    private boolean gender;
    private long phone;
    private String address;
    private ArrayList<Appointment> appointments;
    private Hospitalization hospitalization;
    
    public patient(long id, String username, String firstname, String lastname, String password, String email, LocalDate birthdate, boolean gender, long phone, String address) {
        super(id, username, firstname, lastname, password);
        this.email = email;
        this.birthdate = birthdate;
        this.gender = gender;
        this.phone = phone;
        this.address = address;
        this.appointments = new ArrayList<>();
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public void setPhone(long phone) {
        this.phone = phone;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setHospitalization(Hospitalization hospitalization) {
        this.hospitalization = hospitalization;
    }

    public ArrayList<Appointment> getAppointments() {
        return appointments;
    }
    
    public void addAppointment(Appointment a) {
        this.appointments.add(a);
    }

    @Override
    public HashMap<String, Object> serialize() {
        HashMap<String, Object> patmap=new HashMap<>();
        patmap.put("id", this.id );
        patmap.put("username", this.username);
        patmap.put("firstname", this.firstname);
        patmap.put("lastname", this.lastname);
        patmap.put("userType", "patient" );
        patmap.put("email", this.email);
        patmap.put("birthdate", this.birthdate.toString());
        patmap.put("gender", this.gender);
        patmap.put("phone", this.phone);
        patmap.put("address", this.address);
        return patmap;
    }
    
    public void update(String username, String firstname, String lastname, String password, String email, LocalDate birthdate, boolean gender, long phone, String address){
        this.username=username;
        this.firstname=firstname;
        this.lastname=lastname;
        this.password=password;
        this.email=email;
        this.birthdate=birthdate;
        this.gender=gender;
        this.phone=phone;
        this.address=address;
        
    }

    
    
}
