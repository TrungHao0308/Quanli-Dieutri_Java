package com.example.website;

import com.example.website.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
class WebsiteApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Test
    void contextLoads() {
    }

    @Test
    void testRegisterSuccess() throws Exception {
        mockMvc.perform(post("/register")
                .param("firstName", "John")
                .param("lastName", "Doe")
                .param("email", "john.doe@example.com")
                .param("phone", "+84912345678")
                .param("birthdate", "2000-01-01")
                .param("gender", "male")
                .param("password", "password123")
                .param("confirmPassword", "password123")
                .param("terms", "true"))
            .andExpect(status().is3xxRedirection())
            .andExpect(view().name("redirect:/loginpage"));
    }

    @Test
    void testLoginPageAccess() throws Exception {
        mockMvc.perform(get("/loginpage"))
            .andExpect(status().isOk())
            .andExpect(view().name("loginPage"));
    }
}