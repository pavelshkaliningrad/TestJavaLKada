import java.util.Scanner;

public class StatProgram {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String inExpression;
        System.out.print("Enter expression\n--> ");
        inExpression = sc.next();
        try {
            System.out.println("Result: " + Main.calc(inExpression));
        }
        catch (Exception e) {
            System.out.println("Error");
        }
    }
}
