package com.github.davidmoten.tv.rs;

import org.glassfish.jersey.server.ResourceConfig;

public class ApplicationResourceConfig extends ResourceConfig {
	
	public ApplicationResourceConfig() {
		packages(ApplicationResourceConfig.class.getPackage().getName());
	}

}
