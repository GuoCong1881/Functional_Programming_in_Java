package FunctionalProgramming_Exercise2;

public final class Calculate {

    public static void main(String[] args){


        Calculator calculator = new Calculator();

        calculator.registerOperation("+", (a, b)-> a + b);
        calculator.registerOperation("-", (a, b)-> a - b);
        calculator.registerOperation("*", (a, b)-> a * b);
        calculator.registerOperation("/", (a, b)-> a / b);

        /**
         *if (args.length != 3){
         *    System.out.println("Usage: Calculate [int] [operator] [int]");
         *    return;
         *}
         *int a = Integer.parseInt(args[0]);
         *String operator = args[1];
         *int b = Integer.parseInt(args[2]);
        */

        System.out.println(calculator.calculate(3, "+", 4));
    }
}
