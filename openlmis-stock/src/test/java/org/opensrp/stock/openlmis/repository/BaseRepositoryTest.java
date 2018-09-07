package org.opensrp.stock.openlmis.repository;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:test-openlmis-application-context.xml")
public abstract class BaseRepositoryTest {

    @Autowired
    private DataSource openLmisDataSource;
    protected static List<String> tableNames = new ArrayList<>();

    @Before
    public void setUp() {
        truncateTables();
    }

    private void truncateTables() {
        try {
            for (String tableName : tableNames) {
                Connection connection = DataSourceUtils.getConnection(openLmisDataSource);
                Statement statement = connection.createStatement();
                statement.executeUpdate("TRUNCATE " + tableName);
                connection.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
