package com.github.davidmoten.xmltv;

import java.util.stream.Collectors;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

import org.junit.Test;

public class MarshallerTest {

    @Test
    public void test() throws JAXBException {
        Tv tv = tv();
        System.out.println(tv.programme.size());
        // tv.getProgramme().stream().forEach(p -> System.out
        // .println(p.getChannel() + ": " + getTitle(p) + " " + p.getStart() +
        // "-"));
    }

    private static Tv tv() {
        try {
            final JAXBContext context = JAXBContext.newInstance(Tv.class);
            final Unmarshaller u = context.createUnmarshaller();
            return u.unmarshal(
                    new StreamSource(MarshallerTest.class.getResourceAsStream("/sample.xml")),
                    Tv.class).getValue();
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }

    private String getTitle(Programme p) {
        return p.getTitle().stream().map(t -> t.getvalue()).collect(Collectors.joining(" "));
    }

    @Test
    public void testHtmlChannel() {
        Tv tv = tv();
        tv.getProgramme().stream().map(p -> p.getChannel()).distinct().sorted()
                .forEach(System.out::println);
    }

    @Test
    public void produceJson() {
        System.out.println(new JsonConverter().toJson(tv().getProgramme().get(0)));
    }

}
