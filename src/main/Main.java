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
import core.models.events.EventBus;
import core.models.events.ModelEventBus;
import core.models.services.AppointmentService;
import core.models.services.HospitalizationService;
import core.models.services.UserService;
import core.models.storage.IAppointmentRepository;
import core.models.storage.IHospitalizationRepository;
import core.models.storage.IStorage;
import core.models.storage.IUserRepository;
import core.models.storage.Storage;

/**
 *
 * @author krl0s
 */
public class Main {

    public static void main(String[] args) {
        IStorage storage = Storage.getInstance();

        EventBus bus = ModelEventBus.getInstance();
        UserService userService = new UserService((IUserRepository) storage, bus);
        AppointmentService appointmentService = new AppointmentService((IAppointmentRepository) storage, bus);
        HospitalizationService hospitalizationService = new HospitalizationService((IHospitalizationRepository) storage, bus);

        ILoginController loginController = new LoginController(userService);
        IUserController userController = new UserController(userService);
        IAppointmentController appointmentController = new AppointmentController(appointmentService, userService);
        IHospitalizationController hospitalizationController = new HospitalizationController(hospitalizationService, userService);
        HospitalizeFromAppointmentUseCase useCase = new HospitalizeFromAppointmentUseCase(appointmentController, hospitalizationController, appointmentService);
    }
}
