package core.models.storage;

import core.models.enums.Specialty;
import core.models.user.Administrator;
import core.models.user.doctor;
import core.models.user.patient;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import org.json.JSONArray;
import org.json.JSONObject;

public final class JsonUserLoader {

    private JsonUserLoader() {
    }

    public static void loadUsersInto(Storage storage, String filePath) throws Exception {
        String content = Files.readString(Path.of(filePath));
        JSONObject root = new JSONObject(content);
        JSONArray usersArray = root.getJSONArray("users");

        for (int i = 0; i < usersArray.length(); i++) {
            JSONObject userJson = usersArray.getJSONObject(i);
            String type = userJson.getString("type");
            long id = userJson.getLong("id");
            String username = userJson.getString("username");
            String firstname = userJson.getString("firstname");
            String lastname = userJson.getString("lastname");
            String password = userJson.getString("password");

            if ("admin".equalsIgnoreCase(type)) {
                storage.addUser(new Administrator(id, username, firstname, lastname, password));
                continue;
            }

            if ("patient".equalsIgnoreCase(type)) {
                String email = userJson.getString("email");
                LocalDate birthdate = LocalDate.parse(userJson.getString("birthdate"));
                boolean gender = userJson.getBoolean("gender");
                long phone = userJson.getLong("phone");
                String address = userJson.getString("address");
                storage.addUser(new patient(id, username, firstname, lastname, password, email, birthdate, gender, phone, address));
                continue;
            }

            if ("doctor".equalsIgnoreCase(type)) {
                Specialty specialty = mapSpecialty(userJson.getString("specialty"));
                String licenceNumber = userJson.getString("licenceNumber");
                String assignedOffice = userJson.getString("assignedOffice");
                storage.addUser(new doctor(id, username, firstname, lastname, password, specialty, licenceNumber, assignedOffice));
            }
        }
    }

    private static Specialty mapSpecialty(String specialtyName) {
        if (specialtyName == null) {
            return null;
        }

        return switch (specialtyName.trim().toUpperCase()) {
            case "ORTHOPEDICS" -> Specialty.TRAUMATOLOGY_ORTHOPEDICS;
            case "GYNECOLOGY" -> Specialty.GYNECOLOGY_OBSTETRICS;
            default -> Specialty.valueOf(specialtyName.trim().toUpperCase());
        };
    }
}
