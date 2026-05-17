/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.controllers.utils;

import java.time.LocalDate;

/**
 *
 * @author krl0s
 */
public class Validator {
    //Validaciones
    public static boolean isValidDate(String date) {
        if (!date.matches("\\d{4}-\\d{2}-\\d{2}")) {
            return false;
        }
        try {
            LocalDate.parse(date);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isValidHour(String hour) {
        if (!hour.matches("([01]\\d|2[0-3]):(00|15|30|45)")) {
            return false;
        }
        return true;
    }
    
    public static boolean isValidId(long id) {
        if (id < 0 || String.valueOf(Math.abs(id)).length() != 12) {
            return false;
        }
        return true;
    }

    public static boolean isValidEmail(String email) {
        if (!email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.com$")) {
            return false;
        }
        return true;
    }

    public static boolean isValidPhone(long phone) {
        if (phone <= 0 || String.valueOf(Math.abs(phone)).length() != 10) {
            return false;
        }
        return true;
    }

    public static boolean isValidLicence(String licence) {
        if (!licence.matches("L-\\d{10} MTL")) {
            return false;
        }
        return true;
    }

    public static boolean isValidOffice(String office) {
        if (!office.matches("O-\\d{3}")) {
            return false;
        }
        return true;
    }
}
