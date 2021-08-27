package com.example.demo;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/api")
public class TestService {

  @GET
  @Path("/greet")
  @Produces(MediaType.TEXT_PLAIN)
  public Response getTestService(@QueryParam("name") String name) {
    return Response.status(200).entity("Hello "+name+"! This is coming from the webservice!").build();
  }

  @GET
  @Path("/cart")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getCart() {
    String ans = Repository.getCart();
    return Response.status(200).entity(ans).build();
  }

  @POST
  @Path("/cart/add/")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.TEXT_PLAIN)
  public Response addItem(Item item){
    if(item == null){
      return Response.status(400).entity("Missing item details. ").build();
    }
    Repository.addItem(item);
    String result = "Item added to cart : " + item.getName() + " Quantity : " + item.getQuantity();
    return Response.status(200).entity(result).build();
  }

  @DELETE
  @Path("/cart/delete/")
  @Produces(MediaType.TEXT_PLAIN)
  public Response deleteItemById(@QueryParam("id") int id) {
    Repository.deleteItemById(id);
    return Response.status(200).entity("Item with id: "+id+" deleted.").build();
  }

  
}
