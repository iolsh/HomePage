package me.iolsh.homepage.web.controllers;

import me.iolsh.homepage.model.User;
import me.iolsh.homepage.repositories.RoleRepository;
import me.iolsh.homepage.repositories.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private RoleRepository userRoleRepository;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserController controller;

    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        //controller = new UserController(userRepository, userRoleRepository, authenticationManager, passwordEncoder);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void processRegistration() throws Exception {

        User mockUser =  mock(User.class);
        UsernamePasswordAuthenticationToken mockToken = mock(UsernamePasswordAuthenticationToken.class);

        when(userRepository.save(any())).thenReturn(mockUser);
        when(authenticationManager.authenticate(any())).thenReturn(mockToken);
        when(mockToken.isAuthenticated()).thenReturn(true);

        mockMvc.perform(
                post("/register/")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("firstName", "Test")
                .param("lastName", "User")
                .param("userName", "test_user")
                .param("email", "test@user.com")
                .param("password", "password")
        )
        .andExpect(status().isFound())
        .andExpect(view().name("redirect:/"))
        .andExpect(model().attributeExists("homePageUser"))
        .andExpect(model().hasNoErrors())

        ;
    }

    @Test
    public void testRegistrationFailed() throws Exception {
        mockMvc.perform(
                post("/register/")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("firstName", "Test")
                        .param("lastName", "User")
                        .param("email", "test@user.com")
                        .param("password", "password")
                )
                .andExpect(status().isOk())
                .andExpect(view().name("/register"))
                .andExpect(model().attributeExists("homePageUser"))
                .andExpect(model().hasErrors())
                .andExpect(model().attributeErrorCount("homePageUser", 1))
                .andExpect(model().attributeHasFieldErrorCode("homePageUser", "userName", "NotNull"));
    }

    @Test
    public void register() throws Exception {
        mockMvc.perform(get("/register/"))
                .andExpect(status().isOk())
                .andExpect(view().name("register"));
    }

    @Test
    public void login() throws  Exception{
        mockMvc.perform(get("/login/"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"));

        mockMvc.perform(post("/login/").contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("username", "any")
                .param("password", "any")
        )
        .andExpect(status().isOk());

    }
}