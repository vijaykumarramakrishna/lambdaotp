package org.example;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.amazonaws.services.simpleemail.model.*;

public class SendEmail {

    private static final String FROM = "your-email@example.com";

    private final AmazonSimpleEmailService sesClient;

    public SendEmail() {
        this.sesClient = AmazonSimpleEmailServiceClientBuilder.standard().build();
    }

    public void sendOTP(String email, String otp) {
        String subject = "Your OTP Code";
        String body = "Your OTP code is: " + otp;

        SendEmailRequest request = new SendEmailRequest()
                .withDestination(new Destination().withToAddresses(email))
                .withMessage(new Message()
                        .withBody(new Body().withText(new Content().withCharset("UTF-8").withData(body)))
                        .withSubject(new Content().withCharset("UTF-8").withData(subject)))
                .withSource(FROM);

        sesClient.sendEmail(request);
    }
}
