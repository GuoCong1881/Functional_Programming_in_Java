package FunctionalProgramming_Body;

import java.time.Year;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamLearning {

    /**
     * Stream: a sequence of elements
     *
     * Streams are useful because they allow us to process collection, one element at a time.
     * They can process elements in many ways, such as (but not limited to)
     * filtering or transforming elements, sorting elements, or computing statistics such as the sum or average.
     *
     *  Stream pipeline:
     *
     *  Stream computation are usually done by performing computations on stream pipeline
     *  A stream pipeline consists of creating a stream, calling intermediate operations on the stream,
     *  and then terminating the stream using a terminal operation.
     *
     *  Intermediate Operations:
     *  return another stream; transform the elements; pipeline can have zero or more intermediate operations
     *
     *  Terminal Operations:
     *  ends the stream; usually returns a result; only one terminal operation at the end of the pipeline
     *
     *  - Streams are single-use. Once you do an operation on a Stream, you cannot to any more operations on that
     *  same stream. This means intermediate operations always return a brand new Stream, never the original.
     *  - Streams are lazily evaluated. No computation happens until the very end, when the terminal operation is called.
     *
     *
     */
    int getTopScore(List<Student> students) {

        // First, the stream() method creates a stream from the students list
        return students.stream()

                //this stream pipeline has two intermediate methods:
                //the filter() method removes the elements of the stream that are null
                .filter(Objects::nonNull)

                //mapToInt() transforms each student into an int
                //notice that each of these methods returns another Stream
                //filter() returns a Stream<Student>, and mapToInt() return an IntStream
                .mapToInt(Student::getScore)

                //Finally, the terminal operation max() computes the maximum value in the IntStream
                //This terminal method actually return an OptionalInt instead of an int
                //if the students parameter is empty or contains only null elements,
                //it's possible the final stream will be empty.
                //in this case, we need to tell the program to return a default value of 0
                .max()
                .orElse(0);
    }

    public static void createStream(){
        /**
         * to create a stream:
         */
        List<String> stringList = new ArrayList<>();
        Stream<String> s = stringList.stream();

        String[] stringArray = new String[3];
        Stream<String> d = Arrays.stream(stringArray);

        Stream<String> e = Stream.of("a", "b", "c");
    }

    /**
     * Collector:
     *
     * - Collectors accumulate elements of a stream in a data structure such as a list, set, or map
     * - The collect() method is a terminal operation that aggregates stream elements
     * - collectors can be passed to collect() to determine what kind of collection is created
     *
     */

    public static void collectorExample(List<Student> studentList){
        List<String> stringList = new ArrayList<>();
        // the collector aggregates the elements into a set
        // there are collectors for all the common data structures such as lists, sets, and maps
        Set<String> s = stringList.stream().collect(Collectors.toSet());

        // Collectors can be used to perform reduction operations such as adding or counting
        // in this example, it will count how many students there are for each graduation year
        Map<Year, Long> graduatingClassSizes =studentList.stream()
                //groupingBy() is used to collect elements into a Map.
                .collect(Collectors.groupingBy(
                        //Collects.counting() counts the number of values for each key
                        Student::getGraduationYear, Collectors.counting()));

    }


    public static void main(String[] args){

        Student a = new Student(100, Year.of(2022));
        Student b = new Student(90, Year.of(2023));
        Student c = new Student(80, Year.of(2022));
        List<Student> studentList = new ArrayList<Student>();
        studentList.add(a);
        studentList.add(b);
        studentList.add(c);

        collectorExample(studentList);

    }

}
