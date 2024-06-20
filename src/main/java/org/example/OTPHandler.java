package org.example;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import java.util.Map;

public class OTPHandler implements RequestHandler<Map<String, String>, String> {

    private final OTPGenerator otpGenerator = new OTPGenerator();
    private final SendEmail sendEmail = new SendEmail();
    private final VerifyOTP verifyOTP = new VerifyOTP();

    @Override
    public String handleRequest(Map<String, String> event, Context context) {
        String action = event.get("action");
        String email = event.get("email");

        if ("generate".equalsIgnoreCase(action)) {
            String otp = otpGenerator.generateOTP(email);
            sendEmail.sendOTP(email, otp);
            return "OTP sent to " + email;
        }
        else if ("verify".equalsIgnoreCase(action)) {
            String otp = event.get("otp");
            boolean isValid = verifyOTP.verifyOTP(email, otp);
            return isValid ? "OTP is valid" : "OTP is invalid or expired";
        }
        else {
            return "Invalid action";
        }
    }
}