package me.iolsh.homepage.web.controllers;

import me.iolsh.homepage.repositories.RoleRepository;
import me.iolsh.homepage.repositories.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.Assert.*;

public class UserControllerTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private RoleRepository userRoleRepository;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private PasswordEncoder passwordEncoder;

    UserController controller;

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        controller = new UserController(userRepository, userRoleRepository, authenticationManager, passwordEncoder);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void processRegistration() {

        Home

    }

    @Test
    public void register() {
    }

    @Test
    public void login() {
    }

    private void doRegister() {

    }
}