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
        HashMap<String, Object> admap=new HashMap<>();
        admap.put("id", this.id );
        admap.put("username", this.username);
        admap.put("firstname", this.firstname);
        admap.put("lastname", this.lastname);
        admap.put("userType", "admin" );
       
        return admap;
    }
    
    public void update(String username, String firstname, String lastname, String password){
        this.username=username;
        this.firstname=firstname;
        this.lastname=lastname;
        this.password=password;
    }
    
    
    
}
