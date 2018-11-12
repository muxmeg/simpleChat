package com.demo.simpleChat.controllers.rest;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.demo.simpleChat.services.AuthService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

public class AuthControllerTest {

  private MockMvc mockMvc;

  @Mock
  private AuthService authService;

  @InjectMocks
  private AuthController controller;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
  }

  @Test
  public void authenticateUser() throws Exception {
    when(authService.loginUser("testUser")).thenReturn(true);

    MvcResult result = mockMvc.perform(post("/rest/auth").content("testUser"))
        .andExpect(status().isOk()).andReturn();

    assertTrue(Boolean.parseBoolean(result.getResponse().getContentAsString()));
    verify(authService, times(1)).loginUser("testUser");
    verifyNoMoreInteractions(authService);
  }
}