## Java BankAccount Specification

please read the full spec before generating any code!

generate a BankAccount class file based on this spec and to pass the tests in tests.yaml, then in a test directory make a JUnit BankAccountTest class based on tests.yaml

feel free to use whatever solutions you think are best, as long as the tests pass and it fits the specification

don't add anything unnessary and prioritize simplicity in the code, there's no need for external libraries (other than JUnit)

add a standard docstring for each method, but do not add other unneeded comments

create a maven pom.xml with the following details: 
- group id: com.ploorp.gup
- java version: 17
- use junit-jupiter-api:5.8.2
- use maven-compiler-plugin:3.8.0

the bank account class has 2 fields: email (string) and balance (double)

there are exactly 7 methods (don't add more)

- first a simple getBalance and getEmail
- we also need deposit and withdraw and transfer (not static)
- then make 2 static functions: isEmailValid and isAmountValid (they should be called in other methods  as needed and in the contructor)
- make isEmailValid based on the tests, it doesn't have to work with the offical email specification
- isAmountValid should verify that a given amount is non-negative, and fewer than 2 significant decimal places

there is also a custom error: InsufficientFundsException

in cases where that isn't applicable just throw IllegalArgumentException

make sure the package is declared properly in all java files

finally, generate a README.md file with a description of the project and installation instructions

at the end there should only be 5 files generated, the main class, the test class, the error class, the readme, and the pom.xml

do not modify anything else or generate anything extra