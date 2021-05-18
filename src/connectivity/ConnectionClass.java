package connectivity;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionClass {
    public Connection connection;
    public Connection getConnection(){
        String databaseName="alienchess";
        String userName="root";
        String password="Devpatel5?";
        String url="jdbc:mysql://localhost/"+ databaseName;

        try {
            Class.forName("com.mysql.jdbc.Driver");//calling hte driver calss
             connection= DriverManager.getConnection(url,userName,password);
        } catch (Exception e) {
            e.printStackTrace();
        }


        return connection;
    }




}
