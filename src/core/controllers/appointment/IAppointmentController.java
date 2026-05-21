/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package core.controllers.appointment;

import core.controllers.utils.Response;
import core.models.enums.Specialty;

/**
 *
 * @author krl0s
 */
public interface IAppointmentController {

    Response requestAppointment(long patientId, long doctorId, Specialty specialty, String reason, String date, String hour);

    Response acceptAppointment(String appointmentId);

    Response completeAppointment(String appointmentId);

    Response cancelAppointment(String appointmentId);

    Response rescheduleAppointment(String appointmentId, String newHour, String reason);

    Response prescribeMedication(String appointmentId, String medicationName, double dose, String administrationRoute, int treatmentDuration, String additionalInstructions, int frecuency);

    Response getPatientAppointments(long patientId);

    Response getDoctorAppointments(long doctorId, boolean pendingOnly);
}
