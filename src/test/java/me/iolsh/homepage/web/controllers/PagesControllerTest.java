package me.iolsh.homepage.web.controllers;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

public class PagesControllerTest {

    PagesController controller;
    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        controller = new PagesController();
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void home() throws Exception {

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("home"));
    }

    @Test
    public void about() throws Exception {
        mockMvc.perform(get("/about/"))
                .andExpect(status().isOk())
                .andExpect(view().name("about"));
    }

    @Test
    public void notes() throws Exception {
        mockMvc.perform(get("/notes/"))
                .andExpect(status().isOk())
                .andExpect(view().name("notes"));
    }
}