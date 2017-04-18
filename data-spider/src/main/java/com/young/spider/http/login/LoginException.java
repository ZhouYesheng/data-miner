package com.young.spider.http.login;

/**
 * Created by yangyong3 on 2017/3/14.
 */
public class LoginException extends Exception {

    public LoginException(String message) {
        super(message);
    }

    public LoginException(String message, Throwable cause) {
        super(message, cause);
    }

    public LoginException(Throwable cause) {
        super(cause);
    }
}
