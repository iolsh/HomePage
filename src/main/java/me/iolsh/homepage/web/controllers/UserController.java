package me.iolsh.homepage.web.controllers;

import me.iolsh.homepage.model.Role;
import me.iolsh.homepage.model.User;
import me.iolsh.homepage.repositories.RoleRepository;
import me.iolsh.homepage.repositories.UserRepository;
import me.iolsh.homepage.web.command.HomePageUser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
public class UserController {

    private UserRepository userRepository;
    private RoleRepository userRoleRepository;
    private AuthenticationManager authenticationManager;

    public UserController(UserRepository userRepository, RoleRepository userRoleRepository,
        AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.authenticationManager = authenticationManager;
    }

    @Value("${homepage.char_secret}")
    private String char_secret;


    @RequestMapping(value = "/register", method = POST)
    String processRegistration(@Valid HomePageUser user, Errors errors, HttpServletRequest request) {
        if (errors.hasErrors()) {
            return "register";
        }
        User newUser = new User(user.getUserName(), new StandardPasswordEncoder(char_secret).encode(user.getPassword()),
                user.getFirstName(), user.getLastName(), user.getEmail());
        newUser = userRepository.save(newUser);
        userRoleRepository.save(new Role(newUser, Role.Roles.USER));
        return doAutoLogin(newUser, request);
    }

    @RequestMapping(value = "/register", method = GET)
    public String register(Model model) {
        model.addAttribute(new HomePageUser());
        return "register";
    }

    @RequestMapping(value = "/login")
    public String login() {
        return "login";
    }



    private String doAutoLogin(User user, HttpServletRequest request) {
        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword());
        token.setDetails(request.getRemoteAddr());
        token = (UsernamePasswordAuthenticationToken) authenticationManager.authenticate(token);
        if (token.isAuthenticated()) {
            SecurityContextHolder.getContext().setAuthentication(token);
            return  "redirect:/";
        } else {
            return "redirect:/error";
        }
    }



}
