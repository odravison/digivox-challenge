package br.digivox.odravison.apiserver;

import br.digivox.odravison.apiserver.shared.config.AppContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class ApiServerApplication {

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(ApiServerApplication.class, args);
        AppContext.loadApplicationContext(ctx);
    }

}
