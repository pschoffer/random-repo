package airport.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

/**
 * Created by pavel on 31.3.17.
 */
@Configuration
@PropertySource("classpath:properties/database.properties")
public class PersistenceConfig {
    @Autowired
    Environment env;
}
