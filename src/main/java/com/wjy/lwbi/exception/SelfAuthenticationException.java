package com.wjy.lwbi.exception;

import org.springframework.security.core.AuthenticationException;

import java.nio.file.AccessDeniedException;

/**
 * @author 吴峻阳
 * @version 1.0
 */
public class SelfAuthenticationException extends AuthenticationException {

    private final int code;

    public SelfAuthenticationException(int code, String message) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
