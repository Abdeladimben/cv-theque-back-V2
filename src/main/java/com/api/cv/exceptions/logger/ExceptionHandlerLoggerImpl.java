package com.api.cv.exceptions.logger;

import com.api.cv.enums.ErrorCode;
import com.api.cv.exceptions.ApiErrorException;
import com.api.cv.services.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.Serial;

@RequiredArgsConstructor
@Slf4j
@Component
public class ExceptionHandlerLoggerImpl implements ExceptionHandlerLogger {

    @Serial
    private static final long serialVersionUID = 2270868033669621899L;

    private final UserService userService;

    public void handle(Throwable th) {
        String message = th.getMessage();
        String USER = userService.getUsername(null) == null ? "UNKNOWN-USER" : userService.getUsername(null);
        log(message, USER, th);
    }

    protected void log(String message, String user, Throwable th) {

        StringBuilder messageToLogBuilder = new StringBuilder("  ");
        if(message != null) {
            messageToLogBuilder.append(message).append(" , ");
        }

        if (user != null) {
            messageToLogBuilder.append(" Logged In User : [" + user + "] , ");
        }

        if(th instanceof ApiErrorException) {
            // Ne pas tracer la stacktrace

            ErrorCode errorCode = ((ApiErrorException) th).getErrorCode();
            String description = ((ApiErrorException) th).getMessage();
            if(errorCode != null && description != null) {
                messageToLogBuilder.append("A functional exception occured. error code : ").append(errorCode).append(" , error description : ").append(description);
            }
            StackTraceElement stackTraceElement = th.getStackTrace()[0];

            messageToLogBuilder.append(System.lineSeparator());
            messageToLogBuilder.append("Source Class : [" + stackTraceElement.getClassName() + "]").append(" , Method Name : [" + stackTraceElement.getMethodName() + "]").append(" , Line Number : [" + stackTraceElement.getLineNumber() + "]");

            String messageToLog = messageToLogBuilder.toString();
            log.error(messageToLog);
        }

        else {
            String messageToLog = messageToLogBuilder.toString();
            log.error(messageToLog, th);
        }

    }

}
