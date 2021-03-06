package com.example.demo;

import java.io.InputStream;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.io.InputStreamReader;
import java.sql.SQLException;

import javax.naming.NamingException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;

@Path("/api")
public class Service{

  static{
    try {
      InitContext.setupInitialContext();
      Repository.init();
    } catch (NamingException e) {
      System.err.println(e.toString());
    } catch (SQLException e) {
      System.err.println(e.toString());
    }
  }


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
    } catch (NamingException e) {
      return Response.status(500).entity(e.getMessage()).build();
    }
    return Response.status(200).entity(ans).build();
  }

  @GET
  @Path("/users")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getUsers() {
    String ans;
    try {
      ans = Repository.getUsers();
    } catch (SQLException e) {
      SqlExceptionMapper exceptionMapper = new SqlExceptionMapper();
      return exceptionMapper.toResponse(new SqlException(new SqlExceptionInfo(e.getMessage(), e.getSQLState(), e.getErrorCode())));
    } catch (NamingException e) {
      return Response.status(500).entity(e.getMessage()).build();
    }
    return Response.status(200).entity(ans).build();
  }

  @POST
  @Path("/cart/add/")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response addItem(InputStream input){

    String result = "";
    Reader reader;
    try {
      reader = new InputStreamReader(input, "UTF-8");
    } catch (UnsupportedEncodingException e) {
      return Response.status(500).entity(e.getMessage()).build();
    }
    Item item = new Gson().fromJson(reader, Item.class);

    try {
      Repository.addItem(item);
    } catch (SQLException e) {
      SqlExceptionMapper exceptionMapper = new SqlExceptionMapper();
      return exceptionMapper.toResponse(new SqlException(new SqlExceptionInfo(e.getMessage(), e.getSQLState(), e.getErrorCode())));
    } catch (NamingException e) {
      return Response.status(500).entity(e.getMessage()).build();
    }
    result = "Item added to cart : " + item.getProductName() + " Quantity : " + item.getQuantity() + " UserID: " + item.getUserId();
    
    return Response.status(200).entity(result).build();
    
  }

  @POST
  @Path("/users/add/")
  @Produces(MediaType.APPLICATION_JSON)
  public Response addUser(@QueryParam("userName") String userName){

    String result = "";

    try {
      Repository.addUser(userName);
    } catch (SQLException e) {
      SqlExceptionMapper exceptionMapper = new SqlExceptionMapper();
      return exceptionMapper.toResponse(new SqlException(new SqlExceptionInfo(e.getMessage(), e.getSQLState(), e.getErrorCode())));
    } catch (NamingException e) {
      return Response.status(500).entity(e.getMessage()).build();
    }
    result = "User with Name: " + userName + " added. ";
    
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
    } catch (NamingException e) {
      return Response.status(500).entity(e.getMessage()).build();
    }
    return Response.status(200).entity("Item with id: "+id+" deleted.").build();
  }

  @DELETE
  @Path("/users/delete/")
  @Produces(MediaType.APPLICATION_JSON)
  public Response deleteUserById(@QueryParam("userId") int userId) {
    try {
      Repository.deleteUserById(userId);
    } catch (SQLException e) {
      SqlExceptionMapper exceptionMapper = new SqlExceptionMapper();
      return exceptionMapper.toResponse(new SqlException(new SqlExceptionInfo(e.getMessage(), e.getSQLState(), e.getErrorCode())));
    } catch (NamingException e) {
      return Response.status(500).entity(e.getMessage()).build();
    }
    return Response.status(200).entity("User with userId: "+userId+" deleted.").build();
  }

  @POST
  @Path("/users/checkout/")
  @Produces(MediaType.APPLICATION_JSON)
  public Response checkout(@QueryParam("userId") int userId){

    String result = "";

    try {
      Repository.checkout(userId);
    } catch (SQLException e) {
      SqlExceptionMapper exceptionMapper = new SqlExceptionMapper();
      return exceptionMapper.toResponse(new SqlException(new SqlExceptionInfo(e.getMessage(), e.getSQLState(), e.getErrorCode())));
    } catch (NamingException e) {
      return Response.status(500).entity(e.getMessage()).build();
    }
    result = "Checkout successful for User with Id: " + userId + ". ";
    
    return Response.status(200).entity(result).build();
    
  }

  @GET
  @Path("/activityLog")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getActivityLog() {
    String ans;
    try {
      ans = Repository.getActivityLog();
    } catch (SQLException e) {
      SqlExceptionMapper exceptionMapper = new SqlExceptionMapper();
      return exceptionMapper.toResponse(new SqlException(new SqlExceptionInfo(e.getMessage(), e.getSQLState(), e.getErrorCode())));
    } catch (NamingException e) {
      return Response.status(500).entity(e.getMessage()).build();
    }
    return Response.status(200).entity(ans).build();
  }


}

