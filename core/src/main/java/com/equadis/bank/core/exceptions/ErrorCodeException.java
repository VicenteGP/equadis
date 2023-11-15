package com.equadis.bank.core.exceptions;

import com.equadis.bank.core.exceptions.errors.ErrorCode;
import lombok.Getter;

@Getter
public abstract class ErrorCodeException extends RuntimeException{

    private final ErrorCode errorCode;

    public ErrorCodeException(ErrorCode errorCode) {
        super(errorCode.getDescription());
        this.errorCode = errorCode;
    }

    public ErrorCodeException(ErrorCode errorCode, Object... args) {
        super(String.format(errorCode.getDescription(), args));
        this.errorCode = errorCode;
    }
}
