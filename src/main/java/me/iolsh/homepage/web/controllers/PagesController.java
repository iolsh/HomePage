package me.iolsh.homepage.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;

@Controller
public class PagesController {

    @RequestMapping("/")
    public ModelAndView home() {
        LocalDateTime time = LocalDateTime.now();
        ModelAndView mav = new ModelAndView("home");
        mav.addObject("time", time);
        return mav;
    }

    @RequestMapping("/about")
    public ModelAndView about() {
        return new ModelAndView("about");
    }

    @RequestMapping("/notes")
    public ModelAndView notes() {
        return new ModelAndView("notes");
    }

}
