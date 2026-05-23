/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/*
 * Archivo: ISerializable.java
 * Propósito: Contrato que obliga a las entidades a exponer `serialize()`.
 * Relacionado con: todas las entidades que necesitan serialización (usuarios, citas, hospitalizaciones, prescripciones).
 * Impacto SOLID:
 *  - ISP: interfaz pequeña y enfocada (cumple ISP).
 *  - DIP: permite depender de abstracciones para serializar datos.
 */
package core.models;

import java.util.HashMap;

/**
 *
 * @author migue
 */
public interface ISerializable {
    
    HashMap<String, Object> serialize();
    
}
