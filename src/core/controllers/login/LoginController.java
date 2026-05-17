/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.controllers.login;

import core.controllers.utils.Response;
import core.controllers.utils.Status;
import core.models.storage.IStorage;
import core.models.storage.Storage;
import core.models.user.User;
import java.util.HashMap;

/**
 *
 * @author krl0s
 */
public class LoginController implements ILoginController {
    
    //Atributos
    private final IStorage storage;


    //Metodos
    
    public LoginController(IStorage storage) {
        this.storage = storage;
    }
    
    public Response loginUser(String username, String password) {
        try {
            if (username.trim().equals("")) {
                return new Response("Username must not be empty.", Status.BAD_REQUEST);
            }

            if (password.trim().equals("")) {
                return new Response("Password must not be empty.", Status.BAD_REQUEST);
            }

            User user = this.storage.getUserByUsername(username);
            if (user == null) {
                return new Response("User not found.", Status.NOT_FOUND);
            }
            if (!user.getPassword().equals(password.trim())) {
                return new Response("Password incorrect", Status.BAD_REQUEST);
            }

            //TODO: Serializacion (Migue)
            return new Response("Loged in.", Status.OK, user.serialize());

        } catch (Exception e) {
            return new Response("Unexpected error", Status.INTERNAL_SERVER_ERROR);
        }
    }
}
