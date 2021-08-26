package com.example.demo;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
 

@Path("/testservice")
public class TestService {


 
  @GET
  @Produces(MediaType.TEXT_PLAIN)
  public String getTestService(@QueryParam("name") String name) {
    return "Hello "+name+"! This is coming from webservice! "+Repository.sqlQuery("SHOW tables");
  }
  
}
