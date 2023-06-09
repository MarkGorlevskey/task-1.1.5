package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.Properties;

import static jm.task.core.jdbc.util.Util.*;

public class UserDaoHibernateImpl implements UserDao {
    private final Session session = Util.getSessionFactory().openSession();
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        try {
            session.beginTransaction();

            session.createSQLQuery("create table users (id int not null auto_increment, " +
                    "name varchar(20), " +
                    "lastName varchar(20), " +
                    "age int, " +
                    "primary key(id))")
                    .addEntity(User.class).executeUpdate();

            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        try {
            session.beginTransaction();

            session.createSQLQuery("drop table if exists users").addEntity(User.class).executeUpdate();

            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try {
            User user = new User(name, lastName, age);
            session.beginTransaction();

            session.save(user);

            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        try {
            session.beginTransaction();

            User user = session.get(User.class, id);
            session.delete(user);

            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> allUsers = null;
        try {
            session.beginTransaction();

            allUsers = session.createQuery("from User")
                            .getResultList();

            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        }
        return allUsers;
    }

    @Override
    public void cleanUsersTable() {
        try {
            session.beginTransaction();

            session.createQuery("delete User users").executeUpdate();

            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        }
    }
}
