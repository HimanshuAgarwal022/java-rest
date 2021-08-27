package com.example.demo;


public class SqlException extends RuntimeException {

    private SqlExceptionInfo error;

    public SqlException (SqlExceptionInfo error) {
        super();
        this.error = error;
    }

    public SqlExceptionInfo getError() {
        return this.error;
    }

}
