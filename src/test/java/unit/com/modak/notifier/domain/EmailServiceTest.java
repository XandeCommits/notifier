package unit.com.modak.notifier.domain;

import com.modak.notifier.domain.EmailService;
import com.modak.notifier.domain.models.Email;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class EmailServiceTest {

    @Test
    public void testIsSpam_NotSpam() {

        Email email = mock(Email.class);

        when(email.getLimit()).thenReturn(100);

        assertFalse(EmailService.isSpam(email, 50L));
    }

    @Test
    public void testIsSpam_Spam() {

        Email email = mock(Email.class);

        when(email.getLimit()).thenReturn(100);

        assertTrue(EmailService.isSpam(email, 150L));
        assertTrue(EmailService.isSpam(email, 100L));
    }
}
