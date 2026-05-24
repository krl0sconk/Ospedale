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
        return hour.matches("([01]\\d|2[0-3]):(00|15|30|45)");
    }
    public static boolean isValidId(long id) {
        return id > 0 && String.valueOf(id).length() == 12;
    }

    public static boolean isValidEmail(String email) {
        return email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.com$");
    }

    public static boolean isValidPhone(long phone) {
        return phone > 0 && String.valueOf(phone).length() == 10;
    }

    public static boolean isValidLicence(String licence) {
        return licence.matches("L-\\d{10} MTL");
    }

    public static boolean isValidOffice(String office) {
        return office.matches("O-\\d{3}");
    }

}
