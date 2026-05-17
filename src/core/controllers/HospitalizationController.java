/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.controllers;

import core.controllers.utils.HospitalizationNotFoundException;
import core.controllers.utils.Response;
import core.controllers.utils.Serializer;
import core.controllers.utils.Status;
import core.controllers.utils.Validator;
import core.models.Hospitalization;
import core.models.enums.HospitalizationStatus;
import core.models.enums.RoomType;
import core.models.storage.Storage;
import core.models.user.Doctor;
import core.models.user.Patient;
import core.models.user.User;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

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

    private static Response createHospitalization(long patientId, long doctorId, String date, String reason, RoomType roomType, String observations, HospitalizationStatus status) {
        try {
            Storage storage = Storage.getInstance();
            User patient = storage.getUserById(patientId);
            if (patient == null || !(patient instanceof Patient)) {
                return new Response("Invalid Patient id.", Status.BAD_REQUEST);
            }
            if (!Validator.isValidDate(date)) {
                return new Response("Invalid date.", Status.BAD_REQUEST);
            }

            User doctor = storage.getUserById(doctorId);
            if (doctor == null || !(doctor instanceof Doctor)) {
                return new Response("Invalid Doctor id.", Status.BAD_REQUEST);
            }

            if (reason.trim().equals("")) {
                return new Response("Reason must be declared.", Status.BAD_REQUEST);
            }

            String hospitalizationId = generateHospitalizationId(patientId);
            Hospitalization hospitalization = new Hospitalization(hospitalizationId, (Patient) patient, (Doctor) doctor, LocalDate.parse(date), reason, roomType, observations, status);
            storage.addHospitalization(hospitalization);
            return new Response("Hospitalization requested", Status.CREATED);
        } catch (Exception e) {
            return new Response("Unexpected error", Status.INTERNAL_SERVER_ERROR);

        }
    }

    //Metodos
    public static Response requestHospitalization(long patientId, long doctorId, String date, String reason, RoomType roomType, String observations) {
            return createHospitalization(patientId, doctorId, date, reason, roomType, observations, HospitalizationStatus.REQUESTED);
    }

    public static Response requestHospitalizationOngoing(long patientId, long doctorId, String date, String reason, RoomType roomType, String observations) {
        return createHospitalization(patientId, doctorId, date, reason, roomType, observations, HospitalizationStatus.ONGOING);
    }

    public static Response approveHospitalization(String hospitalizationId) {
        return changeStatus(hospitalizationId, HospitalizationStatus.REQUESTED, true, HospitalizationStatus.ONGOING, "Hospitalization approved.", "Hospitalization cannot be approved in its current state.");
    }

    public static Response denyHospitalization(String hospitalizationId) {
        return changeStatus(hospitalizationId, HospitalizationStatus.REQUESTED, true, HospitalizationStatus.CANCELED, "Hospitalization denied.", "Hospitalization cannot be denied in its current state.");
    }

    public static Response cancelHospitalization(String hospitalizationId) {
        return changeStatus(hospitalizationId, HospitalizationStatus.ONGOING, false, HospitalizationStatus.CANCELED, "Hospitalization canceled.", "Hospitalization cannot be canceled in its current state.");
    }

    public static Response getPatientHospitalizations(long patientId) {
        try {
            ArrayList<Hospitalization> result = new ArrayList<>();
            for (Hospitalization h : Storage.getInstance().getHospitalizations()) {
                if (h.getPatient().getId() == patientId) {
                    result.add(h);
                }
            }
            result.sort((a, b) -> b.getDate().compareTo(a.getDate()));
            HashMap<String, Object> data = new HashMap<>();
            data.put("list", Serializer.serializeList(result));
            return new Response("Returned patient hospitalizations.", Status.OK, data);
        } catch (Exception e) {
            return new Response("Unexpected Error.", Status.INTERNAL_SERVER_ERROR);
        }
    }
}
