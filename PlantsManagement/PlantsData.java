package PlantsManagement;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PlantsData implements Serializable {
    private String excelRecordPath;
    private String databaseName;
    private String savedPath = "";
    private final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private String DB_URL;
    private String USER = "root";
    private String PASS = "Wcqxm@1981";
    public PlantsData(String path, String databaseName, String userName, String password) throws ClassNotFoundException, SQLException {
        this.excelRecordPath = path;
        this.databaseName = databaseName;
        this.DB_URL = "jdbc:mysql://localhost:3306/" + this.databaseName + "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
        this.USER = userName;
        this.PASS = password;
        Class.forName(JDBC_DRIVER);
        Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
    }
    public boolean addItem(Plant plant) {
        return true;
    }
    public boolean modifyItem(int plantId, Plant plant) {
        return true;
    }
    public boolean removeItem(int plantId) {
        return true;
    }
    public boolean save() {
        return true;
    }
    public boolean exist() {
        return !savedPath.isEmpty();
    }

    public void init() {
    }
    public void excelToDatabase() {

    }
}
