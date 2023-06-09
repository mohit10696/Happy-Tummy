package com.happytummy.happytummybackend.interceptors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.happytummy.happytummybackend.interceptors.JwtInterceptor;
import com.happytummy.happytummybackend.utils.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.HandlerInterceptor;

@SpringBootTest
public class JwtInterceptorTest {
    @Autowired
    private JwtInterceptor jwtInterceptor;
    private MockMvc mockMvc;
    private HttpServletRequest request;
    private HttpServletResponse response;

    private JwtUtils jwtUtils;

    @BeforeEach
    public void setUp() {
        jwtInterceptor = new JwtInterceptor();
        mockMvc = MockMvcBuilders.standaloneSetup().addInterceptors((HandlerInterceptor) jwtInterceptor).build();
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        jwtUtils = mock(JwtUtils.class);
        jwtInterceptor.jwtUtils = jwtUtils;
    }


    @Test
    public void testPreHandleWithValidToken() throws Exception {
        String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxMSIsImlhdCI6MTY4MDE0MjkzMSwiZXhwIjoxNjgxMDA2OTMxfQ.QcLJBYtgbMbKqF0SZnxA3uWYO7eBvpNkVk0Rug88y_nX0UVlUeQ6fkVL7ezyADMdQyQeoNODBrgymXVfYJ6zJA";
        when(request.getHeader("Authorization")).thenReturn("Bearer " + token);
        when(jwtUtils.validateToken(token)).thenReturn("user-id");
        when(request.getMethod()).thenReturn("GET");
        assertTrue(jwtInterceptor.preHandle(request, response, null));
    }

    @Test
    public void testPreHandleWithValidTokenWithNull() throws Exception {
        String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxMSIsImlhdCI6MTY4MDE0MjkzMSwiZXhwIjoxNjgxMDA2OTMxfQ.QcLJBYtgbMbKqF0SZnxA3uWYO7eBvpNkVk0Rug88y_nX0UVlUeQ6fkVL7ezyADMdQyQeoNODBrgymXVfYJ6zJA";
        when(request.getHeader("Authorization")).thenReturn("Bearer " + token);
        when(request.getMethod()).thenReturn("GET");
        when(jwtUtils.validateToken(token)).thenReturn(null);
        assertFalse(jwtInterceptor.preHandle(request, response, null));
    }

    @Test
    public void testPreHandleWithInvalidToken() throws Exception {
        String token = "invalid-token";
        when(request.getHeader("Authorization")).thenReturn("Bearer " + token);
        when(request.getMethod()).thenReturn("GET");
        when(jwtUtils.validateToken(token)).thenThrow(new RuntimeException());

        assertFalse(jwtInterceptor.preHandle(request, response, null));
        verify(response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }

    @Test
    public void testPreHandleWithNoAuthorizationHeader() throws Exception {
        when(request.getMethod()).thenReturn("GET");
        assertFalse(jwtInterceptor.preHandle(request, response, null));
        verify(response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }

    @Test
    public void testPreHandleWithInvalidAuthorizationHeader() throws Exception {
        when(request.getHeader("Authorization")).thenReturn("Invalid Header");
        when(request.getMethod()).thenReturn("GET");
        assertFalse(jwtInterceptor.preHandle(request, response, null));
        verify(response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }
}
