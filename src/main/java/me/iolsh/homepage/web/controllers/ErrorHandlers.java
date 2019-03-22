package me.iolsh.homepage.web.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@ControllerAdvice
@Slf4j
public class ErrorHandlers {

    //cannot create object ibn database
    @ExceptionHandler(DataIntegrityViolationException.class)
    public String conflict(Exception e, HttpServletResponse resp) throws IOException {
        log.error(e.getMessage());
        resp.sendError(HttpStatus.CONFLICT.value());
        return null;
    }

    //global
    @ExceptionHandler(RuntimeException.class)
    public ModelAndView error(Exception ex) {
        log.error(ex.getMessage());
        ModelAndView mav = new ModelAndView("error");
        mav.addObject("cause", ex);
        return mav;
    }
}
