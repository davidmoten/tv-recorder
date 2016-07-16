package com.github.davidmoten.tv.rs;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.github.davidmoten.tv.TvGuide;
import com.github.davidmoten.xmltv.Programme;

@Path("/guide")
public class GuideResource {

	@GET
	@Path("ping")
	public String getServerTime() {
		System.out.println("RESTful Service 'MessageService' is running ==> ping");
		return "received ping on " + new Date().toString();
	}
	
	@GET
	@Path("search")
	@Produces({MediaType.APPLICATION_JSON})
	public List<Programme> search(){
		return new TvGuide().get().getProgramme().stream().limit(5).collect(Collectors.toList());
	}

}
