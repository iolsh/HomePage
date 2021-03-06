package me.iolsh.homepage.web.controllers;

import lombok.extern.slf4j.Slf4j;
import me.iolsh.homepage.model.Role;
import me.iolsh.homepage.model.User;
import me.iolsh.homepage.repositories.RoleRepository;
import me.iolsh.homepage.repositories.UserRepository;
import me.iolsh.homepage.web.command.HomePageUser;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Slf4j
@Controller
public class UserController {

    private UserRepository userRepository;
    private RoleRepository userRoleRepository;
    private AuthenticationManager authenticationManager;
    private PasswordEncoder passwordEncoder;

    public UserController(UserRepository userRepository, RoleRepository userRoleRepository,
        AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping(value = "/register")
    String processRegistration(@Valid HomePageUser user, Errors errors, HttpServletRequest request) {
        if (errors.hasErrors()) {
            return "/register";
        }
        User newUser = new User(user.getUserName(), passwordEncoder.encode(user.getPassword()),
                user.getFirstName(), user.getLastName(), user.getEmail());
        newUser = userRepository.save(newUser);
        userRoleRepository.save(new Role(newUser, Role.Roles.USER));
        return doAutoLogin(user, request);
    }

    @GetMapping(value = "/register")
    public String register(Model model) {
        model.addAttribute(new HomePageUser());
        return "register";
    }

    @RequestMapping(value = "/login")
    public String login() {
        return "login";
    }

    private String doAutoLogin(HomePageUser user, HttpServletRequest request) {
        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword());
        token.setDetails(new WebAuthenticationDetails(request));
        Authentication authentication = authenticationManager.authenticate(token);
        if (authentication.isAuthenticated()) {
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return  "redirect:/";
        } else {
            return "redirect:/error";
        }
    }
}
