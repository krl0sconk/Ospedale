/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.controllers;

import core.controllers.utils.AppointmentNotFoundException;
import core.controllers.utils.Response;
import core.controllers.utils.Status;
import core.controllers.utils.Validator;
import core.models.Appointment;
import core.models.Prescription;
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

    //Metodos internos
    private static boolean checkDisponibility(long doctorId, LocalTime hour, LocalDate date) {
        Storage storage = Storage.getInstance();
        Doctor doctor = (Doctor) storage.getUserById(doctorId);

        for (Appointment appointment : storage.getAppointments()) {
            if (appointment.getDoctor().equals(doctor) && !(appointment.getStatus().equals(AppointmentStatus.CANCELED))) {
                if (appointment.getDatetime().equals(LocalDateTime.of(date, hour))) {
                    return false;
                }
            }
        }
        return true;
    }

    private static String generateAppointmentId(long patientId) {
        long count = Storage.getInstance().getAppointments().stream()
                .filter(a -> a.getPatient().getId() == patientId)
                .count();
        return String.format("A-%d-%04d", patientId, count);
    }

    private static Appointment findAppointment(String appointmentId) throws AppointmentNotFoundException {
        Appointment appointment = Storage.getInstance().getAppointmentById(appointmentId);
        if (appointment == null) {
            throw new AppointmentNotFoundException("Appointment not found.");
        }
        return appointment;
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

                if (!checkDisponibility(doctorId, LocalTime.parse(hour), LocalDate.parse(date))) {
                    return new Response("Doctor not available.", Status.BAD_REQUEST);
                }
                doctor = (Doctor) storage.getUserById(doctorId);
                if (doctor.getSpecialty() != specialty) {
                    return new Response("Specialty does not match doctor's specialty.", Status.BAD_REQUEST);
                }
                type = true;
            } else {
                for (Doctor doc : storage.getDoctors()) {
                    if (doc.getSpecialty() == specialty && checkDisponibility(doc.getId(), LocalTime.parse(hour), LocalDate.parse(date))) {
                        doctor = doc;
                        break;
                    }
                }
                if (doctor == null) {
                    return new Response("No available doctor for that specialty.", Status.BAD_REQUEST);
                }
            }

            String appointmentId = generateAppointmentId(patientId);
            Patient patient = (Patient) storage.getUserById(patientId);
            LocalDateTime datetime = LocalDateTime.of(LocalDate.parse(date), LocalTime.parse(hour));

            storage.addAppointment(new Appointment(appointmentId, patient, doctor, specialty, datetime, reason, type));
            return new Response("Appointment requested successfully.", Status.CREATED);
        } catch (Exception e) {
            return new Response("Unexpected Error.", Status.INTERNAL_SERVER_ERROR);
        }
    }

    public static Response acceptAppointment(String appointmentId) {
        try {
            Appointment appointment = findAppointment(appointmentId);
            if (appointment.getStatus().equals(AppointmentStatus.REQUESTED)) {
                appointment.setStatus(AppointmentStatus.PENDING);
                return new Response("Appointment accepted.", Status.OK);
            }
            return new Response("Appointment cannot be accepted in its current state.", Status.BAD_REQUEST);
        } catch (AppointmentNotFoundException e) {
            return new Response(e.getMessage(), Status.NOT_FOUND);
        } catch (Exception e) {
            return new Response("Unexpected Error.", Status.INTERNAL_SERVER_ERROR);
        }
    }

    public static Response completeAppointment(String appointmentId) {
        try {
            Appointment appointment = findAppointment(appointmentId);
            if (appointment.getStatus().equals(AppointmentStatus.PENDING)) {
                appointment.setStatus(AppointmentStatus.COMPLETED);
                return new Response("Appointment completed.", Status.OK);
            }
            return new Response("Appointment cannot be completed in its current state.", Status.BAD_REQUEST);
        } catch (AppointmentNotFoundException e) {
            return new Response(e.getMessage(), Status.NOT_FOUND);
        } catch (Exception e) {
            return new Response("Unexpected Error.", Status.INTERNAL_SERVER_ERROR);
        }
    }

    public static Response cancelAppointment(String appointmentId) {
        try {
            Appointment appointment = findAppointment(appointmentId);
            if (!appointment.getStatus().equals(AppointmentStatus.COMPLETED)) {
                appointment.setStatus(AppointmentStatus.CANCELED);
                return new Response("Appointment canceled.", Status.OK);
            }
            return new Response("Appointment cannot be canceled in its current state.", Status.BAD_REQUEST);
        } catch (AppointmentNotFoundException e) {
            return new Response(e.getMessage(), Status.NOT_FOUND);
        } catch (Exception e) {
            return new Response("Unexpected Error.", Status.INTERNAL_SERVER_ERROR);
        }
    }

    public static Response rescheduleAppointment(String appointmentId, String newHour, String reason) {
        try {
            Appointment appointment = findAppointment(appointmentId);
            if (appointment.getStatus().equals(AppointmentStatus.PENDING)) {
                if (!Validator.isValidHour(newHour)) {
                    return new Response("Hour invalid.", Status.BAD_REQUEST);
                }
                if (!checkDisponibility(appointment.getDoctor().getId(), LocalTime.parse(newHour), appointment.getDatetime().toLocalDate())) {
                    return new Response("No disponibility.", Status.BAD_REQUEST);
                }
                LocalDateTime newDatetime = LocalDateTime.of(appointment.getDatetime().toLocalDate(), LocalTime.parse(newHour));
                appointment.setDatetime(newDatetime);
                appointment.setReason(appointment.getReason() + " RESCHEDULED:" + reason);
                return new Response("Appointment rescheduled.", Status.OK);
            }
            return new Response("Appointment cannot be rescheduled in its current state.", Status.BAD_REQUEST);
        } catch (AppointmentNotFoundException e) {
            return new Response(e.getMessage(), Status.NOT_FOUND);
        } catch (Exception e) {
            return new Response("Unexpected Error.", Status.INTERNAL_SERVER_ERROR);
        }
    }

    public static Response prescribeMedication(String appointmentId, String medicationName, double dose, String administrationRoute, int treatmentDuration, String additionalInstructions, int frecuency) {
        try {
            Appointment appointment = findAppointment(appointmentId);
            if (!appointment.getStatus().equals(AppointmentStatus.PENDING)) {
                return new Response("Medication cannot be prescribed in appointment's current state.", Status.BAD_REQUEST);
            }
            Prescription pre = new Prescription(appointment, medicationName, dose, administrationRoute, treatmentDuration, additionalInstructions, frecuency);
            appointment.addPrescription(pre);
            return new Response("Medication prescribed.", Status.CREATED);
        } catch (AppointmentNotFoundException e) {
            return new Response(e.getMessage(), Status.NOT_FOUND);
        } catch (Exception e) {
            return new Response("Unexpected Error.", Status.INTERNAL_SERVER_ERROR);
        }
    }
}
