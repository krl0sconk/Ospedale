/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package core.controllers.user;

import core.controllers.utils.Response;
import core.models.enums.Specialty;

/**
 *
 * @author krl0s
 */
public interface IUserController {

    Response registerPatient(String firstname, String lastname, long id, boolean gender, String birthdate, String address, long phone, String email, String username, String password, String passwordConfirmation);

    Response updatePatient(String firstname, String lastname, long id, boolean gender, String birthdate, String address, long phone, String email, String username, String password, String passwordConfirmation);

    Response registerDoctor(String firstname, String lastname, long id, String username, String password, String passwordConfirmation, String licenceNumber, Specialty specialty, String assignedOffice);

    Response updateDoctor(String firstname, String lastname, long id, String username, String password, String passwordConfirmation, String licenceNumber, Specialty specialty, String assignedOffice);

    Response getPatients();

    Response getDoctors();

}
