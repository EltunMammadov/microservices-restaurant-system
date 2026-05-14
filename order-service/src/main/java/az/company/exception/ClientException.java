package az.company.exception;

import lombok.Getter;

@Getter
public class ClientException extends RuntimeException {
    private final String errorCode;
    private final Integer statusCode;

    public ClientException(String message, String errorCode, Integer statusCode) {
        super(message);
        this.errorCode = errorCode;
        this.statusCode = statusCode;
    }
}
