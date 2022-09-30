package FunctionalProgramming_Body;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class OptionalTypesLearning {
/**
 * Optional Type
 *
 * java.util.Optional is a container object that may or may not contain a single, non-null value.
 * Optional is an alternative to using null to represent the absence of a value.
 *
 * When to use Optional Types
 *
 * When you're designing Java APIs, you should consider using Optional instead of null
 * to represent the absence of values. Optional can have methods invoked on it without throwing NullPointerException.
 * The Stream API uses optional types for many of its terminal operations. However, optionals can sometimes lead to
 * more verbose code by forcing you to call .get() whenever you want the value.
 *
 * Optional.of("Hi");
 * Optional.empty();
  */
    static int getTopScore(List<Student> students){
        return students.stream()
                .filter(Objects::nonNull)
                .mapToInt(Student::getScore)
                //max() method actually returns an OptionalInt, not an Int
                //if the studentList is empty, the max() method will return an empty optional
                //If max() returns an OptionalInt with a value, that value will be used.
                // However, if max() returns OptionalInt.empty(), the call to orElse() makes sure
                // that a default value of 0 will be returned.
                .max()
                .orElse(0);
    /*
    This example also shows you how, in addition to Optional<T>, Java also has optional types that are
    specialized for int, long, and double primitives. These classes avoid the need for auto-boxing
    and auto-unboxing of their values.

     */

    }

    public static void main(String[] args){
        Student a = new Student(100, Year.of(2022));
        Student b = new Student(90, Year.of(2023));
        Student c = new Student(80, Year.of(2022));
        List<Student> studentList = new ArrayList<Student>();
        studentList.add(a);
        studentList.add(b);
        studentList.add(c);

        int topScore = getTopScore(studentList);
        System.out.println(topScore);
    }

}
