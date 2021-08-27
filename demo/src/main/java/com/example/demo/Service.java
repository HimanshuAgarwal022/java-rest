package com.example.demo;

import java.sql.SQLException;

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
public class Service{

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
    String ans;
    try {
      ans = Repository.getCart();
    } catch (SQLException e) {
      SqlExceptionMapper exceptionMapper = new SqlExceptionMapper();
      return exceptionMapper.toResponse(new SqlException(new SqlExceptionInfo(e.getMessage(), e.getSQLState(), e.getErrorCode())));
    }
    return Response.status(200).entity(ans).build();
  }

  @POST
  @Path("/cart/add/")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response addItem(Item item){
    if(item == null){
      return Response.status(400).entity("Missing item details. ").build();
    }
    try {
      Repository.addItem(item);
    } catch (SQLException e) {
      SqlExceptionMapper exceptionMapper = new SqlExceptionMapper();
      return exceptionMapper.toResponse(new SqlException(new SqlExceptionInfo(e.getMessage(), e.getSQLState(), e.getErrorCode())));
    }
    String result = "Item added to cart : " + item.getName() + " Quantity : " + item.getQuantity();
    return Response.status(200).entity(result).build();
  }

  @DELETE
  @Path("/cart/delete/")
  @Produces(MediaType.APPLICATION_JSON)
  public Response deleteItemById(@QueryParam("id") int id) {
    try {
      Repository.deleteItemById(id);
    } catch (SQLException e) {
      SqlExceptionMapper exceptionMapper = new SqlExceptionMapper();
      return exceptionMapper.toResponse(new SqlException(new SqlExceptionInfo(e.getMessage(), e.getSQLState(), e.getErrorCode())));
    }
    return Response.status(200).entity("Item with id: "+id+" deleted.").build();
  }

  
}
