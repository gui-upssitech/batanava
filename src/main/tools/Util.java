package main.tools;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Util {

    public static int randInt(int min, int max) {
        return (int) (Math.random() * (max - min)) + min;
    }

    public static int randInt(int max) {
        return randInt(0, max);
    }

    public static int renderMenu(String title, ArrayList<String> options) {
        System.out.println("=== " + title + " ===");
        for(int i = 0; i < options.size(); i++) {
            System.out.println((i+1) + ". " + options.get(i));
        }
        System.out.print("\n");

        return readInt("Votre choix: ", 1, options.size()) - 1;
    }

    public static int readInt(String prompt, int min, int max) {
        Scanner scanner = new Scanner(System.in);
        int number;
        do {
            System.out.print(prompt);
            try {
                number = Integer.parseInt(scanner.next());
            } catch(NumberFormatException e) {
                number = min - 1;
            }
        } while(number < min || number > max);
        return number;
    }

    public static void waitForEnter() {
        System.out.print("Appuyez sur [Entrée] pour continuer ... ");
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String genColorString(String message, TextColor color) {
        return color + message + TextColor.RESET;
    }

    // Source : https://www.delftstack.com/howto/java/java-clear-console/
    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

}
