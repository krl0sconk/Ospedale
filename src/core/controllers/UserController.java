/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.controllers;

import core.controllers.utils.Response;
import core.controllers.utils.Serializer;
import core.controllers.utils.Status;
import core.controllers.utils.Validator;
import core.models.enums.Specialty;
import core.models.storage.Storage;
import core.models.user.Doctor;
import core.models.user.Patient;
import core.models.user.User;
import java.time.LocalDate;
import java.util.HashMap;

/**
 *
 * @author krl0s
 */
public class UserController {

    //Metodos internos
    private static Response validateCommonFields(long id, String username, String password, String confirmation, Storage storage) {
        if (!Validator.isValidId(id)) {
            return new Response("Invalid id.", Status.BAD_REQUEST);
        }

        User existingUsername = storage.getUserByUsername(username);
        if (existingUsername != null && storage.getUserById(id) != existingUsername) {
            return new Response("Username already exists.", Status.BAD_REQUEST);
        }

        if (!password.equals(confirmation)) {
            return new Response("Passwords do not match.", Status.BAD_REQUEST);
        }
        return null;
    }

    //Metodos
    //Pacientes
    public static Response registerPatient(String firstname, String lastname, long id, boolean gender, String birthdate, String address, long phone, String email, String username, String password, String passwordConfirmation) {
        try {
            Storage storage = Storage.getInstance();

            if (firstname.trim().equals("") || lastname.trim().equals("") || address.trim().equals("")) {
                return new Response("All fields are required", Status.BAD_REQUEST);
            }

            Response validation = validateCommonFields(id, username, password, passwordConfirmation, storage);
            if (validation != null) {
                return validation;
            }
            if (!Validator.isValidEmail(email)) {
                return new Response("Invalid email.", Status.BAD_REQUEST);
            }
            if (!Validator.isValidPhone(phone)) {
                return new Response("Invalid phone.", Status.BAD_REQUEST);
            }
            if (!Validator.isValidDate(birthdate)) {
                return new Response("Invalid date.", Status.BAD_REQUEST);
            }

            if (!storage.addUser(new Patient(id, username, firstname, lastname, password, email, LocalDate.parse(birthdate), gender, phone, address))) {
                return new Response("A Patient with that id already exists", Status.BAD_REQUEST);
            }

            return new Response("Patient registered succesfully.", Status.CREATED);

        } catch (Exception e) {
            return new Response("Unexpected error.", Status.INTERNAL_SERVER_ERROR);
        }
    }

    public static Response updatePatient(String firstname, String lastname, long id, boolean gender, String birthdate, String address, long phone, String email, String username, String password, String passwordConfirmation) {
        try {
            Storage storage = Storage.getInstance();

            if (firstname.trim().equals("") || lastname.trim().equals("") || address.trim().equals("")) {
                return new Response("All fields are required", Status.BAD_REQUEST);
            }

            Response validation = validateCommonFields(id, username, password, passwordConfirmation, storage);
            if (validation != null) {
                return validation;
            }
            if (storage.getUserById(id) == null) {
                return new Response("Patient not found.", Status.NOT_FOUND);
            }
            if (!Validator.isValidEmail(email)) {
                return new Response("Invalid email.", Status.BAD_REQUEST);
            }
            if (!Validator.isValidPhone(phone)) {
                return new Response("Invalid phone.", Status.BAD_REQUEST);
            }
            if (!Validator.isValidDate(birthdate)) {
                return new Response("Invalid date.", Status.BAD_REQUEST);
            }

            storage.updatePatient(id, username, firstname, lastname, password, email, LocalDate.parse(birthdate), gender, phone, address);
            return new Response("Patient updated succesfully.", Status.OK);

        } catch (Exception e) {
            return new Response("Unexpected error.", Status.INTERNAL_SERVER_ERROR);
        }
    }

    //Doctores
    public static Response registerDoctor(String firstname, String lastname, long id, String username, String password, String passwordConfirmation, String licenceNumber, Specialty specialty, String assignedOffice) {
        try {
            Storage storage = Storage.getInstance();

            if (firstname.trim().equals("") || lastname.trim().equals("")) {
                return new Response("All fields are required", Status.BAD_REQUEST);
            }

            Response validation = validateCommonFields(id, username, password, passwordConfirmation, storage);
            if (validation != null) {
                return validation;
            }
            if (!Validator.isValidLicence(licenceNumber)) {
                return new Response("Invalid licence.", Status.BAD_REQUEST);
            }
            if (!Validator.isValidOffice(assignedOffice)) {
                return new Response("Invalid Office.", Status.BAD_REQUEST);
            }

            if (!storage.addUser(new Doctor(id, username, firstname, lastname, password, specialty, licenceNumber, assignedOffice))) {
                return new Response("A Doctor with that id already exists", Status.BAD_REQUEST);
            }
            return new Response("Doctor registered succesfully.", Status.CREATED);

        } catch (Exception e) {
            return new Response("Unexpected error.", Status.INTERNAL_SERVER_ERROR);
        }
    }

    public static Response updateDoctor(String firstname, String lastname, long id, String username, String password, String passwordConfirmation, String licenceNumber, Specialty specialty, String assignedOffice) {
        try {
            Storage storage = Storage.getInstance();

            if (firstname.trim().equals("") || lastname.trim().equals("")) {
                return new Response("All fields are required", Status.BAD_REQUEST);
            }

            Response validation = validateCommonFields(id, username, password, passwordConfirmation, storage);
            if (validation != null) {
                return validation;
            }
            if (storage.getUserById(id) == null) {
                return new Response("Patient not found.", Status.NOT_FOUND);
            }
            if (!Validator.isValidLicence(licenceNumber)) {
                return new Response("Invalid licence.", Status.BAD_REQUEST);
            }
            if (!Validator.isValidOffice(assignedOffice)) {
                return new Response("Invalid Office.", Status.BAD_REQUEST);
            }

            storage.updateDoctor(id, username, firstname, lastname, password, specialty, licenceNumber, assignedOffice);
            return new Response("Doctor updated succesfully.", Status.OK);

        } catch (Exception e) {
            return new Response("Unexpected error.", Status.INTERNAL_SERVER_ERROR);
        }
    }

    public static Response getPatients() {
        try {
            HashMap<String, Object> data = new HashMap<>();
            data.put("list", Serializer.serializeList(Storage.getInstance().getPatients()));
            return new Response("Returned patients.", Status.OK);
        } catch (Exception e) {
            return new Response("Unexpected error.", Status.INTERNAL_SERVER_ERROR);
        }
    }

    public static Response getDoctors() {
        try {
            HashMap<String, Object> data = new HashMap<>();
            data.put("list", Serializer.serializeList(Storage.getInstance().getDoctors()));
            return new Response("Returned doctors.", Status.OK);
        } catch (Exception e) {
            return new Response("Unexpected error.", Status.INTERNAL_SERVER_ERROR);
        }
    }
}
