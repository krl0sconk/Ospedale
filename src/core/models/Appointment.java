/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.models;

import core.models.enums.Specialty;
import core.models.enums.AppointmentStatus;
import core.models.user.patient;
import core.models.user.doctor;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author edangulo
 */
public class Appointment implements ISerializable{
    
    private final String id;
    private patient patient;
    private doctor doctor;
    private Specialty specialty;
    private LocalDateTime datetime;
    private String reason;
    private boolean type;
    private ArrayList<Prescription> prescriptions;
    private AppointmentStatus status;
    private String diagnosis;
    private String observations;
    private String recommendedTreatment;
    private String followUp;

    public Appointment(String id, patient patient, doctor doctor, Specialty specialty, LocalDateTime datetime, String reason, boolean type) {
        this.id = id;
        this.patient = patient;
        this.doctor = doctor;
        this.specialty = specialty;
        this.datetime = datetime;
        this.reason = reason;
        this.type = type;
        this.status = AppointmentStatus.REQUESTED;
        this.prescriptions = new ArrayList<>();
    }
    
    
    public void setReason(String reason) {
        this.reason = reason;
    }

    public void setPrescriptions(ArrayList<Prescription> prescriptions) {
        this.prescriptions = prescriptions;
    }
    
    
    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }

    public void setRecommendedTreatment(String recommendedTreatment) {
        this.recommendedTreatment = recommendedTreatment;
    }

    public void setFollowUp(String followUp) {
        this.followUp = followUp;
    }


    public void setStatus(AppointmentStatus status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public doctor getDoctor() {
        return doctor;
    }

    public Specialty getSpecialty() {
        return specialty;
    }

    public LocalDateTime getDatetime() {
        return datetime;
    }

    public boolean isType() {
        return type;
    }

    public AppointmentStatus getStatus() {
        return status;
    }

    public patient getappient() {
        return patient;
    }

    public boolean addPrescription(Prescription prescrip) {
        return this.prescriptions.add(prescrip);
    }

    public String getReason() {
        return this.reason;
    }

    public ArrayList<Prescription> getPrescriptions() {
        return prescriptions;
    }

    
    public void setDatetime(LocalDateTime newDatetime) {
        this.datetime=newDatetime;
    }

    @Override
    public HashMap<String, Object> serialize() {
        HashMap<String, Object> appmap=new HashMap<>();
        appmap.put("id", this.id );
        appmap.put("username", this.id);
        appmap.put("firstname", this.patient);
        appmap.put("lastname", this.doctor);
        appmap.put("email", this.specialty);
        appmap.put("birthdate", this.datetime);
        appmap.put("gender", this.reason);
        appmap.put("phone", this.type);
        appmap.put("address", this.status.name());
        appmap.put("prescriptions",this.prescriptions );
        return appmap;
    }
    
}
