package org.opensrp.stock.openlmis.service;

import org.opensrp.stock.openlmis.domain.MasterTableEntry;
import org.opensrp.stock.openlmis.domain.metadata.BaseMetaData;
import org.opensrp.stock.openlmis.domain.metadata.FacilityProgramMetaData;
import org.opensrp.stock.openlmis.repository.MasterTableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.opensrp.stock.openlmis.util.Utils.FACILITY_PROGRAM;

@Service
public class FacilityProgramService {

    @Autowired
    private MasterTableRepository repository;

    public List<FacilityProgramMetaData> getAll() {

        List<MasterTableEntry> facilityPrograms = repository.get(FACILITY_PROGRAM);
        List<FacilityProgramMetaData> facilityProgramsMetaData = new ArrayList<>();
        for (MasterTableEntry facilityProgram : facilityPrograms) {
            facilityProgramsMetaData.add((FacilityProgramMetaData) facilityProgram.getJson());
        }
        return facilityProgramsMetaData;
    }

    public List<FacilityProgramMetaData> get(long syncServerVersion) {

        List<MasterTableEntry> facilityPrograms = repository.get(FACILITY_PROGRAM, syncServerVersion);
        List<FacilityProgramMetaData> facilityProgramsMetaData = new ArrayList<>();
        for (MasterTableEntry facilityProgram : facilityPrograms) {
            facilityProgramsMetaData.add((FacilityProgramMetaData) facilityProgram.getJson());
        }
        return facilityProgramsMetaData;
    }

    public MasterTableEntry get(String type, String id) {
        return repository.get(type, id);
    }

    public void addOrUpdate(BaseMetaData entry) {
        repository.add(entry);
    }

    public void update(MasterTableEntry entry) { repository.update(entry); }
}
