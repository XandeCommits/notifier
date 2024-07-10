package unit.integration;

import com.modak.notifier.NotifierApplication;
import com.modak.notifier.domain.EmailFactory;
import com.modak.notifier.domain.models.MarketingEmail;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = NotifierApplication.class)
@AutoConfigureMockMvc
public class MarketingEmailIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmailFactory emailFactory;

    private ResultActions postEmail(String userId, String requestBody) throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders.post("/{user-id}/send-email", userId)
                .contentType("application/json")
                .content(requestBody)
        );
    }

    @Test
    public void testSendEmailEndpoint() throws Exception {
        String userId = UUID.randomUUID().toString();
        String requestBody = "{\"emailType\": \"Marketing\", \"emailContent\": \"Lorem Ipsum\"}";

        Instant mockedSentAt = Instant.parse("2024-07-08T12:00:00Z");
        Instant mockedSentAt2 = mockedSentAt.plus(10, ChronoUnit.MINUTES);
        Instant mockedSentAt3 = mockedSentAt.plus(20, ChronoUnit.MINUTES);
        Instant mockedSentAt4 = mockedSentAt.plus(30, ChronoUnit.MINUTES);
        Instant mockedSentAt5 = mockedSentAt.plus(61, ChronoUnit.MINUTES);

        Mockito.when(emailFactory.newEmail(Mockito.any(), Mockito.anyString(), Mockito.any()))
                .thenReturn(new MarketingEmail("First Email", mockedSentAt))
                .thenReturn(new MarketingEmail("Second Email", mockedSentAt2))
                .thenReturn(new MarketingEmail("Third E-mail", mockedSentAt3))
                .thenReturn(new MarketingEmail("Spam E-mail", mockedSentAt4))
                .thenReturn(new MarketingEmail("Forth E-mail", mockedSentAt5));

        postEmail(userId, requestBody)
                .andExpect(MockMvcResultMatchers.status().isOk());

        postEmail(userId, requestBody)
                .andExpect(MockMvcResultMatchers.status().isOk());

        postEmail(userId, requestBody)
                .andExpect(MockMvcResultMatchers.status().isOk());

        postEmail(userId, requestBody)
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.TOO_MANY_REQUESTS.value()));

        postEmail(userId, requestBody)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testSendEmailEndpointMultiUser() throws Exception {
        String firstUser = UUID.randomUUID().toString();
        String secondUser = UUID.randomUUID().toString();
        String requestBody = "{\"emailType\": \"Marketing\", \"emailContent\": \"Lorem Ipsum\"}";

        Instant mockedSentAt = Instant.parse("2024-07-08T12:00:00Z");
        Instant mockedSentAt2 = mockedSentAt.plus(10, ChronoUnit.MINUTES);
        Instant mockedSentAt3 = mockedSentAt.plus(20, ChronoUnit.MINUTES);
        Instant mockedSentAt4 = mockedSentAt.plus(30, ChronoUnit.MINUTES);

        Mockito.when(emailFactory.newEmail(Mockito.any(), Mockito.anyString(), Mockito.any()))
                .thenReturn(new MarketingEmail("First Email", mockedSentAt))
                .thenReturn(new MarketingEmail("Second Email", mockedSentAt2))
                .thenReturn(new MarketingEmail("Third E-mail", mockedSentAt3))
                .thenReturn(new MarketingEmail("Forth E-mail", mockedSentAt4))
                .thenReturn(new MarketingEmail("Spam E-mail", mockedSentAt4));

        postEmail(firstUser, requestBody)
                .andExpect(MockMvcResultMatchers.status().isOk());

        postEmail(firstUser, requestBody)
                .andExpect(MockMvcResultMatchers.status().isOk());

        postEmail(firstUser, requestBody)
                .andExpect(MockMvcResultMatchers.status().isOk());

        postEmail(secondUser, requestBody)
                .andExpect(MockMvcResultMatchers.status().isOk());

        postEmail(firstUser, requestBody)
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.TOO_MANY_REQUESTS.value()));
    }
}
