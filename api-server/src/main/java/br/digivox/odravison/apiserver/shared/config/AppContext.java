package br.digivox.odravison.apiserver.shared.config;

import org.springframework.context.ApplicationContext;

public class AppContext {

    private AppContext() {
        throw new IllegalStateException(AppContext.class.getName());
    }

    /**
     * Application Context
     */
    private static ApplicationContext ctx;

    /**
     * Loads the application context
     */
    public static void loadApplicationContext(ApplicationContext appCtx) {
        ctx = appCtx;
    }

    /**
     * get a bean on context
     */
    public static <T> T getBean(Class<T> clazz) {
        return ctx.getBean(clazz);
    }
}
