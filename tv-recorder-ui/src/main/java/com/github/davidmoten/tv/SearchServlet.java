package com.github.davidmoten.tv;

import java.io.IOException;
import java.io.PrintWriter;
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
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String search = req.getParameter("q");
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();
        out.println(
                "<html><head><link rel=\"stylesheet\" href=\"css/style.css\"/><title>Results</title></head><body>");
        guide.search(search).sorted((a, b) -> a.getStart().compareTo(b.getStart()))
                .map(p -> toHtml(p)).forEach(s -> out.println(s));
        out.println("</body></html>");

    }

    private String toHtml(Programme p) {
        StringBuilder s = new StringBuilder();
        s.append("<div class='time'>");
        s.append(formatTime(p.getStart()));
        s.append(" ");
        s.append(p.getChannel());
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

        for (Desc d : p.getDesc()) {
            if (!d.getvalue().contains("Please donate")) {
                s.append("<p>");
                s.append(d.getvalue());
                s.append("</p>");
            }
        }
        if (!p.getCategory().isEmpty()) {
            s.append("<p>");
            s.append("Categories: ");
            s.append(p.getCategory().stream().map(c -> c.getvalue())
                    .collect(Collectors.joining(", ")));
            s.append("</p>");
        }
        if (!p.getKeyword().isEmpty()) {
            s.append("<p>");
            s.append("Keywords: ");
            s.append(p.getKeyword().stream().map(c -> c.getvalue())
                    .collect(Collectors.joining(", ")));
            s.append("</p>");
        }
        if (!p.getRating().isEmpty()) {
            s.append("<p>");
            s.append("Ratings: ");
            s.append(p.getRating().stream().map(c -> c.getValue())
                    .collect(Collectors.joining(", ")));
            s.append("</p>");
        }
        if (!p.getCountry().isEmpty()) {
            s.append("<p>");
            s.append("Country: ");
            s.append(p.getCountry().stream().map(c -> c.getvalue())
                    .collect(Collectors.joining(", ")));
            s.append("</p>");
        }
        if (p.getCredits() != null) {
            s.append("<p>");
            s.append("Actors: ");
            s.append(p.getCredits().getActor().stream().map(c -> c.getvalue())
                    .collect(Collectors.joining(", ")));
            s.append("</p>");
        }
        return s.toString();
    }

    private Object formatTime(String t) {
        return t.substring(0, 4) + "/" + t.substring(4, 6) + "/" + t.substring(6, 8) + " "
                + t.substring(8, 10) + ":" + t.substring(10, 12);
    }

}
