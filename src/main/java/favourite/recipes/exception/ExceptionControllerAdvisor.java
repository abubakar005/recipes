package favourite.recipes.exception;

import favourite.recipes.dto.ResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionControllerAdvisor {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ResponseDto> handleValidationException(BadRequestException exception) {
        log.error("BadRequest exception occurred with errors: {}", exception.getMsg());
        return new ResponseEntity<>(new ResponseDto(exception.getCode(), exception.getMsg()), HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<ResponseDto> handleElementAlreadyExistException(AlreadyExistsException exception) {
        log.error("Already Exists exception occurred with errors: {}", exception.getMsg());
        return new ResponseEntity<>(new ResponseDto(exception.getCode(), exception.getMsg()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ResponseDto> handleNotFoundException(NotFoundException exception) {
        log.error("Not Found exception occurred with errors: {}", exception.getMsg());
        return new ResponseEntity<>(new ResponseDto(exception.getCode(), exception.getMsg()), HttpStatus.NOT_FOUND);
    }
}
