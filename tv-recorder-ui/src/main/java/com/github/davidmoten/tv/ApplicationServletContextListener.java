package com.github.davidmoten.tv;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ApplicationServletContextListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		ServletContext c = sce.getServletContext();
		c.addServlet("search", new SearchServlet(new TvGuide(), new Recordings())).addMapping("/search");
		c.addServlet("add", new AddServlet(new Recordings())).addMapping("/add");
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {

	}

}
