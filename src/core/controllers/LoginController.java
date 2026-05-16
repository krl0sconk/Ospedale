/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.controllers;

import core.controllers.utils.Response;
import core.controllers.utils.Status;
import core.models.storage.Storage;
import core.models.user.User;
import java.util.HashMap;

/**
 *
 * @author krl0s
 */
public class LoginController {
    
    //Metodos
    public static Response loginUser(String username, String password) {
        try {
            Storage storage = Storage.getInstance();

            if (username.trim().equals("")) {
                return new Response("Username must not be empty.", Status.BAD_REQUEST);
            }
            
            if (password.trim().equals("")) {
                return new Response("Password must not be empty.", Status.BAD_REQUEST);
            }

            try {
                User user = storage.getUserByUsername(username);
                
                if (!user.getPassword().equals(password.trim())) {
                    return new Response("Password incorrect", Status.BAD_REQUEST);
                }
                
                //TODO: Serializacion (Migue)
                return new Response("Password correct", Status.OK, user.serialize());
            } catch (Exception e) {
                return new Response("Username not associated to any user.", Status.NOT_FOUND);
            }

        } catch (Exception e) {
            return new Response("Unexpected error", Status.INTERNAL_SERVER_ERROR);
        }
    }
}
