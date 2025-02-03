package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.config.AppConfig;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private final Connection connection;

    public UserDaoJDBCImpl() throws SQLException {
        this.connection = Util.getConnection();
    }

    private void makeQuery(String query) {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(query);
        } catch (SQLException e) {
            System.out.printf("Error making query: %s", e.getMessage());
        }
    }

    public void createUsersTable() {
        makeQuery(AppConfig.getQuery("create_user_table"));
    }

    public void dropUsersTable() {
        makeQuery(AppConfig.getQuery("drop_user_table"));
    }

    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement ps = connection.prepareStatement(
            AppConfig.getQuery("save_user"))
        ) {
            ps.setString(1, name);
            ps.setString(2, lastName);
            ps.setByte(3, age);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void removeUserById(long id) {
        try (PreparedStatement ps = connection.prepareStatement(
            AppConfig.getQuery("remove_user_by_id"))
        ) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(
                 AppConfig.getQuery("get_all_users"))
        ) {
            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                String lastName = resultSet.getString("lastName");
                byte age = resultSet.getByte("age");

                users.add(new User(id, name, lastName, age));
            }
        } catch (SQLException e) {
            System.out.printf("Error getting users: %s", e);
        }

        return users;
    }

    public void cleanUsersTable() {
        makeQuery(AppConfig.getQuery("clean_user_table"));
    }

    public void closeConnection() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            System.out.printf("Error closing connection: %s", e.getMessage());
        }
    }
}
