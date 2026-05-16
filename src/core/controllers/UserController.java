/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.controllers;

import core.controllers.utils.Response;
import core.controllers.utils.Status;
import core.models.storage.Storage;
import java.time.LocalDate;

/**
 *
 * @author krl0s
 */
public class UserController {

    //Validaciones
    private static boolean isValidId(long id) {
        Storage storage = Storage.getInstance();
        if (storage.getUserById(id) != null) {
            // TODO: Cambiara a un try catch posiblemente.
            return false;
        }
        if (id < 0 || String.valueOf(Math.abs(id)).length() != 12) {
            return false;
        }
        return true;
    }

    private static boolean isValidEmail(String email) {
        if (!email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.com$")) {
            return false;
        }
        return true;
    }

    private static boolean isValidPhone(long phone) {
        if (phone < 0 || String.valueOf(Math.abs(phone)).length() != 10) {
            return false;
        }
        return true;
    }

    private static boolean isValidDate(LocalDate birthdate) {
        return false;
    }

    //Metodos
    public static Response registerPatient(String firstname, String lastname, long id, boolean gender, LocalDate birthdate, String address, long phone, String email, String username, String password, String passwordConfirmation) {
        try {
            Storage storage = Storage.getInstance();

            if (firstname.trim().equals("") || lastname.trim().equals("") || address.trim().equals("")) {
                return new Response("All fields are required", Status.BAD_REQUEST);
            }
            
            if (!isValidId(id)) {
                return new Response("Invalid id.", Status.BAD_REQUEST);
            }
            if (!isValidEmail(email)) {
                return new Response("Invalid email.", Status.BAD_REQUEST);
            }
            if (!isValidPhone(phone)) {
                return new Response("Invalid phone.", Status.BAD_REQUEST);
            }

        } catch (Exception e) {
            return new Response("Unexpected error.", Status.INTERNAL_SERVER_ERROR);
        }
        return null;
    }
}
