package com.wjy.lwbi.exception;

/**
 * @author 吴峻阳
 * @version 1.0
 */
public class SelfSecurityException extends RuntimeException{

    private final int code;

    public SelfSecurityException(int code, String message) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
