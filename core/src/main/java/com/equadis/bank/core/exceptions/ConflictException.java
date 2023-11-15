package com.equadis.bank.core.exceptions;

import com.equadis.bank.core.exceptions.ErrorCodeException;
import com.equadis.bank.core.exceptions.errors.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT)
public class ConflictException extends ErrorCodeException {

    public ConflictException(ErrorCode errorCode) {
        super(errorCode);
    }

    public ConflictException(ErrorCode errorCode, Object... args) {
        super(errorCode, args);
    }
}
