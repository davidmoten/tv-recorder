package com.github.davidmoten.xmltv;

import java.io.IOException;
import java.io.StringWriter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;

public class Converter {

	public String toJson(Object o) {
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JaxbAnnotationModule());
		mapper.enable(SerializationFeature.INDENT_OUTPUT);
		StringWriter out = new StringWriter();
		try {
			mapper.writeValue(out, o);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return out.toString();
	}

}
