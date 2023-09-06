package com.auth.library.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.auth.library.model.CommonClientDetails;

public class ClientResponseWithId {

    private int status;
    private String message;
    private LocalDateTime timestamp;
    private String path;
    private String clientId;

    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public String getPath() {
        return path;
    }
    public void setPath(String path) {
        this.path = path;
    }
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public ClientResponseWithId(int status, String message, LocalDateTime timestamp, String clientId, String path) {
        this.status = status;
        this.message = message;
        this.timestamp = timestamp;
        this.clientId = clientId;
        this.path = path;
    }
    public ClientResponseWithId() {}

    @Override
    public String toString() {
        return "SingleClientControllerResponse{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", timestamp=" + timestamp +
                ", path='" + path + '\'' +
                ", clientId=" + clientId +
                '}';
    }
}
