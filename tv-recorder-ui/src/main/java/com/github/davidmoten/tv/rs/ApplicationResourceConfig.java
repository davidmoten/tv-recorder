package com.github.davidmoten.tv.rs;

import org.glassfish.jersey.server.ResourceConfig;

import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;

public class ApplicationResourceConfig extends ResourceConfig {
	
	public ApplicationResourceConfig() {
		register(JacksonJaxbJsonProvider.class);
		packages(ApplicationResourceConfig.class.getPackage().getName());
	}

}
