# Spring Security Send Reset Password Token

## Description
- This project is a continuation of the link provided: https://github.com/RBaykan/Spring_Security_User_Password_Encode

- First, all tokens are now generated from an entity called Token. This is an abstract class and all subclasses of this class are stored as separate tables in the database using `@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)`.
  Authentication and password reset tokens share the same storage and service layers.

- A Reset Password API is called with an email parameter through a GET request. The ResetPassword event is triggered, and if the user with the provided email exists in the database,
  a ResetPassword Token is generated and sent to the user's email. Although the link will include the token as a parameter, it currently needs to be manually defined. The link should
  include the email and the new password as parameters.

## Steps to Run the Project
1. **Clone the repository**:
```bash
git clone https://github.com/RBaykan/Spring_Security_Register_Email_Verification_Token.git
```
2. **Navigate to the project directory**:
```bash
cd Spring_Security_Register_Email_Verification_Token
```
3. **Dependencies and build the project**:
```bash
mvn clean install
```
- Run the application:
```bash
mvn spring-boot:run
```
The application should now be running on `http://localhost:8080`

4. Send a `GET` request to the `http://localhost:8080/api/user/resetPassword` API with the email parameter.
  If a user exists with this email, a password reset token will be sent to that user's email in the form of a link for confirmation.

5. This link is missing. You need to manually define its parameters. Set the user's email and new password as URL parameters along with the token, and then send a `POST` request to `http://localhost:8080/api/user/resetPassword`.
