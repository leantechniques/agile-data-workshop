package co.leantechniques;

import org.dbunit.Assertion;
import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.ext.mysql.MySqlConnection;
import org.dbunit.operation.DatabaseOperation;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.io.File;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/test-contexts/test-context.xml")
public class DbUnitExample {

    @Autowired
    DataSource dataSource;

    @Before
    public void importDataSet() throws Exception {
        cleanInsertDataFrom("dbunit/expected_people.xml");
    }

    @Test
    public void expectedDataMatchesTableContent() throws Exception {
        // Fetch database data after executing your code
        MySqlConnection connection = new MySqlConnection(dataSource.getConnection(), null);
        IDataSet databaseDataSet = connection.createDataSet();
        ITable actualTable = databaseDataSet.getTable("people");

        // Load expected data from an XML dataset
        String resourceName = "dbunit/expected_people.xml";
        File expectedDataSetXml = new File(getClass().getClassLoader().getResource(resourceName).toURI());
        IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(expectedDataSetXml);
        ITable expectedTable = expectedDataSet.getTable("people");

        // Assert actual database table match expected table
        Assertion.assertEquals(expectedTable, actualTable);
    }

    private void cleanInsertDataFrom(String dbunitXmlFile) throws Exception {
        File expectedDataSetXml = new File(getClass().getClassLoader().getResource(dbunitXmlFile).toURI());
        IDataSet dataSet = new FlatXmlDataSetBuilder().build(expectedDataSetXml);
        IDatabaseTester databaseTester = new JdbcDatabaseTester("com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/customer_dev?autoreconnect=true", "agiledba", "p@ssw0rd");
        databaseTester.setSetUpOperation(DatabaseOperation.CLEAN_INSERT);
        databaseTester.setDataSet(dataSet);
        databaseTester.onSetup();
    }

}
