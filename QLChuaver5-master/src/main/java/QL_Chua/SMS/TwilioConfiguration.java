package QL_Chua.SMS;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


@Configuration
@ConfigurationProperties("twilio")
@Data
public class TwilioConfiguration {
    @NotBlank
    private String accountSid;
    @NotBlank
    private String authToken;
    @NotBlank
    private String trialNumber;
}
