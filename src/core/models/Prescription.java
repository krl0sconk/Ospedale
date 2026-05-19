/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
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
        presmap.put("medicationNmae", this.medicationName );
        presmap.put("administrationRoute", this.administrationRoute );
        presmap.put("treatmentDuration", this.treatmentDuration);
        presmap.put("additionalInstructions", this.additionalInstructions);
        presmap.put("frecuency", this.frecuency);
        presmap.put("dose", this.dose);

        return presmap;
    }
    
    
    
}
