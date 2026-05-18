/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.models.user;

import java.util.HashMap;

/**
 *
 * @author edangulo
 */
public class Administrator extends User {
    
    public Administrator(long id, String username, String firstname, String lastname, String password) {
        super(id, username, firstname, lastname, password);
    }

    @Override
    public HashMap<String, Object> serialize() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    public void update(String username, String firstname, String lastname, String password){
        this.username=username;
        this.firstname=firstname;
        this.lastname=lastname;
        this.password=password;
    }
    
}
