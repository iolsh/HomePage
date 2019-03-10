package me.iolsh.homepage.web.controllers;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ErrorHandlers {
    @ExceptionHandler(RuntimeException.class)
    public ModelAndView error(Exception ex) {
        ex.printStackTrace();
        ModelAndView mav = new ModelAndView("error");
        mav.addObject("cause", ex);
        return mav;
    }
}
