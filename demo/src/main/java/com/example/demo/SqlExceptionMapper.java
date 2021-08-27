package com.example.demo;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class SqlExceptionMapper implements ExceptionMapper<SqlException> {

    @Override
    @Produces(MediaType.APPLICATION_JSON)
    public Response toResponse (SqlException e) {

        ResponseBuilder rb = Response.status(Status.BAD_REQUEST);

        rb.entity(e.getError());
        return rb.build();   
    }
}
