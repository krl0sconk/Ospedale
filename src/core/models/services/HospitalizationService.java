/*
 * Archivo: HospitalizationService.java
 * Propósito: Servicio que maneja la creación y actualización de `Hospitalization` y publica eventos.
 * Relacionado con: `IHospitalizationRepository`, `EventBus`, y controladores que gestionan ingresos.
 * Impacto SOLID:
 *  - SRP: contiene la orquestación y publicación de eventos, manteniendo la entidad enfocada.
 *  - DIP: depende de abstracciones para desacoplar implementación concreta del repositorio y bus.
 */
package core.models.services;

import core.models.Hospitalization;
import core.models.storage.IHospitalizationRepository;
import core.models.events.EventBus;
import java.util.ArrayList;

public final class HospitalizationService {

    private final IHospitalizationRepository repo;
    private final EventBus bus;

    public HospitalizationService(IHospitalizationRepository repo, EventBus bus) {
        this.repo = repo;
        this.bus = bus;
    }

    public boolean addHospitalization(Hospitalization hosp) {
        boolean added = repo.addHospitalization(hosp);
        if (added) {
            bus.emitEvent(core.models.events.ModelEvent.HOSPITALIZATION_ADDED, hosp.serialize());
        }
        return added;
    }

    public java.util.ArrayList<Hospitalization> getHospitalizations() {
        return repo.getHospitalizations();
    }

    public Hospitalization getHospitalizationById(String id) {
        return repo.getHospitalizationById(id);
    }

    public void updateStatus(String hospitalizationId, core.models.enums.HospitalizationStatus status) {
        Hospitalization h = repo.getHospitalizationById(hospitalizationId);
        if (h == null) return;
        h.setStatus(status);
        bus.emitEvent(core.models.events.ModelEvent.HOSPITALIZATION_UPDATED, h.serialize());
    }
}
