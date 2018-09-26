package org.opensrp.stock.openlmis.service;

import org.opensrp.stock.openlmis.domain.MasterTableEntry;
import org.opensrp.stock.openlmis.domain.metadata.BaseMetaData;
import org.opensrp.stock.openlmis.domain.metadata.ValidDestinationMetaData;
import org.opensrp.stock.openlmis.repository.MasterTableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.opensrp.stock.openlmis.util.Utils.VALID_DESTINATION;

@Service
public class ValidDestinationService {

    @Autowired
    private MasterTableRepository repository;

    public List<ValidDestinationMetaData> getAll() {

        List<MasterTableEntry> validDestinations = repository.get(VALID_DESTINATION);
        List<ValidDestinationMetaData> validDestinationsMetaData = new ArrayList<>();
        for (MasterTableEntry validDestination : validDestinations) {
            validDestinationsMetaData.add((ValidDestinationMetaData) validDestination.getJson());
        }
        return validDestinationsMetaData;
    }

    public List<ValidDestinationMetaData> getFiltered(String openlmisUuid, String facilityTypeUuid) {

        List<ValidDestinationMetaData> validDestinations = getAll();
        if (openlmisUuid != null && facilityTypeUuid != null) {
            return filterResults(validDestinations, openlmisUuid, facilityTypeUuid);
        }
        return validDestinations;
    }

    private List<ValidDestinationMetaData> filterResults(List<ValidDestinationMetaData> validDestinations, String openlmisUuid, String facilityTypeUuid) {
        List<ValidDestinationMetaData> filteredResult = new ArrayList<>();
        for (ValidDestinationMetaData validDestination : validDestinations) {
            if (validDestination.getOpenlmisUuid().equals(openlmisUuid) && validDestination.getFacilityTypeUuid().equals(facilityTypeUuid)) {
                filteredResult.add(validDestination);
            }
        }
        return filteredResult;
    }

    public List<ValidDestinationMetaData> get(long syncServerVersion) {

        List<MasterTableEntry> validDestinations = repository.get(VALID_DESTINATION, syncServerVersion);
        List<ValidDestinationMetaData> validDestinationsMetaData = new ArrayList<>();
        for (MasterTableEntry validDestination : validDestinations) {
            validDestinationsMetaData.add((ValidDestinationMetaData) validDestination.getJson());
        }
        return validDestinationsMetaData;
    }

    public MasterTableEntry get(String type, String id) {
        return repository.get(type, id);
    }

    public void addOrUpdate(BaseMetaData entry) {
        repository.add(entry);
    }

    public void update(MasterTableEntry entry) { repository.update(entry); }
}
