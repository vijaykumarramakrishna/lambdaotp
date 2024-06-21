# OTP Service using Lambda, SES and DynamoDB written in Java

## Solution Design
<img width="641" alt="Screenshot 2024-06-20 at 4 04 16 PM" src="https://github.com/vijaykumarramakrishna/lambdaotp/assets/114371379/1fb9fd07-5760-4d38-8d83-da5f5113a4d7">

### Solution
```
Database:
Create a table OTPs in DynamoDB

Functions:
- GenerateOTP
- SendEmail
- VerifyOTP

Handler: OTPHandler to handle the requests.
```
