/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/*
 * Archivo: Hospitalization.java
 * Propósito: Entidad que modela una hospitalización (ingreso de paciente).
 * Relacionado con: `patient`, `doctor`, `RoomType`, `HospitalizationService` y `IHospitalizationRepository`.
 * Impacto SOLID:
 *  - SRP: contiene estado de hospitalización; la publicación de eventos fue movida a servicios para respetar SRP.
 *  - LSP: sustituible por su interfaz/contrato si fuera necesario.
 */
package core.models;

import core.models.enums.HospitalizationStatus;
import core.models.enums.RoomType;
import core.models.user.Patient;
import core.models.user.Doctor;
import java.time.LocalDate;
import java.util.HashMap;

/**
 *
 * @author edangulo
 */
public class Hospitalization implements ISerializable {
    
    private final String id;
    private Patient patient;
    private Doctor doctor;
    private LocalDate date;

    
    private String reason;
    private RoomType roomType;
    private String observations;
    private HospitalizationStatus status;

    public void setStatus(HospitalizationStatus status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }
    
    public Hospitalization(String id, Patient patient, Doctor doctor, LocalDate date, String reason, RoomType roomType, String observations, HospitalizationStatus status) {
        this.id = id;
        this.patient = patient;
        patient.setHospitalization(this);
        this.doctor = doctor;
        doctor.addHospitalization(this);
        this.date = date;
        this.reason = reason;
        this.roomType = roomType;
        this.observations = observations;
        this.status = status;
    }
    

    public Patient getPatient() {
        return patient;
    }

    public HospitalizationStatus getStatus() {
        return status;
    }

    public LocalDate getDate() {
        return date;
    }

    @Override
    public HashMap<String, Object> serialize() {
        HashMap<String, Object> hosmap=new HashMap<>();
        hosmap.put("id", this.id);
        if (this.patient != null) {
            hosmap.put("patientId", this.patient.getId());
            hosmap.put("patientName", this.patient.getFirstname() + " " + this.patient.getLastname());
        } else {
            hosmap.put("patientId", null);
            hosmap.put("patientName", null);
        }
        if (this.doctor != null) {
            hosmap.put("doctorId", this.doctor.getId());
            hosmap.put("doctorName", this.doctor.getFirstname() + " " + this.doctor.getLastname());
        } else {
            hosmap.put("doctorId", null);
            hosmap.put("doctorName", null);
        }
        hosmap.put("date", this.date != null ? this.date.toString() : null);
        hosmap.put("reason", this.reason);
        hosmap.put("roomType", this.roomType != null ? this.roomType.name() : null);
        hosmap.put("observations", this.observations);
        hosmap.put("status", this.status != null ? this.status.name() : null);
        
        return hosmap;
    }
    
    
    
}
