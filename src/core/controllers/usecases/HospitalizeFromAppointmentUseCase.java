/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.controllers.usecases;

import core.controllers.appointment.AppointmentController;
import core.controllers.HospitalizationController;
import core.controllers.utils.Response;
import core.controllers.utils.Status;
import core.models.Appointment;
import core.models.enums.RoomType;
import core.models.storage.Storage;

/**
 *
 * @author krl0s
 */
public class HospitalizeFromAppointmentUseCase {
    public static Response execute(String appointmentId, String date, String reason, RoomType roomType, String observations) {
        Response completeResponse = AppointmentController.completeAppointment(appointmentId);
        if (completeResponse.getStatus() != Status.OK) return completeResponse;
        
        Appointment appointment = Storage.getInstance().getAppointmentById(appointmentId);
        return HospitalizationController.requestHospitalizationOngoing(appointment.getPatient().getId(), appointment.getDoctor().getId(), date, reason, roomType, observations
        );
    }
}
