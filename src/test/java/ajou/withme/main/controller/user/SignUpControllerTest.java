package ajou.withme.main.controller.user;

import ajou.withme.main.BaseControllerTest;
import ajou.withme.main.dto.user.request.SignUpProtectionRequest;
import ajou.withme.main.dto.user.request.SignUpWithEmailDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

class SignUpControllerTest extends BaseControllerTest {

    @Test
    @DisplayName("signUpWithEmail")
    void signupWithEmail() throws Exception {
        // Given
        SignUpWithEmailDto signUpWithEmailDto = new SignUpWithEmailDto("testName", "test123@naver.com", "testpwd123", "수원시 테스트구 테스트동", "01012345678");
        // When
        ResultActions resultActions = this.mockMvc.perform(post("/user/signup/email")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(signUpWithEmailDto))
        );
        // Then
        resultActions
                .andExpect(jsonPath("success").value(true))
                .andExpect(jsonPath("status").value(201))
                .andExpect(jsonPath("data").value("회원가입을 완료했습니다."))
        ;
    }

//    @Test
//    @DisplayName("signUpWithEmail")
//    void signUpProtection() throws Exception {
//        // Given
//        SignUpProtectionRequest signUpProtectionRequest = new SignUpProtectionRequest("testName", "test@naver.com", "수원시 테스트구 테스트동", "testpwd123",  code, "null");
//        // When
//        ResultActions resultActions = this.mockMvc.perform(post("/user/signup/email")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(this.objectMapper.writeValueAsString(signUpWithEmailDto))
//        );
//        // Then
//        resultActions
//                .andExpect(jsonPath("success").value(true))
//                .andExpect(jsonPath("status").value(201))
//                .andExpect(jsonPath("data").value("회원가입을 완료했습니다."))
//        ;
//    }
}