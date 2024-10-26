package com.samuelcharles.docker_manager.controller;

import com.github.dockerjava.api.model.Container;
import com.jayway.jsonpath.internal.function.text.Concatenate;
import com.samuelcharles.docker_manager.service.DockerManagerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvc.*;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import static org.springframework.test.web.servlet.MockMvc.*;


class DockerContainersControllerTest {

    @Mock
    private DockerManagerService service;

    private MockMvc mvc;

    @InjectMocks
    private DockerContainersController containersController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.mvc = MockMvcBuilders.standaloneSetup(containersController).build();
    }

    @Test
    @DisplayName("")
    void getAllContainersByName() throws Exception {
        List<Container> mockContainers = Collections.emptyList();
        when(service.listContainers(true)).thenReturn(mockContainers);

        mvc.perform(get("/docker/containers"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }
}