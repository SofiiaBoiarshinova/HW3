package org.hibernate_test.data.util;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate_test.data.entities.NodePack;

public class HibernateSessionFactoryUtil {
    private static SessionFactory sessionFactory;

    private HibernateSessionFactoryUtil() {}

    public static SessionFactory getSessionFactoryPostgreSQL() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration().configure("hibernate-postgresql.cfg.xml");
                configuration.addAnnotatedClass(NodePack.class);
                StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
                sessionFactory = configuration.buildSessionFactory(builder.build());

            } catch (Exception e) {
                System.out.println("Исключение!" + e);
            }
        }
        return sessionFactory;
    }
    public static SessionFactory getSessionFactoryH2() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration().configure("hibernate-h2.cfg.xml");
                configuration.addAnnotatedClass(NodePack.class);
                StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
                sessionFactory = configuration.buildSessionFactory(builder.build());

            } catch (Exception e) {
                System.out.println("Исключение!" + e);
            }
        }
        return sessionFactory;
    }
}