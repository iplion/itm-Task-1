package jm.task.core.jdbc.util;

import jm.task.core.jdbc.config.AppConfig;
import jm.task.core.jdbc.model.User;
import lombok.Getter;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Util {
    @Getter
    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {

        return new Configuration()
            .setProperties(AppConfig.getHibernateConfig())
            .addAnnotatedClass(User.class)
            .buildSessionFactory();
    }
}
