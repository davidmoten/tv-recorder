package com.github.davidmoten.tv;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.github.davidmoten.xmltv.Desc;
import com.github.davidmoten.xmltv.Programme;
import com.github.davidmoten.xmltv.SubTitle;
import com.github.davidmoten.xmltv.Title;

public class SearchServlet extends HttpServlet {

	private static final long serialVersionUID = -4914856137989997740L;
	private final TvGuide guide;

	public SearchServlet(TvGuide tvGuide) {
		this.guide = tvGuide;
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String search = req.getParameter("q");
		resp.setContentType("text/html");
		PrintWriter out = resp.getWriter();
		out.println("<html><head><link rel=\"stylesheet\" href=\"css/style.css\"/><title>Results</title></head><body>");
		out.println("<form method=\"get\" action=\"search\"><input type=\"text\" class='search' name=\"q\" value=\""
				+ search + "\"><input type=\"submit\" value=\"Search\"></form>");
		guide.search(search).sorted((a, b) -> a.getStart().compareTo(b.getStart())).map(p -> toHtml(p))
				.forEach(s -> out.println(s));
		out.println("</body></html>");

	}

	private String toHtml(Programme p) {
		StringBuilder s = new StringBuilder();
		s.append("<div class='time'>");
		s.append(formatTime(p.getStart()));
		s.append("</div>");
		s.append("<div class='channel'>");
		s.append(" ");
		s.append(p.getChannel());
		s.append("</div>");
		s.append(" ");
		for (Title t : p.getTitle()) {
			s.append("<div class='title'>");
			s.append(t.getvalue());
			s.append("</div>");
		}
		s.append(" ");
		for (SubTitle t : p.getSubTitle()) {
			s.append("<div class='subTitle'>");
			s.append(t.getvalue());
			s.append("</div>");
		}
		s.append("</div>");
		s.append("<div class='afterHeader'></div>");

		for (Desc d : p.getDesc()) {
			if (!d.getvalue().contains("Please donate")) {
				s.append("<div class='description'>");
				s.append(d.getvalue());
				String rating = p.getRating().stream().map(c -> c.getValue()).collect(Collectors.joining(", "));
				if (rating.length() > 0) {
					s.append(" [" + rating + "]");
				}
				s.append("</div>");
			}
		}
		if (!p.getCategory().isEmpty()) {
			label(s, "Category", p.getCategory().stream().map(c -> c.getvalue()).collect(Collectors.joining(", ")));
		}
		if (!p.getKeyword().isEmpty()) {
			label(s, "Keywords ", p.getKeyword().stream().map(c -> c.getvalue()).collect(Collectors.joining(", ")));
		}
		if (!p.getCountry().isEmpty()) {
			label(s, "Country", p.getCountry().stream().map(c -> c.getvalue()).collect(Collectors.joining(", ")));
		}
		if (p.getCredits() != null) {
			label(s, "Actors",
					p.getCredits().getActor().stream().map(c -> c.getvalue()).collect(Collectors.joining(", ")));
		}
		return s.toString();
	}

	private static void label(StringBuilder s, String label, String value) {
		s.append("<div class='label'>");
		s.append(label + ":");
		s.append("</div>");
		s.append("<div class='detail'>");
		s.append(value);
		s.append("</div>");
		s.append("<div class='afterDetail'></div>");
	}

	private Object formatTime(String t) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
		LocalDateTime dateTime = LocalDateTime.parse(t.substring(0, 12), formatter);
		String[] days = { "Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun" };
		return days[dateTime.getDayOfWeek().getValue() - 1] + " " + t.substring(0, 2) + "/" + t.substring(4, 6) + "/"
				+ t.substring(6, 8) + " " + t.substring(8, 10) + ":" + t.substring(10, 12);
	}

}
