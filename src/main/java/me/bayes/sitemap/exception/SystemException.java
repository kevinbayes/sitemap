package me.bayes.sitemap.exception;

/**
 * Created by kevinbayes on 2014/12/02.
 */
public class SystemException extends RuntimeException {

    private int code;

    public SystemException(ExceptionCode code) {
        super(code.message());
        this.code = code.code();
    }

    public SystemException(ExceptionCode code, String message) {
        super(code.message() + "[" + message + "]");
        this.code = code.code();
    }

    public SystemException(ExceptionCode code, Throwable cause) {
        super(code.message(), cause);
        this.code = code.code();
    }


    public int code() {
        return code;
    }
}
