package pl.currencyinfo.curinf.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ErrorResponse {
    private String errorCode;
    private String message;
}
