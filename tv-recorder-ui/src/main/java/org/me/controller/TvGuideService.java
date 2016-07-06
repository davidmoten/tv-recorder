package org.me.controller;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

import org.springframework.stereotype.Service;

import com.github.davidmoten.xmltv.MarshallerTest;
import com.github.davidmoten.xmltv.Tv;

@Service
public class TvGuideService {

    public Tv get() {
        return tv();
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

}
