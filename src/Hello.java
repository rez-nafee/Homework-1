import java.util.Scanner;

public class Hello{
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        String num = "";
        num = input.nextLine().trim();
        checkDateFormat(input)
    }

    public static boolean checkDateFormat(String str){
        try {
            int abc = Integer.parseInt(str);
        }catch (Exception ex){
            System.out.println("Input a dumbass number headassboi!");
            return false;
        }
        return true;
    }
}