/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.controllers.usecases;

import core.controllers.appointment.IAppointmentController;
import core.controllers.hospitalization.IHospitalizationController;
import core.controllers.utils.Response;
import core.controllers.utils.Status;
import core.models.Appointment;
import core.models.enums.RoomType;
import core.models.storage.IStorage;

/**
 *
 * @author krl0s
 */
public class HospitalizeFromAppointmentUseCase {

    private final IAppointmentController appointmentController;
    private final IHospitalizationController hospitalizationController;
    private final IStorage storage;

    public HospitalizeFromAppointmentUseCase(IAppointmentController appointmentController, IHospitalizationController hospitalizationController, IStorage storage) {
        this.appointmentController = appointmentController;
        this.hospitalizationController = hospitalizationController;
        this.storage = storage;
    }

    public Response execute(String appointmentId, String date, String reason, RoomType roomType, String observations) {
        Response completeResponse = this.appointmentController.completeAppointment(appointmentId);
        if (completeResponse.getStatus() != Status.OK) {
            return completeResponse;
        }

        Appointment appointment = this.storage.getAppointmentById(appointmentId);
        return this.hospitalizationController.requestHospitalizationOngoing(
                appointment.getPatient().getId(),
                appointment.getDoctor().getId(),
                date, reason, roomType, observations
        );
    }
}
