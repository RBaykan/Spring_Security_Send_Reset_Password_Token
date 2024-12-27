# Spring Security Send Reset Password Token

## Description
- This project is a continuation of the link provided: https://github.com/RBaykan/Spring_Security_User_Password_Encode

- First, all tokens are now generated from an entity called Token. This is an abstract class and all subclasses of this class are stored as separate tables in the database using `@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)`.
  Authentication and password reset tokens share the same storage and service layers.
