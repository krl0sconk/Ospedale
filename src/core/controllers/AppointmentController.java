/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.controllers;

import core.controllers.utils.Response;
import core.controllers.utils.Status;
import core.controllers.utils.Validator;
import core.models.Appointment;
import core.models.enums.AppointmentStatus;
import core.models.enums.Specialty;
import core.models.storage.Storage;
import core.models.user.Doctor;
import core.models.user.Patient;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 *
 * @author krl0s
 */
public class AppointmentController {

    private static boolean checkDisponibility(long doctorId, String hour, String date) {
        Storage storage = Storage.getInstance();
        Doctor doctor = (Doctor) storage.getUserById(doctorId);

        for (Appointment appointment : storage.getAppointments()) {
            if (appointment.getDoctor().equals(doctor) && !(appointment.getStatus().equals(AppointmentStatus.CANCELED))) {
                if (appointment.getDatetime().equals(LocalDateTime.of(LocalDate.parse(date), LocalTime.parse(hour)))) {
                    return false;
                }
            }
        }
        return true;
    }

    //Metodos
    public static Response requestAppointment(long patientId, long doctorId, Specialty specialty, String reason, String date, String hour) {
        try {
            Storage storage = Storage.getInstance();
            boolean type = false;
            if (storage.getUserById(patientId) == null) {
                return new Response("Invalid Patient id.", Status.BAD_REQUEST);
            }
            if (!Validator.isValidDate(date)) {
                return new Response("Invalid date.", Status.BAD_REQUEST);
            }
            if (!Validator.isValidHour(hour)) {
                return new Response("Invalid hour.", Status.BAD_REQUEST);
            }
            Doctor doctor = null;
            if (doctorId != 0) {
                if (storage.getUserById(doctorId) == null || !(storage.getUserById(doctorId) instanceof Doctor)) {
                    return new Response("Invalid Doctor id.", Status.BAD_REQUEST);
                }

                if (!checkDisponibility(doctorId, hour, date)) {
                    return new Response("Doctor not available.", Status.BAD_REQUEST);
                }
                doctor = (Doctor) storage.getUserById(doctorId);
                if (doctor.getSpecialty() != specialty) {
                    return new Response("Specialty does not match doctor's specialty.", Status.BAD_REQUEST);
                }
                type = true;
            } else {
                for (Doctor doc : storage.getDoctors()) {
                    if (doc.getSpecialty() == specialty && checkDisponibility(doc.getId(), hour, date)) {
                        doctor = doc;
                        break;
                    }
                }
                if (doctor == null) {
                    return new Response("No available doctor for that specialty.", Status.BAD_REQUEST);
                }
            }
            long count = storage.getAppointments().stream()
                    .filter(a -> a.getPatient().getId() == patientId)
                    .count();
            String appointmentId = String.format("A-%d-%04d", patientId, count);

            Patient patient = (Patient) storage.getUserById(patientId);

            LocalDateTime datetime = LocalDateTime.of(LocalDate.parse(date), LocalTime.parse(hour));
            storage.addAppointment(new Appointment(appointmentId, patient, doctor, specialty, datetime, reason, type));
            return new Response("Appointment requested successfully.", Status.CREATED);
        } catch (Exception e) {
            return new Response("Unexpected Error", Status.INTERNAL_SERVER_ERROR);
        }
    }
}
