using java 17 make a BankAccount class

generate a source file based on this spec and to pass the tests in tests.yaml, then in a test directory make a JUnit BankAccountTest class based on tests.yaml

feel free to use whatever solutions you think are best, as long as the tests pass and it fits the specification

don't add anything unnessary and prioritize simplicity in the code
there's no need for external libraries (other than JUnit)

the bank account class has 2 fields: email and balance, balance is a double

there are exactly 7 methods (don't add more)

first a simple getBalance and getEmail
we also need deposit and withdraw and transfer (not static)

then make 2 static functions: isEmailValid and isAmountValid
they should be called in other methods as needed

just make isEmailValid based on the tests, it doesn't have to work with the real email specification
isAmountValid should verify that a given amount is non-negative, and fewer than 2 significant decimal places

there is also a custom error: InsufficientFundsException
in cases where that isn't applicable just throw IllegalArgumentException

the constructor should check that the starting value and email are valid

add a standard docstring for each function

there should only be 4 files generated, the main class, the test class, the error class, and the pom.xml file
do not modify anything else or generate anything extra

it is imperative that the package is declared properly in all java files so an ide can correctly process the file

finally, generate a README.md file for this project