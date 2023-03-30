package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        try (Session session = Util.getSession().getCurrentSession()) {
            session.beginTransaction();

            session.createSQLQuery("create table users (id int not null auto_increment, " +
                    "name varchar(20), " +
                    "lastName varchar(20), " +
                    "age int, " +
                    "primary key(id))")
                    .addEntity(User.class).executeUpdate();

            session.getTransaction().commit();
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = Util.getSession().getCurrentSession()) {
            session.beginTransaction();

            session.createSQLQuery("drop table if exists users").addEntity(User.class).executeUpdate();

            session.getTransaction().commit();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = Util.getSession().getCurrentSession()) {
            User user = new User(name, lastName, age);
            session.beginTransaction();

            session.save(user);

            session.getTransaction().commit();
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = Util.getSession().getCurrentSession()) {
            session.beginTransaction();

            User user = session.get(User.class, id);
            session.delete(user);

            session.getTransaction().commit();
        }
    }

    @Override
    public List<User> getAllUsers() {

        try (Session session = Util.getSession().getCurrentSession()) {
            session.beginTransaction();

            List<User> allUsers = session.createQuery("from User")
                            .getResultList();

            session.getTransaction().commit();
            return allUsers;
        }
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = Util.getSession().getCurrentSession()) {
            session.beginTransaction();

            session.createQuery("delete User users").executeUpdate();

            session.getTransaction().commit();
        }
    }
}
