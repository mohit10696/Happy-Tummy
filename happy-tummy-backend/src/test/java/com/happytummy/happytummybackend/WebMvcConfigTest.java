package com.happytummy.happytummybackend;

import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class WebMvcConfigTest {

    @Inject
    private WebMvcConfig webMvcConfig;

    @Test
    void addInterceptorsTest() {
        InterceptorRegistry registry = new InterceptorRegistry();
        webMvcConfig.addInterceptors(registry);
        assertNotNull(registry);
    }
}