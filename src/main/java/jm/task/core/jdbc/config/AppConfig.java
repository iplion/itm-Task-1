package jm.task.core.jdbc.config;

import lombok.Getter;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;

public class AppConfig {
    @Getter
    private static final Properties hibernateConfig;
    private static final Map<String, String> queries;

    static {
        try (InputStream queriesInputStream = AppConfig.class.getResourceAsStream("/queries.yaml");
             InputStream hibernateInputStream = AppConfig.class.getResourceAsStream("/hibernate.properties")) {
            if (Objects.isNull(hibernateInputStream) || Objects.isNull(queriesInputStream)) {
                throw new IOException("The program configuration file was not found");
            }

            hibernateConfig = new Properties();
            hibernateConfig.load(hibernateInputStream);

            queries = (new Yaml()).load(queriesInputStream);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static String getQuery(String key) {
        return queries.get(key);
    }

}
