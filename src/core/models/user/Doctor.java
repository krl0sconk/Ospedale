/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.models.user;

import java.util.ArrayList;
import core.models.Appointment;
import core.models.Hospitalization;
import core.models.enums.Specialty;
import java.util.HashMap;

/**
 *
 * @author edangulo
 */
public class doctor extends User {
    
    private Specialty specialty;
    private String licenceNumber;
    private String assignedOffice;
    private ArrayList<Appointment> appointments;
    private ArrayList<Hospitalization> hospitalizations;

    public doctor(long id, String username, String firstname, String lastname, String password, Specialty specialty, String licenceNumber, String assignedOffice ) {
        super(id, username, firstname, lastname, password);
        this.specialty = specialty;
        this.licenceNumber = licenceNumber;
        this.assignedOffice = assignedOffice;
        this.appointments = new ArrayList<>();
        this.hospitalizations = new ArrayList<>();
    }

    public ArrayList<Appointment> getAppointments() {
        return appointments;
    }

    public Specialty getSpecialty() {
        return specialty;
    }
    
    
    public void setSpecialty(Specialty specialty) {
        this.specialty = specialty;
    }

    public void setLicenceNumber(String licenceNumber) {
        this.licenceNumber = licenceNumber;
    }

    public void setAssignedOffice(String assignedOffice) {
        this.assignedOffice = assignedOffice;
    }

    @Override
    public HashMap<String, Object> serialize() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    public boolean addHospitalization(Hospitalization hosp){
        return hospitalizations.add(hosp);
    }
    
    public void update(String username, String firstname, String lastname, String password, Specialty specialty, String licenceNumber, String assignedOffice){
        this.username=username;
        this.firstname=firstname;
        this.lastname=lastname;
        this.password=password;
        this.specialty=specialty;
        this.licenceNumber=licenceNumber;
        this.assignedOffice=assignedOffice;
    }
}
