package ajou.withme.main.controller.user;

import ajou.withme.main.Service.AuthService;
import ajou.withme.main.Service.MailService;
import ajou.withme.main.Service.UserService;
import ajou.withme.main.dto.user.LoginWithEmailDto;
import ajou.withme.main.dto.user.SignUpWithEmailDto;
import ajou.withme.main.util.JwtTokenInterceptor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@WebMvcTest(controllers = LoginController.class)
class LoginControllerTest {

    private final MockMvc mvc;
    private final ObjectMapper objectMapper;

    @MockBean
    private AuthService authService;
    @MockBean
    private UserService userService;
    @MockBean
    private MailService mailService;
    @MockBean
    private JwtTokenInterceptor jwtTokenInterceptor;

    @Autowired
    public LoginControllerTest( MockMvc mvc, ObjectMapper objectMapper) {
        this.mvc = mvc;
        this.objectMapper = objectMapper;
    }

    @Test
    public void loginWithEmail() throws Exception {

        String content = objectMapper.writeValueAsString(new LoginWithEmailDto("testEmail@naver.com", "password"));

        ResultActions perform = mvc.perform(MockMvcRequestBuilders.post("/login/email")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        String s = perform.toString();
        System.out.println("s = " + s);
    }

}