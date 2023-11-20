package project.eventregistration.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import project.eventregistration.Exceptions.Errors.UsernameIsAlreadyExist;
import project.eventregistration.Exceptions.Errors.UsernameNotFoundException;

@RestControllerAdvice
public class ExceptionHandlerController {

    private static final int USERNAME_NOT_FOUND_ERROR_CODE = 1001;

    private static final int USERNAME_IS_ALREADY_EXIST_ERROR_CODE = 1002;


    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<AppError> handleUsernameNotFoundException(UsernameNotFoundException ex) {
        AppError error = new AppError(USERNAME_NOT_FOUND_ERROR_CODE, ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.CONFLICT); // 409
    }

    @ExceptionHandler(UsernameIsAlreadyExist.class)
    public ResponseEntity<AppError> handleUsernameIsAlreadyExist(UsernameIsAlreadyExist ex) {
        AppError error = new AppError(USERNAME_IS_ALREADY_EXIST_ERROR_CODE, ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }
}
