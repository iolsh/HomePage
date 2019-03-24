package me.iolsh.homepage.web.controllers;

import me.iolsh.homepage.model.Role;
import me.iolsh.homepage.model.User;
import me.iolsh.homepage.repositories.RoleRepository;
import me.iolsh.homepage.repositories.UserRepository;
import me.iolsh.homepage.web.command.HomePageUser;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
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
    @Ignore
    public void processRegistration() throws Exception {
        HomePageUser webUser = newTestUser();
        User user = getUser(webUser);
        Role role = new Role(user, Role.Roles.USER);
        //user.getRoles().add(role);
        //Role spyRole = Mockito.spy(new Role(any(User.class), Role.Roles.USER));
        Mockito.doReturn(role).when(new Role(user, Role.Roles.USER));

        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword());

        when(authenticationManager.authenticate(token)).thenReturn(token);
        //when(userRepository.save(user)).thenReturn(user);


        mockMvc.perform(
                post("/register/")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("firstName", "Test")
                .param("lastName", "User")
                .param("userName", "test_user")
                .param("email", "test@user.com")
                .param("password", "password")
        )
        .andExpect(status().isOk())
        .andExpect(view().name("/register"))
        .andExpect(model().attributeExists("homePageUser"))
        //.andExpect(model().hasErrors())

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


    private  HomePageUser newTestUser() {
        HomePageUser newUser = new HomePageUser();
        //newUser.setId(1l);
        newUser.setFirstName("Test");
        newUser.setLastName("User");
        newUser.setEmail("test@user.com");
        newUser.setUserName("test_user");
        newUser.setPassword("password");
       return newUser;
    }

    private User getUser(HomePageUser webUser) {
        User user = new User(webUser.getUserName(), webUser.getPassword(), webUser.getFirstName(),
            webUser.getLastName(), webUser.getEmail());
        user.setId(1L);
        return user;
    }

}