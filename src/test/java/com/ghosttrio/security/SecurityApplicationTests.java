package com.ghosttrio.security;

import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.options;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class SecurityApplicationTests {

    MockMvc mvc;
    @Test
    public void test() throws Exception {
        mvc.perform(options("/test")
                .header("Access-Control-Request-Method", "POST")
                .header("Origin", "http://localhost:8080"))
                .andExpect(header().exists("Access-Control-Allow-Origin"))
                .andExpect(header().string("Access-Control-Allow-Origin", "*"))
                .andExpect(header().exists("Access-Control-Allow-Methods"))
                .andExpect(header().string("Access-Control-Allow-Methods", "*"))
                .andExpect(status().isOk());
    }
}
