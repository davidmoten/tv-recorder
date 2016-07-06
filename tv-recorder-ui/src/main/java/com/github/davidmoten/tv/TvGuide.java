package com.github.davidmoten.tv;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

import com.github.davidmoten.xmltv.Tv;

public class TvGuide {

    Tv get() {
        return tv();
    }

    private static Tv tv() {
        try {
            final JAXBContext context = JAXBContext.newInstance(Tv.class);
            final Unmarshaller u = context.createUnmarshaller();
            return u.unmarshal(new StreamSource(TvGuide.class.getResourceAsStream("/sample.xml")),
                    Tv.class).getValue();
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }

}
