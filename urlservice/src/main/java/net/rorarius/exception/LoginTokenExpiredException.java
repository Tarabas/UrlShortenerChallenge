package net.rorarius.exception;

public class LoginTokenExpiredException extends Exception {

    public LoginTokenExpiredException(String message) {
        super(message);
    }
}
