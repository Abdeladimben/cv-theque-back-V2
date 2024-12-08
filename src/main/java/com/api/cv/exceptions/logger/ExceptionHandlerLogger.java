package com.api.cv.exceptions.logger;

import java.io.Serializable;

public interface ExceptionHandlerLogger extends Serializable {

    /**
     *
     * Methode handle : Handle the exception.
     *
     * @param th
     *            a throwable to handle.
     */
    void handle(Throwable th);

}
