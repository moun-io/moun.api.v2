package io.moun.api.controller;

import io.moun.api.member.controller.dto.RegisterRequest;
import io.moun.api.security.service.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class AuthControllerTests {

    @Autowired
    private MockMvc mockMvc;

    private String registerUrl = "/auth/register";
    @Autowired
    private AuthService authService;

    @BeforeEach
    void setUp() {
        RegisterRequest existingUser = new RegisterRequest();
        existingUser.setUsername("existinguser");
        existingUser.setPassword("password123");
        authService.registerAuth(existingUser);
    }

    // 회원가입 성공 테스트
    @Test
    public void testRegisterSuccess() throws Exception {
        // 정상적인 회원가입 데이터
        String requestBody = "{\"username\": \"newuser\", \"password\": \"password123\"}";

        mockMvc.perform(MockMvcRequestBuilders
                        .post(registerUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated()); // 201 상태 코드 확인
//                .andExpect(MockMvcResultMatchers.content().string("User successfully registered!"));
    }

    // 이미 존재하는 사용자로 회원가입 시도
    @Test
    public void testRegisterUsernameTaken() throws Exception {
        // 이미 존재하는 사용자명으로 데이터 전송
        String requestBody = "{\"username\": \"existinguser\", \"password\": \"password123\"}";

        mockMvc.perform(MockMvcRequestBuilders
                        .post(registerUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest()); // 400 상태 코드 확인
//                .andExpect(MockMvcResultMatchers.content().string("username is taken!"));
    }

    // 잘못된 요청 형식으로 회원가입 시도 (예: 필수 필드 빠짐)
    @Test
    public void testRegisterInvalidRequest() throws Exception {
        // 잘못된 데이터 (password가 없음)
        String requestBody = "{\"username\": \"invaliduser\"}";

        mockMvc.perform(MockMvcRequestBuilders
                        .post(registerUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest()); // 400 상태 코드 확인
    }
}
