package dev.gabriel.conduitapi.exception;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.io.Serial;
import java.io.Serializable;

@RequiredArgsConstructor
@Getter
@Setter
public class StandardError implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private final Integer status;
    private final String message;
    private final String details;
    private final Long timeStamp = System.currentTimeMillis();

    public StandardError(HttpStatus status, String details) {
        this.message = status.getReasonPhrase();
        this.status = status.value();
        this.details = details;
    }
}
