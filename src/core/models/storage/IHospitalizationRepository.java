package core.models.storage;

import core.models.Hospitalization;
import java.util.ArrayList;

public interface IHospitalizationRepository {
    boolean addHospitalization(Hospitalization hospitalization);

    ArrayList<Hospitalization> getHospitalizations();

    Hospitalization getHospitalizationById(String id);
}
