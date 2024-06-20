package org.example;

import java.util.Random;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.Item;

public class OTPGenerator {

    // use final variables to ensure immutability

    private static final long EXPIRATION_TIME = 5 * 60 * 1000;
    private static final Random random = new Random();
    private static final String TABLE_NAME = "OTPs";

    private final DynamoDB dynamoDB;

    public OTPGenerator() {
        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().build();
        this.dynamoDB = new DynamoDB(client);
    }

    public String generateOTP(String email) {
        String otp = String.format("%06d", random.nextInt(999999));
        long expirationTime = (System.currentTimeMillis() + EXPIRATION_TIME) / 1000; // in seconds

        Table table = dynamoDB.getTable(TABLE_NAME);
        Item item = new Item()
                .withPrimaryKey("email", email)
                .withString("otp", otp)
                .withNumber("expirationTime", expirationTime);

        PutItemOutcome outcome = table.putItem(item);

        return otp;
    }

}
