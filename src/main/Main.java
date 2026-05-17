/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import core.controllers.appointment.AppointmentController;
import core.controllers.appointment.IAppointmentController;
import core.controllers.hospitalization.HospitalizationController;
import core.controllers.hospitalization.IHospitalizationController;
import core.controllers.login.ILoginController;
import core.controllers.login.LoginController;
import core.controllers.usecases.HospitalizeFromAppointmentUseCase;
import core.controllers.user.IUserController;
import core.controllers.user.UserController;
import core.models.storage.IStorage;
import core.models.storage.Storage;

/**
 *
 * @author krl0s
 */
public class Main {

    public static void main(String[] args) {
        IStorage storage = Storage.getInstance();

        ILoginController loginController = new LoginController(storage);
        IUserController userController = new UserController(storage);
        IAppointmentController appointmentController = new AppointmentController(storage);
        IHospitalizationController hospitalizationController = new HospitalizationController(storage);
        HospitalizeFromAppointmentUseCase useCase = new HospitalizeFromAppointmentUseCase(appointmentController, hospitalizationController, storage);
    }
}
