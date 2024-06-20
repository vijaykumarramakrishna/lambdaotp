package org.example;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.DeleteItemOutcome;

public class VerifyOTP {

    private static final String TABLE_NAME = "OTPs";

    private final DynamoDB dynamoDB;

    public VerifyOTP() {
        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().build();
        this.dynamoDB = new DynamoDB(client);
    }

    public boolean verifyOTP(String email, String otp) {
        Table table = dynamoDB.getTable(TABLE_NAME);
        Item item = table.getItem("email", email, "otp", otp);

        if (item != null) {
            long expirationTime = item.getLong("expirationTime");
            if (System.currentTimeMillis() / 1000 < expirationTime) {
                DeleteItemOutcome outcome = table.deleteItem("email", email, "otp", otp);
                return true;
            }
        }

        return false;
    }
}