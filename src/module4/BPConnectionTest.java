package module4;

import java.sql.Connection;

public class BPConnectionTest {

    public static void main(String[] args) {

        Connection conn =
                BPDatabaseConnection.getConnection();

        if(conn != null){

            System.out.println(
                    "Database Connected Successfully!");

        }else{

            System.out.println(
                    "Connection Failed!");

        }
    }
}