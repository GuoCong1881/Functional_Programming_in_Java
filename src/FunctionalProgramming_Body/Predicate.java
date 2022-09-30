package FunctionalProgramming_Body;

/**
- If that annotation is added to any interface that is not a valid
functional interface, the Java compiler will report a compilation error.
- It tells whoever is reading the code that this is interface is
designed to be used with lambdas.
 */

@FunctionalInterface
public interface Predicate<T> {

/**
Interface: describe an API that can be implemented in different ways


Functional Interface:

only certain kinds of interfaces can be implemented by lambdas.
Those interfaces are called functional interfaces.
A functional interface is a Java interface
with exactly one abstract method, called the functional method.

type parameter T: the type being tested
 */


    boolean test(T t);
    /**
    test is the only one abstract method
    (abstract method is the method not implemented,
    so in order to be a functional method, it cannot have a
    default implementation).
     */

    default Predicate<T> negate() {return (t) -> !test(t);}
    /**
    However, functional interfaces can have other default methods.
    In this case, the Predicate interface has a default method called negate()
    that returns another Predicate.
     */
}
