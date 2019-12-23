package br.digivox.odravison.apiserver.util;

import br.digivox.odravison.apiserver.shared.config.AppContext;
import com.google.gson.Gson;
import org.flywaydb.core.Flyway;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableConfigurationProperties
public class AbstractControllerTest {

    @Autowired
    private Flyway flyway;

    @Autowired
    private ApplicationContext context;

    protected Gson g = new Gson();

    @Autowired
    protected CreateEntityUtil createEntityUtil;

    @Before
    public void setUp() {
        AppContext.loadApplicationContext(this.context);
        flyway.clean();
        flyway.migrate();
    }

}
