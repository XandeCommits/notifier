package unit.com.modak.notifier.domain;

import com.modak.notifier.domain.EmailFactory;
import com.modak.notifier.domain.models.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

public class EmailFactoryTest {

    private EmailFactory emailFactory;

    @BeforeEach
    public void setup(){
        this.emailFactory = new EmailFactory();
    }

    @Test
    public void testNewEmail_StatusEmail() {

        Email email = emailFactory.newEmail(
                EmailTypeEnum.STATUS,
                "Status email content",
                Instant.now()
        );

        assertInstanceOf(StatusEmail.class, email);
    }

    @Test
    public void testNewEmail_NewsEmail() {

        Email email = emailFactory.newEmail(
                EmailTypeEnum.NEWS,
                "News email content",
                Instant.now()
        );

        assertInstanceOf(NewsEmail.class, email);
    }

    @Test
    public void testNewEmail_MarketingEmail() {

        Email email = emailFactory.newEmail(
                EmailTypeEnum.MARKETING,
                "Marketing email content",
                Instant.now()
        );

        assertInstanceOf(MarketingEmail.class, email);
    }
}
