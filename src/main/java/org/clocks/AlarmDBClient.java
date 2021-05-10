package org.clocks;

import java.security.InvalidParameterException;
import java.sql.*;

public class AlarmDBClient {
    AlarmDBClient(int index) throws InvalidParameterException, SQLException {
        if (index < 0 || index > 3) throw new InvalidParameterException("App supports only 4 clocks at the same time");
        index_ = index + 1;

        connection = DriverManager.getConnection(DB_URL);
        if(connection != null)
            System.out.println("Connection Successful !\n");
        else
            System.exit(0);

        String query = "TRUNCATE TABLE IF EXISTS " +
                "alarms.clock" + index_ + ";";
        Statement st = connection.createStatement();
        st.executeQuery(query);
    }

    public String ring(DefaultTime time, String separator) throws SQLException {
        String query = "SELECT " +
                "* " +
                "FROM alarms.clock" + index_ + ";";
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(query);
        StringBuilder message = new StringBuilder(separator);

        while(rs.next()) {
            if (rs.getLong(1) == time.get_hours() &&
                rs.getLong(2) == time.get_minutes() &&
                rs.getLong(3) == time.get_seconds()) {
                message.append(rs.getString(4));
                message.append(separator);
            }
        }
        return message.toString();
    }

    public void set_alarm(DefaultTime time, String message) throws SQLException {
        String query = "INSERT INTO " +
                "alarms.clock" + index_ +
                " (*) VALUES " +
                "(" + time.get_hours() + ", " +
                time.get_minutes() + ", " +
                time.get_seconds() + ", " +
                "'" + message + "');";
        Statement st = connection.createStatement();
        st.executeQuery(query);
    }

    private static final String DB_URL = "jdbc:clickhouse://localhost:8123/default";
    private final Connection connection;
    private final int index_;
}
