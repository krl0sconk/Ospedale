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
            bus.emitEvent("hospitalization.added", hosp.serialize());
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
        bus.emitEvent("hospitalization.updated", h.serialize());
    }
}
