package com.github.davidmoten.tv;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddServlet extends HttpServlet {

	private static final long serialVersionUID = -8188149720997786894L;
	private final Recordings recordings;

	public AddServlet(Recordings recordings) {
		this.recordings = recordings;
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println(req.getParameter("channel"));
		resp.getWriter().println("{ok:\"true\"}".getBytes());
	}

}
