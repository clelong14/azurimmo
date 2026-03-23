package bts.sio.azurimmo.model.dto;

import java.time.Instant;

public class ApiError {
    private final String message;
    private final String details;
    private final int status;
    private final Instant timestamp;

    public ApiError(String message, String details, int status, Instant timestamp) {
        this.message = message;
        this.details = details;
        this.status = status;
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public String getDetails() {
        return details;
    }

    public int getStatus() {
        return status;
    }

    public Instant getTimestamp() {
        return timestamp;
    }
}

