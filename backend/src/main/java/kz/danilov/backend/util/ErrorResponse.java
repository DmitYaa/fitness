package kz.danilov.backend.util;

/**
 * User: Nikolai Danilov
 * Date: 01.10.2023
 */
public class ErrorResponse {
    private String message;

    public ErrorResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
