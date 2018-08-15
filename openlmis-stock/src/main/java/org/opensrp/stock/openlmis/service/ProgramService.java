package org.opensrp.stock.openlmis.service;

import org.opensrp.stock.openlmis.domain.MasterTableEntry;
import org.opensrp.stock.openlmis.domain.metadata.BaseMetaData;
import org.opensrp.stock.openlmis.domain.metadata.ProgramMetaData;
import org.opensrp.stock.openlmis.repository.MasterTableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.opensrp.stock.openlmis.util.Utils.getMostRecentMasterDataEntry;

@Service
public class ProgramService {

    @Autowired
    private MasterTableRepository repository;

    public List<ProgramMetaData> getAll() {

        List<MasterTableEntry> programs = repository.get("Program");
        List<ProgramMetaData> programsMetaData = new ArrayList<>();
        for (MasterTableEntry program : programs) {
            programsMetaData.add((ProgramMetaData) program.getJson());
        }
        return programsMetaData;
    }

    public List<ProgramMetaData> get(long syncServerVersion) {

        List<MasterTableEntry> programs = repository.get("Program", syncServerVersion);
        programs = getMostRecentMasterDataEntry(programs);

        List<ProgramMetaData> programsMetaData = new ArrayList<>();
        for (MasterTableEntry program : programs) {
            programsMetaData.add((ProgramMetaData) program.getJson());
        }
        return programsMetaData;
    }

    public MasterTableEntry get(String type, String id) {
        return repository.get(type, id);
    }

    public void add(BaseMetaData entry) {
        repository.add(entry);
    }

    public void update(MasterTableEntry entry) { repository.update(entry); }
}
