package com.github.davidmoten.tv;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ApplicationServletContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        sce.getServletContext() //
                .addServlet("search", new SearchServlet(new TvGuide())).addMapping("/search");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }

}
