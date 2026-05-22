/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/*
 * Archivo: Prescription.java
 * Propósito: Representa una receta médica asociada a una `Appointment`.
 * Relacionado con: `Appointment` y la serialización (`ISerializable`).
 * Impacto SOLID:
 *  - SRP: enfocado en los datos de la prescripción (cumple SRP).
 */
package core.models;

import java.util.HashMap;

/**
 *
 * @author jjlora
 */
public class Prescription implements ISerializable {
    
    private String medicationName;
    private double dose;
    private String administrationRoute;
    private int treatmentDuration;
    private String additionalInstructions;
    private int frecuency;

    public Prescription(String medicationName, double dose, String administrationRoute, int treatmentDuration, String additionalInstructions, int frecuency) {

        
        this.medicationName = medicationName;
        this.dose = dose;
        this.administrationRoute = administrationRoute;
        this.treatmentDuration = treatmentDuration;
        this.additionalInstructions = additionalInstructions;
        this.frecuency = frecuency;
    }

    @Override
    public HashMap<String, Object> serialize() {
        HashMap<String, Object> presmap=new HashMap<>();
        presmap.put("medicationName", this.medicationName );
        presmap.put("administrationRoute", this.administrationRoute );
        presmap.put("treatmentDuration", this.treatmentDuration);
        presmap.put("additionalInstructions", this.additionalInstructions);
        presmap.put("frequency", this.frecuency);
        presmap.put("dose", this.dose);
        return presmap;
    }

    public String getMedicationName() {
        return medicationName;
    }

    public void setMedicationName(String medicationName) {
        this.medicationName = medicationName;
    }

    public double getDose() {
        return dose;
    }

    public void setDose(double dose) {
        this.dose = dose;
    }

    public String getAdministrationRoute() {
        return administrationRoute;
    }

    public void setAdministrationRoute(String administrationRoute) {
        this.administrationRoute = administrationRoute;
    }

    public int getTreatmentDuration() {
        return treatmentDuration;
    }

    public void setTreatmentDuration(int treatmentDuration) {
        this.treatmentDuration = treatmentDuration;
    }

    public String getAdditionalInstructions() {
        return additionalInstructions;
    }

    public void setAdditionalInstructions(String additionalInstructions) {
        this.additionalInstructions = additionalInstructions;
    }

    public int getFrecuency() {
        return frecuency;
    }

    public void setFrecuency(int frecuency) {
        this.frecuency = frecuency;
    }
    
    
    
}
