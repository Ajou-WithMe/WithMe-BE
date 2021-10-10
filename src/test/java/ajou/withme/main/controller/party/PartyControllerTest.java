package ajou.withme.main.controller.party;

import ajou.withme.main.BaseControllerTest;
import ajou.withme.main.dto.party.request.CreatePartyRequest;
import ajou.withme.main.dto.user.request.LoginWithEmailDto;
import ajou.withme.main.dto.user.request.SignUpWithEmailDto;
import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

class PartyControllerTest  extends BaseControllerTest {

    @Value("${TOKEN}")
    String token;

    @Test
    @DisplayName("create Party")
    void createParty() throws Exception {
        // Given
        CreatePartyRequest createPartyRequest = new CreatePartyRequest(null, "partyName");
        // When
        ResultActions resultActions = this.mockMvc.perform(post("/party")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(createPartyRequest))
                .header("AccessToken", token)
        );
        // Then
        resultActions
                .andExpect(jsonPath("success").value(true))
                .andExpect(jsonPath("status").value(201))
        ;
    }

}