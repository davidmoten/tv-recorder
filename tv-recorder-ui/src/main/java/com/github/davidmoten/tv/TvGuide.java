package com.github.davidmoten.tv;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.stream.Stream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

import com.github.davidmoten.xmltv.Actor;
import com.github.davidmoten.xmltv.Category;
import com.github.davidmoten.xmltv.Country;
import com.github.davidmoten.xmltv.Desc;
import com.github.davidmoten.xmltv.Keyword;
import com.github.davidmoten.xmltv.Programme;
import com.github.davidmoten.xmltv.SubTitle;
import com.github.davidmoten.xmltv.Title;
import com.github.davidmoten.xmltv.Tv;

public class TvGuide {

	public Tv get() {
		return tv();
	}

	private static Tv tv() {
		try {
			final JAXBContext context = JAXBContext.newInstance(Tv.class);
			final Unmarshaller u = context.createUnmarshaller();
			return u.unmarshal(new StreamSource(TvGuide.class.getResourceAsStream("/sample.xml")), Tv.class).getValue();
		} catch (JAXBException e) {
			throw new RuntimeException(e);
		}
	}

	public Stream<Programme> search(String search) {
		return get().getProgramme().stream() //
				.filter(p -> {
					String t = text(p);
					return Arrays.stream(search.split(" ")) //
							.filter(x -> x.length() > 0) //
							.allMatch(x -> t.contains(x.toUpperCase()));
				}) //
				.filter(p -> localDateTime(p.getStop()).isAfter(LocalDateTime.now()));
	}

	private static LocalDateTime localDateTime(String dateTime) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
		return LocalDateTime.parse(dateTime.substring(0, 12), formatter);
	}

	public static int durationMinutes(Programme p) {
		return (int) ChronoUnit.MINUTES.between(localDateTime(p.getStart()), localDateTime(p.getStop()));
	}

	private static String text(Programme p) {
		StringBuilder s = new StringBuilder();
		add(s, p.getChannel());
		add(s, p.getClumpidx());
		for (Title t : p.getTitle()) {
			add(s, t.getvalue());
		}
		for (SubTitle t : p.getSubTitle()) {
			add(s, t.getvalue());
		}
		for (Category t : p.getCategory()) {
			add(s, t.getvalue());
		}
		for (Country t : p.getCountry()) {
			add(s, t.getvalue());
		}
		for (Desc t : p.getDesc()) {
			add(s, t.getvalue());
		}
		for (Keyword t : p.getKeyword()) {
			add(s, t.getvalue());
		}
		if (p.getLanguage() != null) {
			add(s, p.getLanguage().getvalue());
		}
		if (p.getCredits() != null)
			for (Actor t : p.getCredits().getActor()) {
				add(s, t.getvalue());
				add(s, t.getRole());
			}
		return s.toString().toUpperCase();
	}

	private static void add(StringBuilder s, String string) {
		if (string != null && string.trim().length() > 0) {
			s.append(string);
			s.append(' ');
		}
	}

	public static boolean isPlaying(Programme p) {
		LocalDateTime now = LocalDateTime.now();
		return localDateTime(p.getStart()).isBefore(now) && now.isBefore(localDateTime(p.getStop()));
	}

	public boolean markedForRecording(Programme p) {
		return false;
	}

	public boolean hasConflict(Programme p) {
		return false;
	}

}
