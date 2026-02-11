# Bank Account with Validation

A simple Java BankAccount implementation with comprehensive email and balance validation.

## Description
This project implements a BankAccount class with the following features:
- Email validation based on specific rules (length, format, allowed characters).
- Balance and transaction amount validation (non-negative, max 2 decimal places).
- Basic banking operations: deposit, withdraw, transfer.
- Custom exception handling for insufficient funds.

## Installation and Usage

1. **Prerequisites**: Java 17, Maven.
2. **Build the project**:
   ```sh
   mvn clean install
   ```
3. **Run Tests**:
   ```sh
   mvn test
   ```

## Structure
- `src/main/java/com/ploorp/gup/BankAccount.java`: Main class.
- `src/test/java/com/ploorp/gup/BankAccountTest.java`: Logic tests.
