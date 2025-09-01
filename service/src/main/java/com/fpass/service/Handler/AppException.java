package com.fpass.service.Handler;


    // User not found
    public class AppException extends RuntimeException {

        private final ErrorCode errorCode;

        public enum ErrorCode {
            USER_NOT_FOUND,
            INVALID_CREDENTIALS,
            USER_ALREADY_EXISTS,
            INTERNAL_ERROR
        }

        private AppException(String message, ErrorCode errorCode) {
            super(message);
            this.errorCode = errorCode;
        }

        public ErrorCode getErrorCode() {
            return errorCode;
        }

        // Factory methods
        public static AppException userNotFound(String username) {
            return new AppException("User not found: " + username, ErrorCode.USER_NOT_FOUND);
        }

        public static AppException invalidCredentials() {
            return new AppException("Invalid username or password", ErrorCode.INVALID_CREDENTIALS);
        }

        public static AppException userAlreadyExists(String username) {
            return new AppException("Username already taken: " + username, ErrorCode.USER_ALREADY_EXISTS);
        }

        public static AppException internalError(String message) {
            return new AppException(message, ErrorCode.INTERNAL_ERROR);
        }
    }

