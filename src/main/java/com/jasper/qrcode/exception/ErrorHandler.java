package com.jasper.qrcode.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class ErrorHandler {
    /**
     * 输入参数校验异常
     */
    @ExceptionHandler(value = CommonException.class)
    public ResponseEntity<ErrorResult> NotValidExceptionHandler(HttpServletRequest req, CommonException e) throws Exception {
    	ErrorResult res = new ErrorResult(e.getStatus().value(), e.getErrorCode().getComment(), e.getErrorCode().value());
        return new ResponseEntity<ErrorResult>(res, e.getStatus());
    }

    /**
     * 404异常处理
     */
    @ExceptionHandler(value = NoHandlerFoundException.class)
    public ResponseEntity<ErrorResult> NoHandlerFoundExceptionHandler(HttpServletRequest req, Exception e) throws Exception {

        ErrorResult res = new ErrorResult(404, "页面不存在");
        return new ResponseEntity<ErrorResult>(res, HttpStatus.NOT_FOUND);
    }

    /**
     *  默认异常处理，前面未处理
     */
    @ExceptionHandler(value = Throwable.class)
    public ResponseEntity<ErrorResult> defaultHandler(HttpServletRequest req, Exception e) throws Exception {

        ErrorResult res = new ErrorResult(500, "服务器内部错误");

        return new ResponseEntity<ErrorResult>(res, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
