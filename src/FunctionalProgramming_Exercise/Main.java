package FunctionalProgramming_Exercise;

public final class Main {
    public static void main(String[] args) {

        BinaryOperation add = (a, b) -> a + b;
        //to store lambda in a variable whose type is functional interface

        assert 5 == add.apply(2, 3);
        System.out.println(add.apply(2, 3));


        /*
        BinaryOperator<Integer> add = (a, b) -> a + b;
        assert 5 == add.apply(2, 3);
         */
    }
}
