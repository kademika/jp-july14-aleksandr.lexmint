import day11.SystemOut;

import java.sql.*;

/**
 * Created by lexmint on 15.10.14.
 */
public class SQL {
    public static void main(String[] args) throws SQLException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        String url = "jdbc:mysql://37.187.71.101:3306/test";
        String user = "test";
        String password = "test";
        Connection conn = DriverManager.getConnection(url, user, password);

        Statement stat = conn.createStatement();

        stat.executeUpdate("DROP TABLE IF EXISTS Greetings");
        stat.executeUpdate("CREATE TABLE IF NOT EXISTS Greetings (Message CHAR(20), Number INT(20))");

        long time = System.currentTimeMillis();

        StringBuilder request = new StringBuilder("INSERT INTO Greetings VALUES ");
        for (int i = 0; i < 1000; i++) {
            request.append("('" + i + "', " + "'1')");
            if (i == 999) {
                request.append(";");
            } else {
                request.append(',');
            }
            System.out.println(System.currentTimeMillis() - time);
            time = System.currentTimeMillis();
        }

        time = System.currentTimeMillis();
        stat.executeUpdate(request.toString());
        System.out.println(System.currentTimeMillis() - time);
//        try(ResultSet result = stat.executeQuery("SELECT * FROM Greetings")) {
//            if (result.next()) {
//                System.out.println(result.getString(1));
//            }
//        }
    }
}
