package FunctionalProgramming_Body;

import java.time.Year;

public class Student {

    int score;
    Year graduationYear;

    public Student(int score, Year graduationYear){
        this.score = score;
        this.graduationYear = graduationYear;
    }
    public int getScore(){
        return this.score;
    }
    public Year getGraduationYear() {return this.graduationYear;}
}
