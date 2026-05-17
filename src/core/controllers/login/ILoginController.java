/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package core.controllers.login;

import core.controllers.utils.Response;

/**
 *
 * @author krl0s
 */
public interface ILoginController {
    Response loginUser(String username, String password);
}
