package com.example.demo;

import java.util.Objects;
public class SqlExceptionInfo {
    private String message;
    private String state;
    private int errorCode;

    public SqlExceptionInfo() {
        super();
    }

    public SqlExceptionInfo(String message, String state, int errorCode) {
        this.message = message;
        this.state = state;
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getState() {
        return this.state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getErrorCode() {
        return this.errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof SqlExceptionInfo)) {
            return false;
        }
        SqlExceptionInfo sqlException = (SqlExceptionInfo) o;
        return Objects.equals(message, sqlException.message) && Objects.equals(state, sqlException.state) && Objects.equals(errorCode, sqlException.errorCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(message, state, errorCode);
    }

    @Override
    public String toString() {
        return "{" +
            " message='" + getMessage() + "'" +
            ", state='" + getState() + "'" +
            ", errorCode='" + getErrorCode() + "'" +
            "}";
    }

    
}
