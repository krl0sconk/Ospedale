/*
 * Archivo: UserService.java
 * Propósito: Capa de servicio que orquesta operaciones sobre usuarios y publica eventos.
 * Relacionado con: `IUserRepository`, `EventBus`, controladores que consumen `UserService`.
 * Impacto SOLID:
 *  - SRP: separa lógica de negocio y publicación de eventos de las entidades y repositorios.
 *  - DIP: depende de `IUserRepository` y `EventBus` (abstracciones) para facilitar pruebas.
 */
package core.models.services;

import core.models.user.Administrator;
import core.models.user.Doctor;
import core.models.user.Patient;
import core.models.user.User;
import core.models.storage.IUserRepository;
import core.models.events.EventBus;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import core.models.enums.Specialty;

public final class UserService {

    private final IUserRepository repo;
    private final EventBus bus;

    public UserService(IUserRepository repo, EventBus bus) {
        this.repo = repo;
        this.bus = bus;
    }

    public boolean addUser(User user) {
        boolean added = repo.addUser(user);
        if (added) {
            bus.emitEvent(core.models.events.ModelEvent.USER_ADDED, new HashMap<>());
        }
        return added;
    }

    public User getUserById(long id) {
        return repo.getUserById(id);
    }

    public User getUserByUsername(String username) {
        return repo.getUserByUsername(username);
    }

    public ArrayList<Patient> getPatients() {
        return repo.getPatients();
    }

    public ArrayList<Doctor> getDoctors() {
        return repo.getDoctors();
    }

    public ArrayList<Administrator> getAdministrators() {
        return repo.getAdministrators();
    }

    public void updatePatient(long id, String username, String firstname, String lastname, String password, String email, LocalDate birthdate, boolean gender, long phone, String address) {
        repo.updatePatient(id, username, firstname, lastname, password, email, birthdate, gender, phone, address);
        User u = repo.getUserById(id);
        if (u instanceof Patient) {
            bus.emitEvent(core.models.events.ModelEvent.USER_UPDATED, ((Patient) u).serialize());
        }
    }

    public void updateDoctor(long id, String username, String firstname, String lastname, String password, Specialty specialty, String licenceNumber, String assignedOffice) {
        repo.updateDoctor(id, username, firstname, lastname, password, specialty, licenceNumber, assignedOffice);
        User u = repo.getUserById(id);
        if (u instanceof Doctor) {
            bus.emitEvent(core.models.events.ModelEvent.USER_UPDATED, ((Doctor) u).serialize());
        }
    }
}
