package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {

        String sql = "create table users (id long not null auto_increment, " +
                "name varchar(20), " +
                "lastName varchar(20), " +
                "age int, " +
                "primary key(id))";

        try (Connection connection = Util.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql))
        {
            preparedStatement.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        String sql = "drop table if exists users";

        try (Connection connection = Util.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql))
        {
            preparedStatement.executeUpdate();
        }
        catch (SQLException e) {
            System.out.println("Таблички нет, но ошибку я не выкину. Твёрдо и чётко");
        }
    }

    public void saveUser(String name, String lastName, byte age) throws SQLException {
        String sql = "INSERT INTO USERS (id, name, lastName, age) VALUES (?, ?, ?, ?)";
        Connection connection = Util.getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            connection.createStatement();
            connection.setAutoCommit(false);
            User user = new User(name, lastName, age);

            preparedStatement.setLong(1, user.getId());
            preparedStatement.setString(2, user.getName());
            preparedStatement.setString(3, user.getLastName());
            preparedStatement.setByte(4, user.getAge());

            preparedStatement.executeUpdate();
            connection.commit();
        }
        catch (SQLException e) {
            connection.rollback();
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) throws SQLException {
        Connection connection = Util.getConnection();
        String sql = "delete from users where id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql))
        {
            connection.createStatement();
            connection.setAutoCommit(false);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            connection.commit();
        }
        catch (SQLException e) {
            connection.rollback();
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() throws SQLException {
        Connection connection = Util.getConnection();
        List<User> userList = new ArrayList<>();
        String sql = "select id, name, lastName, age from users";

        try (Statement statement = connection.createStatement()) {
            connection.setAutoCommit(false);
            ResultSet resultSet = statement.executeQuery(sql);
            connection.commit();

            while (resultSet.next()) {
                User user = new User();

                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));

                userList.add(user);
            }
        }
        catch (SQLException e) {
            connection.rollback();
            e.printStackTrace();
        }
        return userList;
    }

    public void cleanUsersTable() throws SQLException {
        Connection connection = Util.getConnection();
        String sql = "delete from users";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            connection.createStatement();
            connection.setAutoCommit(false);
            preparedStatement.executeUpdate();
            connection.commit();
        }
        catch (SQLException e) {
            connection.rollback();
            e.printStackTrace();
        }
    }
}
