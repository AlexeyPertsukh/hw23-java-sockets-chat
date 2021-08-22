package Client;

import java.util.Scanner;

public class My {
    private My() {
    }

    public static boolean isInteger(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isDouble(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static int nextInt(Scanner sc, String text, int min, int max){
        while(true) {
            System.out.print(text);
            String cmd = sc.next();

            if(isInteger(cmd)) {
                int num = Integer.parseInt(cmd);
                if(num >= min && num <= max) {
                    return num;
                }
            }
        }
    }

    public static int nextInt(Scanner sc, String text){
        return nextInt(sc, text, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    public static double nextDouble(Scanner sc, String text, double min, double max){
        while(true) {
            System.out.print(text);
            String cmd = sc.next();

            if(isDouble(cmd)) {
                double num = Double.parseDouble(cmd);
                if(num >= min && num <= max) {
                    return num;
                }
            }
        }
    }

    public static double nextDouble(Scanner sc, String text){
        return nextDouble(sc, text, Double.MIN_VALUE, Double.MAX_VALUE);
    }

    public static char nextCharLowerCase(Scanner sc, String text, char... arr) {
        while(true) {
            System.out.print(text);
            String cmd = sc.next().toLowerCase();

            if(cmd.length() == 1) {
                char ch = cmd.charAt(0);
                ch = Character.toLowerCase(ch);
                for (char tmp : arr) {
                    tmp = Character.toLowerCase(tmp);
                    if(tmp == ch) {
                        return tmp;
                    }
                }
            }
        }
    }

    private static final String MSG_ENTER_Y = "Введите 'y' для продолжения: ";

    public static void inputCharToContinue(Scanner sc, String msg) {
        if(!msg.isEmpty()) {
            msg += ". ";
        }
        msg += MSG_ENTER_Y;
        My.nextCharLowerCase(sc, msg,'y');
        System.out.println();
    }

    public static void inputCharToContinue(Scanner sc) {
        inputCharToContinue(sc,"");
    }

    //пауза
    public static void sleep(int n){
        try {
            Thread.sleep(n);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

    public static int random(int min, int max) {
        if(min > max) {
            int tmp = min;
            min = max;
            max = tmp;
        }
        return (int) (Math.random() * (max - min)) + min;
    }

    public static int random(int max) {
        return random(0, max);
    }
}
