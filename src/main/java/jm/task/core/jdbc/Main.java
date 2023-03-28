package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) throws SQLException {
        // реализуйте алгоритм здесь
        UserServiceImpl service = new UserServiceImpl();
        service.createUsersTable();

        service.saveUser("Antony", "Belov", (byte) 32);
        service.saveUser("Billy", "Clover", (byte) 24);
        service.saveUser("Calvin", "Druzhe", (byte) 48);
        service.saveUser("Daniil", "Elkin", (byte) 16);

        Stream.of(service.getAllUsers()).forEach(System.out::println);

        // service.cleanUsersTable();

        // service.dropUsersTable();
    }
}
