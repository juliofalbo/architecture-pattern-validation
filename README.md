# Architecture Pattern Validation

The goal of this project is to show how we can validate a pattern chosen to create our architecture

## How?

To do this, I used an amazing library called [ArchUnit](https://github.com/TNG/ArchUnit) integrated with [JUnit4](https://junit.org/junit4/).

___

## Clean Code Patterns
Today exists a couple of libraries that help us to do this, the most famous are SonarQuebe and SonarLint.
But those libraries can't allows us to validade our code in tests, and it would be a problem if you follow Fail Fast Validation mindset.

To resolve this, we can use ArchUnit! \o/

ArchUnit allows us to validate rules in the code in test scope, given a fast response to developer if your code is inside the pattern.

Let's say that we have 3 rules to code design.

* We never can call System.out.\*, System.err.\*, and \*.printStackTrace().
* We never can throw any **Generic Exceptions** (Throwable.class, Exception.class and RuntimeException.class)
* We never can use **java.util.logging**

ArchUnit just have default methods to validate those rules.

Take a look at **CodeRulesTest** and see the magic!


## Architecture Patterns
Think at a large project that any teams can create new features, how we can guarantee a pattern?

For example, let's say that we have 4 rules for Resources.

* Only resources can use @RestController annotation.
* All methods annotated with PostMapping should return a ResponseEntity.created()
* All methods annotated with GetMapping should return a ResponseEntity.ok()
* All methods annotated with DeleteMapping should return a ResponseEntity.noContent()

With ArchUnit we can guarantee this rules!

Take a look at **ResourceRules** and see the magic!