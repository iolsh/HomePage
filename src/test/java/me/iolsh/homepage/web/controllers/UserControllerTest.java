package me.iolsh.homepage.web.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import me.iolsh.homepage.repositories.RoleRepository;
import me.iolsh.homepage.repositories.UserRepository;
import me.iolsh.homepage.web.command.HomePageUser;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class UserControllerTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private RoleRepository userRoleRepository;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private PasswordEncoder passwordEncoder;

    ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();

    UserController controller;

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        controller = new UserController(userRepository, userRoleRepository, authenticationManager, passwordEncoder);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void processRegistration() throws Exception {
        HomePageUser newUser = newTestUser();
        String json = ow.writeValueAsString(newUser);
        System.out.println(json);
        mockMvc.perform(
                post("/register/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
        )
        .andExpect(status().isOk())
        .andExpect(view().name("/register"))
        .andExpect(model().attributeExists("homePageUser"))
        .andExpect(model().hasErrors())

        ;
    }
//    @Test
//    public void failRegistration() throws Exception {
//        HomePageUser newUser = null;
//        //newUser.setEmail(null);
//
//        ResultActions res = mockMvc.perform(post("/register/", newUser)
//                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
//                .andExpect(status().isOk())
//                .andExpect(view().name("/register"))
//                .andExpect(model().attributeExists("homePageUser"))
//                .andExpect(model().hasErrors())
//                ;//   .andExpect(
//        System.out.println(res);
//
//    }

    @Test
    public void register() throws Exception {
        mockMvc.perform(get("/register/"))
                .andExpect(status().isOk())
                .andExpect(view().name("register"));
    }

    @Test
    public void login() {
    }

    private void doRegister() {

    }

    private  HomePageUser newTestUser() {
        HomePageUser newUser = new HomePageUser();
        newUser.setId(1l);
        newUser.setFirstName("Test");
        newUser.setLastName("User");
        newUser.setEmail("test@user.com");
        newUser.setUserName("test_user");
        newUser.setPassword("password");
       return newUser;
    }
}