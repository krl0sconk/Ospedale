/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.controllers.appointment;

import core.controllers.appointment.utils.AppointmentNotFoundException;
import core.controllers.utils.Response;
import core.controllers.utils.Serializer;
import core.controllers.utils.Status;
import core.controllers.utils.Validator;
import core.models.Appointment;
import core.models.Prescription;
import core.models.enums.AppointmentStatus;
import core.models.enums.Specialty;
import core.models.storage.IStorage;
import core.models.storage.Storage;
import core.models.user.doctor;
import core.models.user.patient;
import core.models.user.User;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author krl0s
 */
public class AppointmentController implements IAppointmentController{
    
    //Atributos
    private final IStorage storage;

    //Metodos internos
    private boolean checkDisponibility(long doctorId, LocalTime hour, LocalDate date) {
        doctor doctor = (doctor) this.storage.getUserById(doctorId);

        for (Appointment appointment : this.storage.getAppointments()) {
            if (appointment.getDoctor().equals(doctor) && !(appointment.getStatus().equals(AppointmentStatus.CANCELED))) {
                if (appointment.getDatetime().equals(LocalDateTime.of(date, hour))) {
                    return false;
                }
            }
        }
        return true;
    }

    private String generateAppointmentId(long patientId) {
        long count = this.storage.getAppointments().stream().filter(a -> a.getPatient().getId() == patientId).count();
        return String.format("A-%d-%04d", patientId, count);
    }

    private Appointment findAppointment(String appointmentId) throws AppointmentNotFoundException {
        Appointment appointment = this.storage.getAppointmentById(appointmentId);
        if (appointment == null) {
            throw new AppointmentNotFoundException("Appointment not found.");
        }
        return appointment;
    }

    private Response changeStatus(String appointmentId, AppointmentStatus requiredStatus, boolean mustMatch, AppointmentStatus newStatus, String successMsg, String errorMsg) {
        try {
            Appointment appointment = findAppointment(appointmentId);

            boolean conditionMet = mustMatch ? appointment.getStatus().equals(requiredStatus) : !appointment.getStatus().equals(requiredStatus);

            if (conditionMet) {
                appointment.setStatus(newStatus);
                return new Response(successMsg, Status.OK);
            }
            return new Response(errorMsg, Status.BAD_REQUEST);

        } catch (AppointmentNotFoundException e) {
            return new Response(e.getMessage(), Status.NOT_FOUND);
        } catch (Exception e) {
            return new Response("Unexpected Error.", Status.INTERNAL_SERVER_ERROR);
        }
    }

    //Metodos
    
    public AppointmentController(IStorage storage) {
        this.storage = storage;
    }
    
    @Override
    public Response requestAppointment(long patientId, long doctorId, Specialty specialty, String reason, String date, String hour) {
        try {
            boolean type = false;
            if (this.storage.getUserById(patientId) == null) {
                return new Response("Invalid Patient id.", Status.BAD_REQUEST);
            }
            if (!Validator.isValidDate(date)) {
                return new Response("Invalid date.", Status.BAD_REQUEST);
            }
            if (!Validator.isValidHour(hour)) {
                return new Response("Invalid hour.", Status.BAD_REQUEST);
            }
            doctor doctor = null;
            if (doctorId != 0) {
                User u = this.storage.getUserById(doctorId);
                if (u == null || !(u instanceof doctor)) {
                    return new Response("Invalid Doctor id.", Status.BAD_REQUEST);
                }
                doctor = (doctor) u;

                if (!checkDisponibility(doctorId, LocalTime.parse(hour), LocalDate.parse(date))) {
                    return new Response("Doctor not available.", Status.BAD_REQUEST);
                }
                if (doctor.getSpecialty() != specialty) {
                    return new Response("Specialty does not match doctor's specialty.", Status.BAD_REQUEST);
                }
                type = true;
            } else {
                for (doctor doc : this.storage.getDoctors()) {
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
            patient patient = (patient) this.storage.getUserById(patientId);
            LocalDateTime datetime = LocalDateTime.of(LocalDate.parse(date), LocalTime.parse(hour));

            this.storage.addAppointment(new Appointment(appointmentId, patient, doctor, specialty, datetime, reason, type));
            return new Response("Appointment requested successfully.", Status.CREATED);
        } catch (Exception e) {
            return new Response("Unexpected Error.", Status.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Response acceptAppointment(String appointmentId) {
        return changeStatus(appointmentId, AppointmentStatus.REQUESTED, true, AppointmentStatus.PENDING, "Appointment accepted.", "Appointment cannot be accepted in its current state.");
    }

    @Override
    public Response completeAppointment(String appointmentId) {
        return changeStatus(appointmentId, AppointmentStatus.PENDING, true, AppointmentStatus.COMPLETED, "Appointment completed.", "Appointment cannot be completed in its current state.");
    }

    @Override
    public Response cancelAppointment(String appointmentId) {
        return changeStatus(appointmentId, AppointmentStatus.COMPLETED, false, AppointmentStatus.CANCELED, "Appointment canceled.", "Appointment cannot be canceled in its current state.");
    }

    @Override
    public Response rescheduleAppointment(String appointmentId, String newHour, String reason) {
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

    @Override
    public Response prescribeMedication(String appointmentId, String medicationName, double dose, String administrationRoute, int treatmentDuration, String additionalInstructions, int frecuency) {
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

    @Override
    public Response getPatientAppointments(long patientId) {
        try {
            ArrayList<Appointment> result = new ArrayList<>();
            for (Appointment a : this.storage.getAppointments()) {
                if (a.getPatient().getId() == patientId) {
                    result.add(a);
                }
            }
            result.sort((a, b) -> b.getDatetime().compareTo(a.getDatetime()));
            HashMap<String, Object> data = new HashMap<>();
            data.put("list", Serializer.serializeList(result));
            return new Response("Returned patient appointments.", Status.OK, data);
        } catch (Exception e) {
            return new Response("Unexpected Error.", Status.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Response getDoctorAppointments(long doctorId, boolean pendingOnly) {
        try {
            ArrayList<Appointment> result = new ArrayList<>();
            for (Appointment a : this.storage.getAppointments()) {
                if (a.getDoctor().getId() == doctorId) {
                    if (pendingOnly) {
                        if (a.getStatus().equals(AppointmentStatus.PENDING)) {
                            result.add(a);
                        }
                    } else {
                        result.add(a);
                    }
                }
            }
            result.sort((a, b) -> b.getDatetime().compareTo(a.getDatetime()));
            HashMap<String, Object> data = new HashMap<>();
            data.put("list", Serializer.serializeList(result));
            return new Response("Returned doctor appointmetns.", Status.OK, data);
        } catch (Exception e) {
            return new Response("Unexpected Error.", Status.INTERNAL_SERVER_ERROR);
        }
    }
}
