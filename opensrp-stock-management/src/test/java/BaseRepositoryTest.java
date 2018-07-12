import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Statement;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:test-openlmis-application-context.xml")
public class BaseRepositoryTest {

    @Autowired
    private DataSource openLmisDataSource;

    @Before
    public void setUp() {
        truncateTables();
    }

    private void truncateTables() {

        try {
            Connection connection = DataSourceUtils.getConnection(openLmisDataSource);
            Statement statement = connection.createStatement();
            statement.executeUpdate("TRUNCATE " + "core.orderable");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
