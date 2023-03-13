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
    private final String msg;
    private final Long timeStamp = System.currentTimeMillis();

    public StandardError(HttpStatus status) {
        this.msg = status.getReasonPhrase();
        this.status = status.value();
    }
}
