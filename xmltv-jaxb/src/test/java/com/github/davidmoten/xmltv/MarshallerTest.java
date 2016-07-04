package com.github.davidmoten.xmltv;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

import org.junit.Test;

public class MarshallerTest {

	@Test
	public void test() throws JAXBException {
		JAXBContext context = JAXBContext.newInstance(Tv.class);
		Unmarshaller u = context.createUnmarshaller();
		JAXBElement<Tv> tv = u.unmarshal(new StreamSource(MarshallerTest.class.getResourceAsStream("/sample.xml")), Tv.class);
		System.out.println(tv.getValue().programme.size());
	}
	
}
