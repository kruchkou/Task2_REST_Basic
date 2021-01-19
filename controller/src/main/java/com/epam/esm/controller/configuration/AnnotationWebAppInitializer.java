package com.epam.esm.controller.configuration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.context.support.GenericWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

public class AnnotationWebAppInitializer implements WebApplicationInitializer {

    private final static String PROD_PROFILE = "prod";
    private final static String PACKAGE_PATH = "com.epam.esm.controller";

    @Override
    public void onStartup(ServletContext container) {
        AnnotationConfigWebApplicationContext context
                = new AnnotationConfigWebApplicationContext();

        context.getEnvironment().setActiveProfiles(PROD_PROFILE);

        context.scan(PACKAGE_PATH);
        container.addListener(new ContextLoaderListener(context));

        ServletRegistration.Dynamic dispatcher = container
                .addServlet("dispatcher", new DispatcherServlet(new GenericWebApplicationContext()));

        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");
    }


}