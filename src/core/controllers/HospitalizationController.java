/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.controllers;

import core.controllers.utils.HospitalizationNotFoundException;
import core.controllers.utils.Response;
import core.controllers.utils.Status;
import core.models.Hospitalization;
import core.models.enums.HospitalizationStatus;
import core.models.enums.RoomType;
import core.models.storage.Storage;

/**
 *
 * @author krl0s
 */
public class HospitalizationController {

    //Metodos internos
    private static Hospitalization findHospitalization(String hospitalizationId) throws HospitalizationNotFoundException {
        Hospitalization hospitalization = Storage.getInstance().getHospitalizationById(hospitalizationId);
        if (hospitalization == null) {
            throw new HospitalizationNotFoundException("Hospitalization not found.");
        }
        return hospitalization;
    }

    private static String generateHospitalizationId(long patientId) {
        long count = Storage.getInstance().getHospitalizations().stream().filter(a -> a.getPatient().getId() == patientId).count();
        return String.format("H-%d-%04d", patientId, count);
    }
    
        private static Response changeStatus(String hospitalizationId, HospitalizationStatus requiredStatus, boolean mustMatch, HospitalizationStatus newStatus, String successMsg, String errorMsg) {
        try {
            Hospitalization hospitalization = findHospitalization(hospitalizationId);

            boolean conditionMet = mustMatch ? hospitalization.getStatus().equals(requiredStatus) : !hospitalization.getStatus().equals(requiredStatus);

            if (conditionMet) {
                hospitalization.setStatus(newStatus);
                return new Response(successMsg, Status.OK);
            }
            return new Response(errorMsg, Status.BAD_REQUEST);

        } catch (HospitalizationNotFoundException e) {
            return new Response(e.getMessage(), Status.NOT_FOUND);
        } catch (Exception e) {
            return new Response("Unexpected Error.", Status.INTERNAL_SERVER_ERROR);
        }
    }

    //Metodos
    public static Response requestHospitalization(long patientId, long doctorId, String date, String reason, RoomType roomType, String observations) {
        try {
            Storage storage = Storage.getInstance();
            
        } catch (Exception e) {
            return new Response("Unexpected error", Status.INTERNAL_SERVER_ERROR);

        }
        return null;
    }
}
