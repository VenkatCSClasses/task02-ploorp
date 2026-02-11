package com.ploorp.gup;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BankAccountTest {

    @Test
    public void testConstructor_Valid() {
        BankAccount account = new BankAccount("a@b.com", 200);
        assertEquals("a@b.com", account.getEmail());
        assertEquals(200, account.getBalance(), 0.001);
    }

    @Test
    public void testConstructor_EmptyEmail() {
        assertThrows(IllegalArgumentException.class, () -> new BankAccount("", 100));
    }

    @Test
    public void testConstructor_NegativeBalance() {
        assertThrows(IllegalArgumentException.class, () -> new BankAccount("a@b.com", -50));
    }

    @Test
    public void testIsEmailValid() {
        assertFalse(BankAccount.isEmailValid(""));
        assertFalse(BankAccount.isEmailValid("a@b.c")); // too short
        assertTrue(BankAccount.isEmailValid("a@b.co")); // shortest valid
        
        assertFalse(BankAccount.isEmailValid("@bb.com")); // missing local
        assertFalse(BankAccount.isEmailValid("aa@.com")); // missing domain
        assertFalse(BankAccount.isEmailValid("aa@bb")); // missing tld
        
        assertTrue(BankAccount.isEmailValid("a@b.com")); // local part 1 char
        assertFalse(BankAccount.isEmailValid("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa@b.com")); // 65 chars
        assertTrue(BankAccount.isEmailValid("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa@b.com")); // 64 chars
        
        assertTrue(BankAccount.isEmailValid("aa@bb.com")); // domain 2 chars
        assertFalse(BankAccount.isEmailValid("aa@b.c")); // tld 1 char
        assertTrue(BankAccount.isEmailValid("aa@bb.international")); // tld 13 chars
        assertFalse(BankAccount.isEmailValid("aa@bb.internationala")); // tld 14 chars
        
        assertTrue(BankAccount.isEmailValid("aa@bb.co.uk")); // 2 part tld
        assertTrue(BankAccount.isEmailValid("aa@bb.bb.bb.bb.bb.com")); // subdomains
        
        assertFalse(BankAccount.isEmailValid("a\"()a@b.com")); // invalid quotes/parens
        assertFalse(BankAccount.isEmailValid("a a@b.com")); // invalid spaces
        assertTrue(BankAccount.isEmailValid("#+!%_&*@b.com")); // valid special chars
        
        assertFalse(BankAccount.isEmailValid("aa@b#b.com")); // hash in domain
        assertFalse(BankAccount.isEmailValid("aa@bb.#4")); // hash in tld
        assertTrue(BankAccount.isEmailValid("aa@a-b.com")); // hyphen in domain
        
        assertTrue(BankAccount.isEmailValid("4@bb.com")); // numbers in local
        assertTrue(BankAccount.isEmailValid("aa@4.com")); // numbers in domain
        assertFalse(BankAccount.isEmailValid("aa@bb.44")); // numbers in tld
        
        assertFalse(BankAccount.isEmailValid("-aa@bb.com")); // local starts hyphen
        assertTrue(BankAccount.isEmailValid("a-a@bb.com")); // local middle hyphen
        assertFalse(BankAccount.isEmailValid("aa-@bb.com")); // local ends hyphen
        assertFalse(BankAccount.isEmailValid("aa@-bb.com")); // domain starts hyphen
        assertTrue(BankAccount.isEmailValid("aa@b-b.com")); // domain middle hyphen
        assertFalse(BankAccount.isEmailValid("aa@bb-.com")); // domain ends hyphen
        
        assertFalse(BankAccount.isEmailValid(".aa@bb.com")); // local starts dot
        assertFalse(BankAccount.isEmailValid("aa@.bb.com")); // domain starts dot
        assertFalse(BankAccount.isEmailValid("aa.@bb.com")); // local ends dot
        assertFalse(BankAccount.isEmailValid("aa@bb.com.")); // domain ends dot
        assertTrue(BankAccount.isEmailValid("a.a@bb.com")); // dot in middle local
        
        assertFalse(BankAccount.isEmailValid("a..a@bb.com")); // double dot local
        assertFalse(BankAccount.isEmailValid("aa@bb..com")); // double dot domain
        
        assertFalse(BankAccount.isEmailValid("a@a@b.com")); // extra @
        assertFalse(BankAccount.isEmailValid("aa.bb.com")); // missing @
        
        assertTrue(BankAccount.isEmailValid("Aa@bb.com")); // caps local
        assertTrue(BankAccount.isEmailValid("aa@Bb.com")); // caps domain
        assertTrue(BankAccount.isEmailValid("AA@BB.COM")); // all caps
    }

    @Test
    public void testIsAmountValid() {
        assertTrue(BankAccount.isAmountValid(0));
        assertTrue(BankAccount.isAmountValid(0.01));
        assertTrue(BankAccount.isAmountValid(100));
        assertTrue(BankAccount.isAmountValid(99999999.99));
        
        assertFalse(BankAccount.isAmountValid(-0.01));
        assertFalse(BankAccount.isAmountValid(-100));
        assertFalse(BankAccount.isAmountValid(0.001));
        assertFalse(BankAccount.isAmountValid(100.999));
    }

    @Test
    public void testWithdraw() {
        BankAccount acc = new BankAccount("a@b.com", 200);
        assertEquals(199.99, acc.withdraw(0.01), 0.001);
        
        acc = new BankAccount("a@b.com", 200);
        assertEquals(100, acc.withdraw(100), 0.001);
        
        acc = new BankAccount("a@b.com", 100);
        assertEquals(0.02, acc.withdraw(99.98), 0.001);
        
        acc = new BankAccount("a@b.com", 200);
        assertEquals(200, acc.withdraw(0), 0.001);
        
        acc = new BankAccount("a@b.com", 0.01);
        assertEquals(0, acc.withdraw(0.01), 0.001);
        
        BankAccount acc2 = new BankAccount("a@b.com", 0.01);
        assertThrows(InsufficientFundsException.class, () -> acc2.withdraw(0.02));
        
        BankAccount acc3 = new BankAccount("a@b.com", 100);
        assertThrows(InsufficientFundsException.class, () -> acc3.withdraw(101));
        
        BankAccount acc4 = new BankAccount("a@b.com", 200);
        assertThrows(InsufficientFundsException.class, () -> acc4.withdraw(1000000.0));
        
        BankAccount acc5 = new BankAccount("a@b.com", 0);
        assertThrows(InsufficientFundsException.class, () -> acc5.withdraw(0.01));
        
        BankAccount acc6 = new BankAccount("a@b.com", 200);
        assertThrows(IllegalArgumentException.class, () -> acc6.withdraw(-0.01));
        assertThrows(IllegalArgumentException.class, () -> acc6.withdraw(0.001));
    }

    @Test
    public void testDeposit() {
        BankAccount acc = new BankAccount("a@b.com", 200);
        assertEquals(200.01, acc.deposit(0.01), 0.001);
        
        acc = new BankAccount("a@b.com", 200.01);
        assertEquals(300.01, acc.deposit(100), 0.001);
        
        acc = new BankAccount("a@b.com", 300.01);
        assertEquals(399.99, acc.deposit(99.98), 0.001);
        
        acc = new BankAccount("a@b.com", 399.99);
        assertEquals(399.99, acc.deposit(0), 0.001);
        
        BankAccount acc2 = new BankAccount("a@b.com", 200);
        assertThrows(IllegalArgumentException.class, () -> acc2.deposit(-0.01));
        assertThrows(IllegalArgumentException.class, () -> acc2.deposit(0.001));
    }

    @Test
    public void testTransfer() {
        BankAccount source = new BankAccount("s@b.com", 200);
        BankAccount dest = new BankAccount("d@b.com", 200);
        source.transfer(0.01, dest);
        assertEquals(199.99, source.getBalance(), 0.001);
        assertEquals(200.01, dest.getBalance(), 0.001);
        
        source = new BankAccount("s@b.com", 199.99);
        dest = new BankAccount("d@b.com", 200.01);
        source.transfer(100, dest);
        assertEquals(99.99, source.getBalance(), 0.001);
        assertEquals(300.01, dest.getBalance(), 0.001);
        
        source = new BankAccount("s@b.com", 99.99);
        dest = new BankAccount("d@b.com", 300.01);
        source.transfer(99.98, dest);
        assertEquals(0.01, source.getBalance(), 0.001);
        assertEquals(399.99, dest.getBalance(), 0.001);
        
        source = new BankAccount("s@b.com", 0.01);
        dest = new BankAccount("d@b.com", 399.99);
        source.transfer(0, dest);
        assertEquals(0.01, source.getBalance(), 0.001);
        assertEquals(399.99, dest.getBalance(), 0.001);
        
        BankAccount s2 = new BankAccount("s@b.com", 0.01);
        BankAccount d2 = new BankAccount("d@b.com", 200);
        assertThrows(InsufficientFundsException.class, () -> s2.transfer(0.02, d2));
        
        BankAccount s3 = new BankAccount("s@b.com", 100);
        BankAccount d3 = new BankAccount("d@b.com", 200);
        assertThrows(InsufficientFundsException.class, () -> s3.transfer(1000, d3));
        
        BankAccount s4 = new BankAccount("s@b.com", 200);
        BankAccount d4 = new BankAccount("d@b.com", 200);
        assertThrows(IllegalArgumentException.class, () -> s4.transfer(-0.01, d4));
        assertThrows(IllegalArgumentException.class, () -> s4.transfer(0.001, d4));
    }
}
