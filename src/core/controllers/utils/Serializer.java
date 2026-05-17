/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.controllers.utils;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author krl0s
 */
public class Serializer {

    public static ArrayList<HashMap<String, Object>> serializeList(ArrayList<?> list) {
        ArrayList<HashMap<String, Object>> result = new ArrayList<>();
        for (Object item : list) {
            // TODO: Migue ISerializable
            result.add(((ISerializable) item).serialize());
        }
        return result;
    }
}
