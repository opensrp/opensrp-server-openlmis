import org.json.JSONObject;
import org.junit.BeforeClass;
import org.junit.Test;
import org.opensrp.stock.openlmis.domain.MasterTableMetaData;
import org.opensrp.stock.openlmis.domain.postgres.MasterTable;
import org.opensrp.stock.openlmis.repository.postgres.MasterTableRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class MasterTableRepositoryTest extends BaseRepositoryTest {

    @Autowired
    private MasterTableRepository repository;

    @BeforeClass
    public static void bootStrap() {
        tableName = "core.master_table";
    }

    @Test
    public void testAddShouldAddNewOrderable() {

        MasterTableMetaData metaData = new MasterTableMetaData(
                "id",
                "type",
                "identifier",
                482492049L
        );

        MasterTable masterTable = new MasterTable();
        masterTable.setId("id");
        masterTable.setJson(metaData);
        masterTable.setDateUpdated(782894L);

        repository.add(masterTable);

        MasterTable masterTableEntry = repository.get("id");
        assertNotNull(masterTableEntry);
    }
}
