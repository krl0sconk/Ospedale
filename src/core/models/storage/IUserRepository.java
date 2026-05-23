/*
 * Archivo: IUserRepository.java
 * Propósito: Contrato del repositorio específico para operaciones sobre usuarios.
 * Relacionado con: `IStorage`, `UserService`, `JsonUserLoader`.
 * Impacto SOLID:
 *  - ISP: define una interfaz enfocada en usuarios para evitar interfaces gordas.
 */
package core.models.storage;

import core.models.user.Administrator;
import core.models.user.Doctor;
import core.models.user.Patient;
import core.models.user.User;
import java.time.LocalDate;
import java.util.ArrayList;
import core.models.enums.Specialty;

public interface IUserRepository {
    boolean addUser(User user);

    User getUserById(long id);

    User getUserByUsername(String username);

    ArrayList<Patient> getPatients();

    ArrayList<Doctor> getDoctors();

    ArrayList<Administrator> getAdministrators();

    void updatePatient(long id, String username, String firstname, String lastname, String password, String email, LocalDate birthdate, boolean gender, long phone, String address);

    void updateDoctor(long id, String username, String firstname, String lastname, String password, Specialty specialty, String licenceNumber, String assignedOffice);
}
