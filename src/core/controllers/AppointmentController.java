/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.controllers;

import core.controllers.utils.Response;
import core.controllers.utils.Status;
import core.models.Appointment;
import core.models.enums.Specialty;
import core.models.storage.Storage;
import core.models.user.Doctor;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 *
 * @author krl0s
 */
public class AppointmentController {

    //Validaciones
    private static boolean isValidDate(String date) {
        if (!date.matches("\\d{4}-\\d{2}-\\d{2}")) {
            return false;
        }
        try {
            LocalDate.parse(date);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private static boolean isValidHour(String hour) {
        if (!hour.matches("([01]\\d|2[0-3]):(00|15|30|45)")) {
            return false;
        }
        return true;
    }

    private static boolean checkDisponibility(long doctorId, String hour, String date) {
        Storage storage = Storage.getInstance();
        Doctor doctor = (Doctor) storage.getUserById(doctorId);
        
        for (Appointment appointment : doctor.getAppointments()) {
            if (appointment.getDatetime() == LocalDateTime.parse(date+hour) &&) {
                return false;
            }
        }
        return true;
    }

    //Metodos
    public static Response requestAppointment(long patientId, long doctorId, Specialty specialty, String date, String hour) {
        try {
            Storage storage = Storage.getInstance();
            if (storage.getUserById(patientId) == null) {
                return new Response("Invalid Patient id.", Status.BAD_REQUEST);
            }
            if (!isValidDate(date)) {
                return new Response("Invalid date.", Status.BAD_REQUEST);
            }
            if (!isValidHour(hour)) {
                return new Response("Invalid hour.", Status.BAD_REQUEST);
            }
            if (storage.getUserById(doctorId) == null || !(storage.getUserById(doctorId) instanceof Doctor)) {
                return new Response("Invalid Doctor id.", Status.BAD_REQUEST);
            }

        } catch (Exception e) {
            return new Response("Unexpected Error", Status.INTERNAL_SERVER_ERROR);
        }
        return null;
    }
}
