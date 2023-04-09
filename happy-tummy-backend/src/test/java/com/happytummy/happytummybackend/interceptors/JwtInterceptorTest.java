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

//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
//            throws Exception {
//        System.out.println("interceptor called");
//        String header = request.getHeader(AUTHORIZATION_HEADER);
//
//        if (header != null && header.startsWith(BEARER_PREFIX)) {
//            String token = header.substring(BEARER_PREFIX.length());
//            try {
//                String userId = jwtUtils.validateToken(token);
//                if (userId == null) {
//                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//                    return false;
//                }
//                System.out.println("userId: " + userId);
//                request.setAttribute("claims", userId);
//            } catch (Exception e) {
//                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//                return false;
//            }
//        } else {
//            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//            return false;
//        }
//        return true;
//    }
//}

    @Test
    public void testPreHandleWithValidToken() throws Exception {
        String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxMSIsImlhdCI6MTY4MDE0MjkzMSwiZXhwIjoxNjgxMDA2OTMxfQ.QcLJBYtgbMbKqF0SZnxA3uWYO7eBvpNkVk0Rug88y_nX0UVlUeQ6fkVL7ezyADMdQyQeoNODBrgymXVfYJ6zJA";
        when(request.getHeader("Authorization")).thenReturn("Bearer " + token);
        when(jwtUtils.validateToken(token)).thenReturn("user-id");
        assertTrue(jwtInterceptor.preHandle(request, response, null));
    }

    @Test
    public void testPreHandleWithValidTokenWithNull() throws Exception {
        String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxMSIsImlhdCI6MTY4MDE0MjkzMSwiZXhwIjoxNjgxMDA2OTMxfQ.QcLJBYtgbMbKqF0SZnxA3uWYO7eBvpNkVk0Rug88y_nX0UVlUeQ6fkVL7ezyADMdQyQeoNODBrgymXVfYJ6zJA";
        when(request.getHeader("Authorization")).thenReturn("Bearer " + token);
        when(jwtUtils.validateToken(token)).thenReturn(null);
        assertFalse(jwtInterceptor.preHandle(request, response, null));
    }

    @Test
    public void testPreHandleWithInvalidToken() throws Exception {
        String token = "invalid-token";
        when(request.getHeader("Authorization")).thenReturn("Bearer " + token);
        when(jwtUtils.validateToken(token)).thenThrow(new RuntimeException());

        assertFalse(jwtInterceptor.preHandle(request, response, null));
        verify(response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }

    @Test
    public void testPreHandleWithNoAuthorizationHeader() throws Exception {
        assertFalse(jwtInterceptor.preHandle(request, response, null));
        verify(response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }

    @Test
    public void testPreHandleWithInvalidAuthorizationHeader() throws Exception {
        when(request.getHeader("Authorization")).thenReturn("Invalid Header");

        assertFalse(jwtInterceptor.preHandle(request, response, null));
        verify(response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }
}
