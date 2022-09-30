package FunctionalProgramming_appetizer;

import java.util.List;
import java.util.Objects;

public class FunctionStyleExample {

    //describe what transformation happen to the input to get the ouput
    int getTopScore_Functional(List<Student> students){
        //input is converted to stream, to use stream API
        return students.stream()
                //filter all Null students
                .filter(Objects::nonNull)
                //map each student to its integer score
                .mapToInt(Student::getScore)
                //return the max value of the scores
                .max()
                //or 0 if the student list has zero length
                .orElse(0);
    }

    int getTopScore_Imperative(List<Student> students){
        int topScore = 0;
        for (Student s: students){
            if (s == null) continue;
            topScore = Math.max(topScore, s.getScore());
        }

        return topScore;
    }

    public static void main(String[] args){

    }
}
