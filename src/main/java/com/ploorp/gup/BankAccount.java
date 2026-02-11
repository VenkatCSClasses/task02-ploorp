package com.ploorp.gup;

public class BankAccount {
    private final String email;
    private double balance;

    /**
     * Constructor for BankAccount.
     * @param email The email address associated with the account.
     * @param balance The initial balance of the account.
     * @throws IllegalArgumentException if email or balance is invalid.
     */
    public BankAccount(String email, double balance) {
        if (!isEmailValid(email)) {
            throw new IllegalArgumentException("Invalid email format");
        }
        if (!isAmountValid(balance)) {
            throw new IllegalArgumentException("Invalid initial balance");
        }
        this.email = email;
        this.balance = balance;
    }

    /**
     * Gets the current balance.
     * @return The current balance.
     */
    public double getBalance() {
        return balance;
    }

    /**
     * Gets the email address.
     * @return The email address.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Deposits an amount into the account.
     * @param amount The amount to deposit.
     * @return The new balance.
     * @throws IllegalArgumentException if the amount is invalid.
     */
    public double deposit(double amount) {
        if (!isAmountValid(amount)) {
            throw new IllegalArgumentException("Invalid deposit amount");
        }
        balance += amount;
        return balance;
    }

    /**
     * Withdraws an amount from the account.
     * @param amount The amount to withdraw.
     * @return The new balance.
     * @throws IllegalArgumentException if the amount is invalid.
     * @throws InsufficientFundsException if the balance is insufficient.
     */
    public double withdraw(double amount) {
        if (!isAmountValid(amount)) {
            throw new IllegalArgumentException("Invalid withdrawal amount");
        }
        if (balance < amount) {
            throw new InsufficientFundsException("Insufficient funds for withdrawal");
        }
        balance -= amount;
        balance = Math.round(balance * 100.0) / 100.0;
        return balance;
    }

    /**
     * Transfers an amount to another bank account.
     * @param amount The amount to transfer.
     * @param target The target bank account.
     * @throws IllegalArgumentException if the amount is invalid or target is null.
     * @throws InsufficientFundsException if the balance is insufficient.
     */
    public void transfer(double amount, BankAccount target) {
        if (target == null) {
            throw new IllegalArgumentException("Target account cannot be null");
        }
        if (!isAmountValid(amount)) {
            throw new IllegalArgumentException("Invalid transfer amount");
        }
        this.withdraw(amount);
        target.deposit(amount);
    }

    /**
     * Validates if the email format is correct according to specific rules.
     * @param email The email string to validate.
     * @return true if valid, false otherwise.
     */
    public static boolean isEmailValid(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }

        int atIndex = email.indexOf('@');
        if (atIndex == -1 || atIndex != email.lastIndexOf('@')) {
            return false;
        }

        String localPart = email.substring(0, atIndex);
        String domainPart = email.substring(atIndex + 1);

        if (localPart.isEmpty() || domainPart.isEmpty()) {
            return false;
        }

        if (localPart.length() > 64) {
            return false;
        }

        if (localPart.startsWith(".") || localPart.endsWith(".") || 
            localPart.startsWith("-") || localPart.endsWith("-")) {
            return false;
        }
        if (localPart.contains("..")) {
            return false;
        }

        for (char c : localPart.toCharArray()) {
            if (!Character.isLetterOrDigit(c) && 
                "#+!%_&*.-".indexOf(c) == -1) {
                return false;
            }
        }

        if (domainPart.startsWith(".") || domainPart.endsWith(".") || 
            domainPart.startsWith("-") || domainPart.endsWith("-")) {
            return false;
        }
        if (domainPart.contains("..")) {
            return false;
        }

        String[] domainSegments = domainPart.split("\\.");
        if (domainSegments.length < 2) {
            return false;
        }

        for (int i = 0; i < domainSegments.length - 1; i++) {
            String segment = domainSegments[i];
            if (segment.isEmpty()) return false;
            for (char c : segment.toCharArray()) {
                if (!Character.isLetterOrDigit(c) && c != '-') {
                    return false;
                }
            }
            if (segment.startsWith("-") || segment.endsWith("-")) {
                return false;
            }
        }

        String tld = domainSegments[domainSegments.length - 1];
        if (tld.length() < 2 || tld.length() > 13) {
            return false;
        }
        for (char c : tld.toCharArray()) {
            if (!Character.isLetter(c)) {
                return false;
            }
        }

        return true;
    }

    /**
     * Validates if the amount is non-negative and has at most 2 decimal places.
     * @param amount The amount to validate.
     * @return true if valid, false otherwise.
     */
    public static boolean isAmountValid(double amount) {
        if (amount < 0) {
            return false;
        }
        double multiplied = amount * 100;
        double tolerance = 0.000001;
        double remainder = Math.abs(multiplied - Math.round(multiplied));
        return remainder < tolerance;
    }
}
