package me.bayes.sitemap.exception;

/**
 * Created by kevinbayes on 2014/12/02.
 */
public enum ExceptionCode {
    GENERAL(50000, "General error occurred."),
    UNKNOWN_FILE_EXTENSION(50001, "Filename must end with .xml."),
    UNABLE_TO_CREATE_SITEMAP(50002, "Unable to create sitemap."),
    INVALID_FILE_CONTENT(50003, "Invalid file content.");

    private int code;
    private String message;

    ExceptionCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int code() {
        return code;
    }

    public String message() {
        return message;
    }
}
