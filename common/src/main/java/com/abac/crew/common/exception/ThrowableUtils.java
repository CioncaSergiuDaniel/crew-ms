package com.abac.crew.common.exception;

public class ThrowableUtils {

    public boolean hasCause(final Throwable ex, final Class<? extends Throwable> causeClass) {

        Throwable exception = ex;
        int count = 10;
        boolean found = false;
        while (exception != null && count > 0) {
            if (exception.getClass() == causeClass) {
                found = true;
                break;
            }
            exception = exception.getCause();
            count--;
        }

        return found;
    }
}
