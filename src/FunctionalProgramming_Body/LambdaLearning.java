package FunctionalProgramming_Body;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.function.BinaryOperator;
import java.util.function.Predicate;
import java.util.stream.Stream;


/**
 * Functional interface:
 *
 * A predicate is a function that takes one argument and
 * returns a boolean. In Java, Predicate<T> is an interface for
 * predicate functions that take an argument of type T.
 * It is common for lambdas to implement predicates to filter or
 * otherwise process a collection of objects.
 */

/**
 * Lambda:
 *
 *A convenient way to implement functional interface in Java
 *
 */


public class LambdaLearning {

    public static long countMatchingStrings(List<String> input, Predicate<String> condition){
        /**
         * Returns the number of strings that match a given condition.
         *
         * @param input the strings that should be tested.
         * @param condition the condition that strings should be tested against.
         * @return the number of strings in the input that match the condition.
         */

        return input.stream().filter(condition).count();
    }

    public static void main(String[] args) throws IOException {
        List<String> input = List.of("hello\", \"\\t   \", \"world\", \"\", \"\\t\", \" \", \"goodbye\", ");

        /**

         Anonymous class VS Lambdas

         Anonymous Class: is a class that is defined "in-line" and has no name, and so it is called "anonymous".
         Nowadays, many anonymous classes can be replaced by lambdas, but there are still
         some important differences you should know about:

         Anonymous class:
         - class generated at compile-time
         - can override equals()/hashCode()
         - this refers to the anonymous class

         Lambdas
         - class generated at runtime
         - cannot override them, has no identity
         - this refers to the enclosing class

         */

        /**
         * anonymous class:
         *
         * create a sub-class of Predicate interface, and override the Predicate's method
         * in the method, trim string and return whether it is empty
         */
        long numberOfWhitespaceStrings_subclass =
                countMatchingStrings(
                    input,
                    new Predicate<String>(){
                        @Override
                        public boolean test(String s){ return s.trim().isEmpty(); }
                    }
                );

        /**
         * lambda:
         *
         * turn into lambda expression
         */

        long numberOfWhitespaceStrings_lambda =
            countMatchingStrings(input, s -> s.trim().isEmpty());

        System.out.println(numberOfWhitespaceStrings_subclass + " whitespace strings");
        System.out.println(numberOfWhitespaceStrings_lambda + " whitespace strings");


        /**
         * another example: BinaryOperator is Java functional interface
         */

        BinaryOperator<Integer> add = (Integer a, Integer b) -> {return a + b;};
        System.out.println(add.apply(1,2));

        /** Shortcomings of Lambdas:
         *
        * - they can only be used to implement functional interfaces, not classes
        * - Lambdas cannot implement any interface that has multiple abstract methods
        * - Lambdas cannot throw checked exceptions (any subclass of Exception, such as IOException)
        */

        /**
         * example of shortcoming 3
         *
        This code DOES NOT COMPILE because Files.readAllFiles can throw an IOException,
        and Lambdas are not allowed to throw checked exceptions!
        Stream.of("file-a.txt", "file-b.txt", "file-c.txt")
                // return a stream of strings (Sream<String>)
                .map(fileName -> Files.readAllLines(Paths.get(fileName), StandardCharsets.UTF_8))
                // converts the Stream<String> to a Stream<List<String>>.
                // Each list contains the collection of lines in a given file
                .flatMap(Collection::stream)
                //flattens the Stream<List<String>> into a Stream<String>.
                .forEach(System.out::println);
                //prints each of the strings to standard output
         */

        /**
         * fix above code:
         *
         * to address this issues is to handle the IOException inside the lambda using a try-catch
        */

         Stream.of("file-a.txt", "file-b.txt", "file-c.txt")
                .map(Path::of)
                //converts Stream<String. to a Stream<Path> objects method
                .map(p -> {
                    try {
                        return Files.readAllLines(p, StandardCharsets.UTF_8);
                    } catch (IOException e){
                        return List.of();
                    }
                })
                //converts the Stream<Path>s to a Stream<List<String>>
                .flatMap(List::stream)
                .forEach(System.out::println);

        //an equivalent for loop
        List<String> fileNames = Arrays.asList("file-a.txt", "file-b.txt", "file-c.txt");
        for (String fileName: fileNames){
            for (String line: Files.readAllLines(Paths.get(fileName), StandardCharsets.UTF_8)){
                System.out.println(line);
            }
        }

        /**
         * Lambda can capture variables from the surrounding code:
         *
         * - A Captured variable variable:
         * is a variable that is defined outside of a lambda, then referenced inside a lambda.
         *
         * - Effectively final:
         * describes any variable whose value does not change after it is initialized.
         * Only effectively final variables can become captured variables.
         *
         * example:
         *
         * Map<Year, Integer> getClassSizes(List<Student> students) {
         *   final Map<Year, Integer> classSizes = new HashMap<>();
         *   students.stream().forEach(s ->
         *       classSizes.compute(
         *           s.getGraduationYear(),
         *           (k, v) -> (v == null) ? 1 : 1 + v));
         *   return classSizes;
         * }
         *
         * A good test to figure out if a variable is effectively final is to add the final keyword to it.
         * If the code still compiles, that variable is effectively final!
         *
         * In the example, the classSizes variable is effectively final because the value of the variable itself
         * does not change after it's initialized. Remember that in Java, objects are passed by reference.
         * Even though the HashMap changes, the variable's value is the
         * HashMap's location in memory, and that location never changes.
         */


        /**
         * Method reference
         *
         * A method reference is a short lambda expression that refers to a method that is already named.
         * if a method is already defined that you can use, you should use
         * a method reference instead of writing a brand new lambda. Method references cannot capture
         * surrounding variables, though. If you find yourself in this situation, you should use a custom lambda instead.
         */

        // originally:
        // long numberOfWhitespaceStrings_lambda = countMatchingStrings(input, s -> s.trim().isEmpty());
        long numberOfWhitespaceStrings = countMatchingStrings(input, String:: isBlank);




    }

}
