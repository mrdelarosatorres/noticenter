package com.nubettix.noticenter.rs;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;
import com.nubettix.noticenter.utils.ReadRss;

@Path("/news")
public class News {
	
	@GET
	@Produces("application/json")
	public Response allNews(@PathParam("rss") String rss) {
		ReadRss rssRead = ReadRss.getInstance();
		rssRead.setURL("https://actualidad.rt.com/feeds/all.rss");
		Gson js = new Gson();
		return Response.status(200).entity(js.toJson(rssRead.writeFeed())).build();
	}
	@Path("/cience/{c}")
	@GET
	@Produces("application/json")
	public Response getAllCienceNews(@PathParam("c") String c) {
		Map<String, String> result = new HashMap<String, String>();
		result.put("cience", c + "algo");
		Gson js = new Gson();
		return Response.status(200).entity(js.toJson(result))
				.build();
	}
}
