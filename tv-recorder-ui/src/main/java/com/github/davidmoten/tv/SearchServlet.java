package com.github.davidmoten.tv;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SearchServlet extends HttpServlet {

    private static final long serialVersionUID = -4914856137989997740L;
    private final TvGuide guide;

    public SearchServlet(TvGuide tvGuide) {
        this.guide = tvGuide;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String search = req.getParameter("search");
        guide.get().getProgramme().stream();

    }

}
