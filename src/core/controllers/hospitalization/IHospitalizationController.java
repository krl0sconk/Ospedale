/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package core.controllers.hospitalization;

import core.controllers.utils.Response;
import core.models.enums.RoomType;

/**
 *
 * @author krl0s
 */
public interface IHospitalizationController {

    Response requestHospitalization(long patientId, long doctorId, String date, String reason, RoomType roomType, String observations);

    Response requestHospitalizationOngoing(long patientId, long doctorId, String date, String reason, RoomType roomType, String observations);

    Response approveHospitalization(String hospitalizationId);

    Response denyHospitalization(String hospitalizationId);

    Response cancelHospitalization(String hospitalizationId);

    Response getPatientHospitalizations(long patientId);
}
